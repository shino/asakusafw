/**
 * Copyright 2011-2014 Asakusa Framework Team.
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
package com.asakusafw.testdriver;

/**
 * ジョブフローのテスト出力データオブジェクト。
 * @since 0.2.0
 * @version 0.6.0
 * @param <T> モデルクラス
 */
public class JobFlowDriverOutput<T> extends FlowDriverOutput<T, JobFlowDriverOutput<T>> {

    /**
     * コンストラクタ。
     *
     * @param driverContext テストドライバコンテキスト
     * @param name 入力の名前
     * @param modelType モデルクラス
     */
    public JobFlowDriverOutput(TestDriverContext driverContext, String name, Class<T> modelType) {
        super(driverContext.getCallerClass(), driverContext.getRepository(), name, modelType);
    }

    @Override
    protected JobFlowDriverOutput<T> getThis() {
        return this;
    }
}
