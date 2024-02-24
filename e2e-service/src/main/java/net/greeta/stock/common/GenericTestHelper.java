package net.greeta.stock.common;

import java.util.function.Function;
import java.util.function.Supplier;

public class GenericTestHelper {

    public <T> T build(Supplier<T> supplier) {
        return supplier.get();
    }

    public <T> T apply(T original, Function<T, T> function) {
        return function.apply(original);
    }
}
