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

import io.zeropointx.fieldaccess.converters.SimpleConstructionConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jeff@mind-trick.net
 * @since 2017-11-26
 */
class BaseFieldConverter implements FieldConverter
{
    private final Map<Class<?>, Map<Class<?>, TypeConverter<?, ?>>> converterMap;

    /**
     * Create a {@link BaseFieldConverter} with the standard set of {@link TypeConverter}s.
     */
    BaseFieldConverter()
    {
        super();

        this.converterMap = new HashMap<>();

        this.register(new SimpleConstructionConverter<>(String.class, Integer.class));
        this.register(new SimpleConstructionConverter<>(String.class, Long.class));
        this.register(new SimpleConstructionConverter<>(String.class, Double.class));
        this.register(new SimpleConstructionConverter<>(String.class, Float.class));
        this.register(new SimpleConstructionConverter<>(String.class, Byte.class));
        this.register(new SimpleConstructionConverter<>(String.class, Boolean.class));
    }

    @Override
    public final <S, D> void register(final TypeConverter<S, D> converter)
    {
        this.getDestinationMap(converter.getSourceClass()).put(converter.getDestinationClass(), converter);
    }

    protected final <S> Map<Class<?>, TypeConverter<?, ?>> getDestinationMap(final Class<S> sourceType)
    {
        return this.converterMap.computeIfAbsent(sourceType, (type) -> new HashMap<>());
    }

    @Override
    public final <S, D> TypeConverter<S, D> getConverter(final Class<S> sourceType, final Class<D> destinationType)
    {
        return (TypeConverter<S,D>)this.getDestinationMap(sourceType)
                .computeIfAbsent(destinationType, (type) -> new BestEffortTypeConverter<>(sourceType, destinationType));
    }
}
