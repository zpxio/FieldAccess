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

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author jeff@mind-trick.net
 * @since 2017-08-29
 */
public class FieldRegistry
{
    /**
     * The global, shared registry for field lookup operations.
     */
    protected static FieldRegistry global = new FieldRegistry();

    /**
     * Replace the current registry with a new version. This will be used for all future lookup operations.
     * Any {@link FieldAccess}s currently performing operations may use the previous registry if they have already
     * fetched references to it. This shouldn't have adverse effects beyond the change in registry instance. If
     * objects need to avoid such situations, external synchronization is required.
     * <p>
     * Due to visibility restrictions, this method is likely to only be called during testing scenarios.
     *
     * @param registry The new registry for use for global lookup operations.
     */
    protected static void setGlobal(final FieldRegistry registry)
    {
        FieldRegistry.global = registry;
    }

    /** A {@link Map} storing {@link ClassFieldIndex} instances for each class that has been encountered. */
    private final Map<Class<? extends FieldAccess>, ClassFieldIndex> index;

    private final FieldConverter converter;

    /**
     * Create a new empty {@link FieldRegistry}.
     */
    public FieldRegistry()
    {
        super();

        this.index = Maps.newConcurrentMap();
        this.converter = new UserFieldConverter(new BaseFieldConverter());
    }

    /**
     * Fetch a {@link ClassFieldIndex} for the requested {@link Class}. If no index exists for the class, a new
     * one will be created and it will index its target class. This does involve some effort, so in time critical
     * situations, this method can also be used to pre-index a class.
     *
     * @return A populated {@link ClassFieldIndex}.
     */
    public ClassFieldIndex getIndex(final Class<? extends FieldAccess> fieldSetClass)
    {
        return this.index.computeIfAbsent(fieldSetClass, c -> new ClassFieldIndex(c));
    }

    /**
     * Fetch a {@link FieldConverter} attached to the registry which is provided as a facility for converting values
     * from one type to another.
     *
     * @return A {@link FieldConverter} instance.
     */
    public FieldConverter getConverter()
    {
        return converter;
    }
}
