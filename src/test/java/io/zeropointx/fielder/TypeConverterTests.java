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

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author jeff@mind-trick.net
 * @since 2017-11-27
 */
public class TypeConverterTests
{
    private final Class<Double> sourceClass = Double.class;
    private final Class<Long> destinationClass = Long.class;
    private TypeConverter<Double, Long> converter;

    @Before
    public void setup()
    {
        this.converter = new TypeConverter<Double, Long>(this.sourceClass, this.destinationClass)
        {
            @Override
            public Long convert(final Double source)
            {
                return source.longValue();
            }
        };
    }

    @Test
    public void constructAndGet()
    {
        assertThat(this.converter).isNotNull();
        assertThat(this.converter.getSourceClass()).isEqualTo(this.sourceClass);
        assertThat(this.converter.getDestinationClass()).isEqualTo(this.destinationClass);
    }

    @Test
    public void convert()
    {
        assertThat(this.converter.convert(4.5)).isInstanceOf(this.destinationClass).isEqualTo(4L);
    }

    @Test
    public void string()
    {
        assertThat(this.converter.toString()).contains(this.sourceClass.getSimpleName()).contains(this.destinationClass.getSimpleName());
    }
}
