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
 * A {@link UserFieldConverter} is a shared facility to handle the conversion of data types for {@link FieldValue}s. It
 * collects {@link TypeConverter}s and provides a simple facade for invoking the converters to handle the conversion
 * of data stored in a {@link FieldValue}. A given {@link UserFieldConverter} can delegate to a base instance if no local
 * converter matches are found.
 *
 * @author jeff@mind-trick.net
 * @since 2017-09-02
 */
public class UserFieldConverter extends BaseFieldConverter implements FieldConverter
{
    private final FieldConverter base;

    public UserFieldConverter(final FieldConverter base)
    {
        super();

        this.base = base;
    }

    /**
     * Fetch the base converter to use if no suitable local {@link TypeConverter} is found.
     *
     * @return A {@link UserFieldConverter} to delegate conversion to.
     */
    protected final FieldConverter getBase()
    {
        return this.base;
    }
}
