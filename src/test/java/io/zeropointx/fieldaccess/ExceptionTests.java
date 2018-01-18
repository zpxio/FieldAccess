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

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author jeff@darkware.org
 * @since 2017-01-09
 */
@RunWith(Parameterized.class)
public class ExceptionTests
{
    private final Class<? extends Exception> type;

    public ExceptionTests(Class<? extends Exception> exceptionType)
    {
        this.type = exceptionType;
    }

    @Parameterized.Parameters
    public static Set<Class<? extends Exception>> exceptionType()
    {
        return Sets.newHashSet(
                MissingTypeConversionException.class,
                NoSetterFoundException.class
        );
    }

    @Test
    public void messageConstructor() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
    {
        String message = "Message";
        Constructor<? extends Exception> constructor = this.type.getConstructor(String.class);

        Exception e = constructor.newInstance(message);

        assertThat(e.getMessage()).isEqualTo(message);
    }

    @Test
    public void messageCauseConstructor() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
    {
        String message = "Message";
        Exception cause = new IllegalStateException("IllegalState");
        Constructor<? extends Exception> constructor = this.type.getConstructor(String.class, Throwable.class);

        Exception e = constructor.newInstance(message, cause);

        assertThat(e.getMessage()).isEqualTo(message);
        assertThat(e.getCause()).isInstanceOf(IllegalStateException.class);
    }


}
