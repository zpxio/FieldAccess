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

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author jeff@mind-trick.net
 * @since 2017-12-13
 */
public class FieldValueTests
{
    @Test
    public void construct()
    {
        final String value = String.valueOf(14);
        FieldValue fv = new FieldValue(value);

        assertThat(fv.toString()).isEqualTo(value);
        assertThat(fv.getValue()).isSameAs(value);
    }

    @Test
    public void toInteger()
    {
        final Integer originalValue = Integer.valueOf(14);
        final String value = originalValue.toString();
        FieldValue fv = new FieldValue(value);

        assertThat(fv.toInteger()).isEqualTo(originalValue);
    }

    @Test
    public void toDouble()
    {
        final Double originalValue = Double.valueOf(14.1);
        final String value = originalValue.toString();
        FieldValue fv = new FieldValue(value);

        assertThat(fv.toDouble()).isEqualTo(originalValue);
    }



}
