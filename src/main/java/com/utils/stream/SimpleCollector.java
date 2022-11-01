package com.utils.stream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @Author Wang Junwei
 * @Date 2022/11/1 10:58
 * @Description
 *
 */
public class SimpleCollector<T, A, R> implements Collector<T, A, R> {
    @Override
    public Supplier<A> supplier() {
        return null;
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<A> combiner() {
        return null;
    }

    @Override
    public Function<A, R> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
