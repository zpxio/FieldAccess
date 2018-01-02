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

/**
 * This is the base implementation of a pseudo-union type.
 *
 * @author jeff@mind-trick.net
 * @since 2017-08-29
 */
public class FieldValue
{
    /**
     * Build a {@link FieldValue} for the given {@link Object} value. The exact type of {@link FieldValue}
     * created is dependant upon the type of the object and
     *
     * @param obj The object to store as the value.
     * @return A new {@link FieldValue} object.
     */
    public static FieldValue forResult(final Object obj)
    {
        return new FieldValue(obj);
    }

    /**
     * The stored raw value of the field.
     */
    private final Object value;

    /**
     * Create a new {@link FieldValue} which stores the given object.
     *
     * @param obj
     */
    public FieldValue(final Object obj)
    {
        super();

        this.value = obj;
    }

    public Object getValue()
    {
        return this.value;
    }

    public Integer toInteger()
    {
        return FieldRegistry.global.getConverter().getConverter(this.value.getClass(), Integer.class).convert(this.value);
    }

    public Long toLong()
    {
        return FieldRegistry.global.getConverter().getConverter(this.value.getClass(), Long.class).convert(this.value);
    }

    public Float toFloat()
    {
        return FieldRegistry.global.getConverter().getConverter(this.value.getClass(), Float.class).convert(this.value);
    }

    public Double toDouble()
    {
        return FieldRegistry.global.getConverter().getConverter(this.value.getClass(), Double.class).convert(this.value);
    }

    public Byte toByte()
    {
        return FieldRegistry.global.getConverter().getConverter(this.value.getClass(), Byte.class).convert(this.value);
    }

    public String toString()
    {
        return this.value.toString();
    }
}
