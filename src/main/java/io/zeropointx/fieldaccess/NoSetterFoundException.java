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

/**
 * @author jeff@mind-trick.net
 * @since 2017-11-26
 */
public class NoSetterFoundException extends RuntimeException
{
    /**
     * Create a new {@link NoSetterFoundException} with the given explanation.
     *
     * @param message An explanation for how the exception was encountered.
     */
    public NoSetterFoundException(final String message)
    {
        super(message);
    }

    /**
     * Create a new {@link NoSetterFoundException} caused by another error.
     *
     * @param message An explanation of what was being done while the error was encountered.
     * @param cause The {@link Throwable} cause which triggered this exception.
     */
    public NoSetterFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
