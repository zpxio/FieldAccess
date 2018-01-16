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

import com.google.common.base.Objects;

/**
 * A {@link TypeConverter} is a utility object which encapsulates code that can do a one-way conversion from one object
 * type to another. The scope is intended to be as-global-as-you-want, though the resulting converter object makes no
 * attempt to register itself with any sort of context or global manager.
 * <p>
 * Of course, the most obvious use of a {@link TypeConverter} is to be registered with a {@link UserFieldConverter} for use
 * in handling automatic type conversion for a {@link FieldRegistry}.
 *
 * @author jeff@mind-trick.net
 * @since 2017-09-02
 */
public abstract class TypeConverter<S, D>
{
    private final Class<S> sourceClass;
    private final Class<D> destinationClass;

    /**
     * Create a new {@link TypeConverter} which can convert objects of the supplied source type to a child class of the
     * destination type.
     *
     * @param sourceClass
     * @param destinationClass
     */
    public TypeConverter(final Class<S> sourceClass, final Class<D> destinationClass)
    {
        super();

        this.sourceClass = sourceClass;
        this.destinationClass = destinationClass;
    }

    /**
     * Fetch the source {@link Class} of the data to convert.
     *
     * @return A {@link Class} which this converter can convert.
     */
    public final Class<S> getSourceClass()
    {
        return this.sourceClass;
    }

    /**
     * Fetch the destination {@link Class} which the conversion will produce.
     *
     * @return A {@link Class} which is a supertype of the object produced by conversion.
     */
    public final Class<D> getDestinationClass()
    {
        return this.destinationClass;
    }

    /**
     * Convert the given object to the destination type.
     *
     * @param source The source data object.
     * @return An object of the {@link #getDestinationClass() destination type} which was produced by converting the
     * supplied source object.
     */
    public final D convert(final Object source)
    {
        S typedSource = this.sourceClass.cast(source);

        if (this.sourceClass.isAssignableFrom(this.destinationClass)) {
            return this.destinationClass.cast(source);
        }

        return this.convertTyped(typedSource);
    }

    abstract protected D convertTyped(final S source);

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (!(o instanceof TypeConverter)) return false;
        final TypeConverter<?, ?> that = (TypeConverter<?, ?>) o;
        return Objects.equal(sourceClass, that.sourceClass) &&
               Objects.equal(destinationClass, that.destinationClass);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(sourceClass, destinationClass);
    }

    @Override
    public String toString()
    {
        return String.format("TypeConverter[%s->%s]", this.sourceClass.getSimpleName(), this.destinationClass.getSimpleName());
    }
}
