/**
 * Copyright 2011 Asakusa Framework Team.
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
package com.asakusafw.runtime.flow;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import com.asakusafw.runtime.core.Result;

/**
 * 結果を出力する。
 * @param <T> 結果の型
 */
public class ResultOutput<T extends Writable> implements Result<T> {

    static final Log LOG = LogFactory.getLog(ResultOutput.class);

    private final TaskAttemptContext context;

    private final RecordWriter<Object, Object> writer;

    /**
     * インスタンスを生成する。
     * @param context 現在のコンテキスト
     * @param writer 結果の出力先
     * @throws IOException 初期化に失敗した場合
     * @throws InterruptedException 初期化に失敗した場合
     * @throws IllegalArgumentException 引数に{@code null}が指定された場合
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResultOutput(
            TaskAttemptContext context,
            RecordWriter writer) throws IOException, InterruptedException {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null"); //$NON-NLS-1$
        }
        if (writer == null) {
            throw new IllegalArgumentException("writer must not be null"); //$NON-NLS-1$
        }
        this.context = context;
        this.writer = writer;
    }

    @Override
    public void add(T result) {
        try {
            writer.write(getKey(result), result);
        } catch (Exception e) {
            throw new Result.OutputException(e);
        }
    }

    /**
     * Returns a key for the value.
     * @param result target value
     * @return a corresponded key
     */
    protected Object getKey(T result) {
        return NullWritable.get();
    }

    /**
     * 現在の出力を破棄する。
     * @throws IOException 出力のフラッシュに失敗した場合
     * @throws InterruptedException 出力の破棄に割り込みが発行された場合
     * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
     */
    public void close() throws IOException, InterruptedException {
        writer.close(context);
    }
}
