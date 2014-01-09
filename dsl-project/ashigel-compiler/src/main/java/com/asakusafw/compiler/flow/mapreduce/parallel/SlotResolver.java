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
package com.asakusafw.compiler.flow.mapreduce.parallel;

import java.util.List;

import com.asakusafw.compiler.common.Precondition;
import com.asakusafw.compiler.flow.DataClass;
import com.asakusafw.compiler.flow.DataClass.Property;
import com.asakusafw.compiler.flow.FlowCompilingEnvironment;
import com.asakusafw.utils.collections.Lists;

/**
 * {@link Slot}を解析して{@link ResolvedSlot}に変換する。
 */
public class SlotResolver {

    private FlowCompilingEnvironment environment;

    /**
     * インスタンスを生成する。
     * @param environment コンパイルに利用する環境
     * @throws IllegalArgumentException 引数に{@code null}が指定された場合
     */
    public SlotResolver(FlowCompilingEnvironment environment) {
        Precondition.checkMustNotBeNull(environment, "environment"); //$NON-NLS-1$
        this.environment = environment;
    }

    /**
     * していのスロット一覧をコンパイルし、結果を返す。
     * @param slots 対象のスロット一覧
     * @return コンパイル結果
     * @throws IllegalArgumentException 引数に{@code null}が指定された場合
     */
    public List<ResolvedSlot> resolve(List<Slot> slots) {
        Precondition.checkMustNotBeNull(slots, "slots"); //$NON-NLS-1$
        List<ResolvedSlot> results = Lists.create();
        int number = 0;
        for (Slot slot : slots) {
            ResolvedSlot compiled = compile(slot, number++);
            results.add(compiled);
        }
        return results;
    }

    private ResolvedSlot compile(Slot slot, int number) {
        assert slot != null;
        DataClass valueClass = environment.getDataClasses().load(slot.getType());
        List<Property> sortProperties = Lists.create();
        if (valueClass == null) {
            valueClass = new DataClass.Unresolved(environment.getModelFactory(), slot.getType());
            environment.error("型{0}をロードできませんでした", slot.getType());
        } else {
            for (String name : slot.getSortPropertyNames()) {
                Property property = valueClass.findProperty(name);
                if (property == null) {
                    environment.error("型{0}のプロパティをロードできませんでした", slot.getType(), name);
                } else {
                    sortProperties.add(property);
                }
            }
        }
        return new ResolvedSlot(slot, number, valueClass, sortProperties);
    }
}
