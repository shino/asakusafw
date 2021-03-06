/**
 * Copyright 2011-2016 Asakusa Framework Team.
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
package com.asakusafw.runtime.io.line;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.asakusafw.runtime.io.ModelOutput;
import com.asakusafw.runtime.value.StringOption;

/**
 * A simple line writer for text.
 * @since 0.7.5
 */
public abstract class LineOutput implements ModelOutput<StringOption> {

    static final Charset INTERNAL_CHARSET = StandardCharsets.UTF_8;

    static final char LINE_BREAK = '\n';

    /**
     * Creates a new instance.
     * @param stream the target stream
     * @param path the destination path
     * @param configuration current configuration
     * @return the created instance
     * @throws IllegalArgumentException if some parameters were {@code null}
     */
    public static LineOutput newInstance(OutputStream stream, String path, LineConfiguration configuration) {
        if (stream == null) {
            throw new IllegalArgumentException("stream must not be null"); //$NON-NLS-1$
        }
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null"); //$NON-NLS-1$
        }
        if (configuration.getCharset().equals(INTERNAL_CHARSET)) {
            return new Utf8LineOutput(stream, path, configuration);
        } else {
            return new BasicLineOutput(stream, path, configuration);
        }
    }
}
