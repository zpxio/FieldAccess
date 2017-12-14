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

package io.zeropointx.fielder.sample;

import io.zeropointx.fielder.FieldAccess;

/**
 * A simple object using default options.
 *
 * @author jeff@mind-trick.net
 * @since 2017-08-30
 */
public class SampleOne implements FieldAccess
{
    private String name;
    private int size;
    private double itemWeight;

    public SampleOne()
    {
        super();

        this.setName("?");
        this.setSize(0);
        this.setItemWeight(1.0);
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(final int size)
    {
        this.size = size;
    }

    public double getItemWeight()
    {
        return itemWeight;
    }

    public void setItemWeight(final double itemWeight)
    {
        this.itemWeight = itemWeight;
    }
}
