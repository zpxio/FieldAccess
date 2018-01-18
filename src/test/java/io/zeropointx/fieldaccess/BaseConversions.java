/*================================================================================================
 =
 = Copyright 2018: Jeff Sharpe
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

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * @author jeff@mind-trick.net
 * @since 2018-01-17
 */
@RunWith(Parameterized.class)
public class BaseConversions
{
    @Parameterized.Parameters(name = "{0}")
    public static List<BaseConversionCase> getConversionCases()
    {
        return Lists.newArrayList(
            new BaseConversionCase(new Integer(4), new Integer(4)),

            new BaseConversionCase("54", new Integer(54)),
            new BaseConversionCase("492090118", new Long(492090118)),
            new BaseConversionCase("1919948193.25", new Double(1919948193.25)),
            new BaseConversionCase("4.5", new Float(4.5)),
            new BaseConversionCase("4", new Byte((byte)4)),
            new BaseConversionCase("true", new Boolean(true)),

            // Noop transformation of Strings
            new BaseConversionCase(new String("test"), new String("test"))
        );
    }

    private final BaseConversionCase testCase;
    private final BaseFieldConverter converter;

    public BaseConversions(final BaseConversionCase testCase)
    {
        super();

        this.testCase = testCase;
        this.converter = new BaseFieldConverter();
    }

    @Test
    public void conversion()
    {
        final Object result = this.converter.getConverter(this.testCase.sourceType, this.testCase.destinationType).convert(this.testCase.sourceData);
        assertThat(result).isEqualTo(this.testCase.expectedValue);
    }

    public static class BaseConversionCase
    {
        public final Class<?> sourceType;
        public final Class<?> destinationType;
        public final Object sourceData;
        public final Object expectedValue;

        public  BaseConversionCase(final Object sourceData, final Object value)
        {
            super();

            this.sourceType = sourceData.getClass();
            this.destinationType = value.getClass();
            this.sourceData = sourceData;
            this.expectedValue = value;
        }

        @Override
        public String toString()
        {
            return String.format("%s->%s", this.sourceType.getSimpleName(), this.destinationType.getSimpleName());
        }
    }
}
