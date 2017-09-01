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

import io.zeropointx.fielder.sample.SampleOne;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author jeff@mind-trick.net
 * @since 2017-08-30
 */
public class ClassFieldIndexTests
{
    @Test
    public void fieldName_fromMethodName_simple()
    {
        final ClassFieldIndex index = new ClassFieldIndex(SampleOne.class);
        assertThat(index.parseFieldFromMethodName("getName")).isEqualTo("name");
    }

    @Test
    public void fieldName_fromMethodName_multiWord()
    {
        final ClassFieldIndex index = new ClassFieldIndex(SampleOne.class);
        assertThat(index.parseFieldFromMethodName("getItemWeight")).isEqualTo("itemWeight");
    }

    @Test
    public void fieldName_fromMethodName_acronymItem()
    {
        final ClassFieldIndex index = new ClassFieldIndex(SampleOne.class);
        assertThat(index.parseFieldFromMethodName("getHTTPResponse")).isEqualTo("httpResponse");
    }

    @Test
    public void fieldName_fromMethodName_captialTail()
    {
        final ClassFieldIndex index = new ClassFieldIndex(SampleOne.class);
        assertThat(index.parseFieldFromMethodName("getGraphX")).isEqualTo("graphX");
    }

    @Test
    public void fieldName_fromMethodName_singleLetterPart()
    {
        final ClassFieldIndex index = new ClassFieldIndex(SampleOne.class);
        assertThat(index.parseFieldFromMethodName("getStandardCString")).isEqualTo("standardCString");
    }

    @Test
    public void fieldName_fromMethodName_booleanIs()
    {
        final ClassFieldIndex index = new ClassFieldIndex(SampleOne.class);
        assertThat(index.parseFieldFromMethodName("isActive")).isEqualTo("active");
    }

    @Test
    public void fieldName_fromMethodName_booleanHas()
    {
        final ClassFieldIndex index = new ClassFieldIndex(SampleOne.class);
        assertThat(index.parseFieldFromMethodName("hasAbbreviation")).isEqualTo("abbreviation");
    }
}
