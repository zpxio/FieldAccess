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

package io.zeropointx.fielder;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author jeff@mind-trick.net
 * @since 2017-08-29
 */
public class ClassFieldIndex
{
    private static final Set<String> accessorPrefices = Collections.unmodifiableSet(Sets.newHashSet("get", "is", "has" ));

    /** The {@link Class} being indexed. */
    private final Class<? extends FieldSet> targetClass;

    private final Map<String, Method> fieldMap;

    /**
     * Create a new index for a {@link FieldSet} class. This will scan the class for supported fields and store
     * the results in this index.
     *
     * @param targetClass The {@link Class} to index.
     */
    public ClassFieldIndex(final Class<? extends FieldSet> targetClass)
    {
        super();

        this.targetClass = targetClass;
        this.fieldMap = Maps.newConcurrentMap();

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
            String fieldName = this.getFieldForMethod(method);

            this.fieldMap.put(fieldName, method);
        }
    }

    public final String getFieldForMethod(final Method method)
    {
        return this.parseFieldFromMethodName(method.getName());
    }

    public String parseFieldFromMethodName(final String methodName)
    {
        String fieldName = methodName;

        for (final String prefix : ClassFieldIndex.accessorPrefices)
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
        if (this.fieldMap.containsKey(fieldName))
        {
            Method getter = this.fieldMap.get(fieldName);

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
}
