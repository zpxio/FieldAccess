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

import io.zeropointx.fieldaccess.sample.SampleOne;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author jeff@mind-trick.net
 * @since 2017-08-29
 */
public class FieldAccessTest
{
    @Test
    public void getField_sampleOne_simple()
    {
        String testName = "aardvark";
        SampleOne s = new SampleOne();
        s.setName(testName);

        assertThat(s.getField("name").toString()).isEqualTo(testName);
    }

    @Test
    public void getField_sampleOne_int() {
        int testValue = 22;
        SampleOne s = new SampleOne();

        s.setSize(testValue);

        assertThat(s.getField("size").toInteger()).isEqualTo(testValue);
    }

    @Test
    public void getField_sampleOne_double() {
        double testValue = 3.14156;
        SampleOne s = new SampleOne();

        s.setItemWeight(testValue);

        assertThat(s.getField("itemWeight").toDouble()).isEqualTo(testValue);
    }

}
