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

package io.zeropointx.fieldaccess.converters;

import io.zeropointx.fieldaccess.TypeConverter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author jeff@mind-trick.net
 * @since 2017-12-13
 */
public class SimpleConstructionConverter<S, D> extends TypeConverter<S, D>
{
    final Constructor<D> builder;
    
    /**
     * Create a new {@link TypeConverter} which can convert objects of the supplied source type to a child class of the
     * destination type.
     *
     * @param sourceClass
     * @param destinationClass
     */
    public SimpleConstructionConverter(final Class<S> sourceClass, final Class<D> destinationClass)
    {
        super(sourceClass, destinationClass);

        try
        {
            this.builder = destinationClass.getConstructor(sourceClass);
        }
        catch (NoSuchMethodException e)
        {
            throw new IllegalArgumentException("Supplied destination class does not provide a single-parameter constructor of the required type (" + sourceClass.getName() + ")", e);
        }
    }

    @Override
    protected D convertTyped(final S source)
    {
        try
        {
            return this.builder.newInstance(source);
        }
        catch (InstantiationException e)
        {
            throw new IllegalStateException("The destination type cannot be instantiated.", e);
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException("The destination constructor is not accessible.", e);
        }
        catch (InvocationTargetException e)
        {
            throw new IllegalArgumentException("An error occurred while trying to convert the supplied value.");
        }
    }
}
