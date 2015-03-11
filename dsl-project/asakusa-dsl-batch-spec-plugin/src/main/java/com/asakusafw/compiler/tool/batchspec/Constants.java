/**
 * Copyright 2011-2015 Asakusa Framework Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.asakusafw.compiler.tool.batchspec;

import java.nio.charset.Charset;

/**
 * Constants for this package.
 * @since 0.5.0
 */
final class Constants {

    public static final String PATH = "etc/batch-spec.json"; //$NON-NLS-1$

    public static final Charset ENCODING = Charset.forName("UTF-8"); //$NON-NLS-1$

    private Constants() {
        return;
    }
}
