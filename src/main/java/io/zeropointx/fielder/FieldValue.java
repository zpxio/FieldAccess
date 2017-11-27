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

/**
 * This is the base implementation of a pseudo-union type.
 *
 * @author jeff@mind-trick.net
 * @since 2017-08-29
 */
public class FieldValue<T>
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
    private final T value;

    /**
     * Create a new {@link FieldValue} which stores the given object.
     *
     * @param obj
     */
    public FieldValue(final T obj)
    {
        super();

        this.value = obj;
    }

    public String toString()
    {
        return this.value.toString();
    }
}
