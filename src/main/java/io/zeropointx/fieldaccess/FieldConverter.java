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
 * @author jeff@mind-trick.net
 * @since 2017-12-13
 */
public interface FieldConverter
{

    /**
     * Register a {@link TypeConverter} for the given source and destination types. After registering the converter,
     * attempts to convert the supplied types will use the supplied {@link TypeConverter}.
     *
     * @param converter The {@link TypeConverter} to use.
     * @param <S>
     * @param <D>
     */
    <S,D> void register(TypeConverter<S, D> converter);

    /**
     * Fetch a {@link TypeConverter} for the given source and destination types.
     *
     * @param sourceType The source object type to convert from.
     * @param destinationType The destination object type to convert to.
     * @param <S> The source class type.
     * @param <D> The destination class type.
     * @return A {@link TypeConverter}, possibly a {@link BestEffortTypeConverter}, which can be used to convert one
     * type to another.
     */
    <S, D> TypeConverter<S, D> getConverter(Class<S> sourceType, Class<D> destinationType);
}
