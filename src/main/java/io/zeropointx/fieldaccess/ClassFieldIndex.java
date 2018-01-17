/*================================================================================================
 =
 = Copyright 2017: Jeff Sharpe
 =
 =    Licensed under the Apache License, Version 2.0 (the "License");
 =    you may not use this file except in compliance with the License.
 =    You may obtain a copy of the License at
 =
 =        http://www.apache.org/licenses/LICENSE-2.0
 =
 =    Unless required by applicable law or agreed to in writing, software
 =    distributed under the License is distributed on an "AS IS" BASIS,
 =    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 =    See the License for the specific language governing permissions and
 =    limitations under the License.
 =
 ===============================================================================================*/

package io.zeropointx.fieldaccess;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jeff@mind-trick.net
 * @since 2017-08-29
 */
public class ClassFieldIndex
{
    protected static final Set<String> accessorPrefices = Collections.unmodifiableSet(Sets.newHashSet("get", "is", "has" ));
    protected static final Set<String> mutatorPrefices = Collections.unmodifiableSet(Sets.newHashSet("set"));

    /** The {@link Class} being indexed. */
    private final Class<? extends FieldAccess> targetClass;

    private final Map<String, Method> getters;
    private final Map<String, Map<Class<?>, Method>> setters;

    /**
     * Create a new index for a {@link FieldAccess} class. This will scan the class for supported fields and store
     * the results in this index.
     *
     * @param targetClass The {@link Class} to index.
     */
    public ClassFieldIndex(final Class<? extends FieldAccess> targetClass)
    {
        super();

        this.targetClass = targetClass;
        this.getters = Maps.newConcurrentMap();
        this.setters = Maps.newConcurrentMap();

        this.scanFields();
    }

    private final void scanFields()
    {
        Set<Method> selectedMethods = Sets.newHashSet();

        //TODO: Find the default policy from the class-level annotation


        //Scan all methods that might work.
        Arrays.stream(this.targetClass.getDeclaredMethods())
              .filter(method -> method.getReturnType() != Void.TYPE)
              .filter(method -> method.getParameterCount() == 0)
              .forEach(selectedMethods::add);

        // Scan selected methods
        for (Method method : selectedMethods)
        {
            // Look for a field for the method.
            String fieldName = this.getFieldForMethod(method, ClassFieldIndex.accessorPrefices);

            this.getters.put(fieldName, method);
        }

        // Scan methods that look like setters
        Set<Method> possibleSetters = Arrays.stream(this.targetClass.getDeclaredMethods())
              .filter(method -> method.getParameterCount() == 1)
              .filter(method -> method.getName().startsWith("set"))
              .collect(Collectors.toSet());

        for (Method method : possibleSetters)
        {
            String fieldName = this.getFieldForMethod(method, ClassFieldIndex.mutatorPrefices);

            Map<Class<?>, Method> setterMap = this.setters.computeIfAbsent(fieldName, key -> new HashMap<>());
            setterMap.putIfAbsent(method.getParameterTypes()[0], method);
        }
    }

    public final String getFieldForMethod(final Method method, final Set<String> prefixes)
    {
        return this.parseFieldFromMethodName(method.getName(), prefixes);
    }

    public String parseFieldFromMethodName(final String methodName, final Set<String> prefixes)
    {
        String fieldName = methodName;

        for (final String prefix : prefixes)
        {
            if (fieldName.startsWith(prefix) && Character.isUpperCase(fieldName.charAt(prefix.length())))
            {
                fieldName = fieldName.substring(prefix.length(), prefix.length()+1)
                    + fieldName.substring(prefix.length()+1);

                break;
            }
        }

        char[] nameBuffer = fieldName.toCharArray();
        boolean seenLower = false;
        for (int i = 0; i < fieldName.length(); i++)
        {
            if (i == 0 || i < nameBuffer.length-1 && Character.isUpperCase(nameBuffer[i]) && !seenLower && Character.isUpperCase(nameBuffer[i+1]))
            {
                nameBuffer[i] = Character.toLowerCase(nameBuffer[i]);
            }
            else if  (Character.isLowerCase(nameBuffer[i]))
            {
                seenLower = true;
            }
        }

        return String.valueOf(nameBuffer);
    }

    public FieldValue getValue(final Object instance, final String fieldName)
    {
        if (this.getters.containsKey(fieldName))
        {
            Method getter = this.getters.get(fieldName);

            try
            {
                return FieldValue.forResult(getter.invoke(instance));
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }

        return new UnknownFieldValue();
    }

    public boolean isMatchingClass(final Class<?> target, final Class<?> query)
    {
        if (target.isPrimitive())
        {
            if ((target == int.class) && (query == Integer.class)) return true;
            if ((target == long.class) && (query == Long.class)) return true;
            if ((target == double.class) && (query == Double.class)) return true;
            if ((target == boolean.class) && (query == Boolean.class)) return true;
            if ((target == float.class) && (query == Float.class)) return true;
            if ((target == short.class) && (query == Short.class)) return true;
            if ((target == byte.class) && (query == Byte.class)) return true;
            if ((target == char.class) && (query == Character.class)) return true;
        }
        else
        {
            return target.isAssignableFrom(query);
        }

        return false;
    }

    public void setValue(final Object instance, final String fieldName, final Object value)
    {
        if (this.setters.containsKey(fieldName))
        {
            Map<Class<?>, Method> setterMap = this.setters.get(fieldName);
            try
            {
                setterMap.entrySet().stream().filter(e -> this.isMatchingClass(e.getKey(), value.getClass()))
                         .findFirst()
                         .orElseThrow(() -> new NoSetterFoundException("No compatible setter method found for " + fieldName + "(" + value.getClass().getName() + ")"))
                         .getValue().invoke(instance, value);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
    }
}
