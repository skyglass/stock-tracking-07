package net.greeta.stock.common;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class GenericBuilder<T> {

    public T build(Supplier<T> supplier) {
        return build(supplier, null);
    }

    public T build(Supplier<T> supplier, Consumer<T> consumer) {
        return build(supplier.get(), consumer);
    }

    public T build(T original, Consumer<T> consumer) {
        if (consumer != null) {
            consumer.accept(original);
        }
        return original;
    }

    public T apply(Supplier<T> supplier, Function<T, T> function) {
        return apply(supplier.get(), function);
    }

    public T apply(T original, Function<T, T> function) {
        return function.apply(original);
    }

}
