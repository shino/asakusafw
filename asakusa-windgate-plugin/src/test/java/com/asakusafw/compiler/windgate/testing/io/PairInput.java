package com.asakusafw.compiler.windgate.testing.io;
import com.asakusafw.compiler.windgate.testing.model.Pair;
import com.asakusafw.runtime.io.ModelInput;
import com.asakusafw.runtime.io.RecordParser;
import java.io.IOException;
/**
 * TSVファイルなどのレコードを表すファイルを入力として<code>pair</code>を読み出す
 */
public final class PairInput implements ModelInput<Pair> {
    private final RecordParser parser;
    /**
     * インスタンスを生成する。
     * @param parser 利用するパーサー
     * @throws IllegalArgumentException 引数に<code>null</code>が指定された場合
     */
    public PairInput(RecordParser parser) {
        if(parser == null) {
            throw new IllegalArgumentException("parser");
        }
        this.parser = parser;
    }
    @Override public boolean readTo(Pair model) throws IOException {
        if(parser.next()== false) {
            return false;
        }
        parser.fill(model.getKeyOption());
        parser.fill(model.getValueOption());
        return true;
    }
    @Override public void close() throws IOException {
        parser.close();
    }
}