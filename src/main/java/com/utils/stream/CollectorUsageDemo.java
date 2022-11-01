package com.utils.stream;

import com.utils.list.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Author Wang Junwei
 * @Date 2022/11/1 9:37
 * @Description
 */
public class CollectorUsageDemo {


    public <R, T, A> A execute(ExecutorService threadPool, Collection<T> data, Collector<T, R, A> collector) throws ExecutionException, InterruptedException {
        // 查询特征，判断是否要进行分段处理
        Set<Collector.Characteristics> characteristics = collector.characteristics();
        int segment = 1;
        if (characteristics.contains(Collector.Characteristics.CONCURRENT)) {
            segment = data.size() / Runtime.getRuntime().availableProcessors() + 1;
        }
        // 集合分段用于多线程，以便不会对统一数据多次计算
        Collection<Collection<T>> segmentList = ListUtil.segmentList(data, segment);
        // 初始化容器
        R r = collector.supplier().get();
        CompletableFuture<R> completableFuture = CompletableFuture.completedFuture(r);
        for (Collection<T> collection : segmentList) {
            completableFuture.thenApplyAsync(r1 -> {
                // 起初初始容器也将作为函数计算的一部分, 这里将容器合并，并返回新的容器
                R r2 = this.dealWithElement(collection, collector);
                return collector.combiner().apply(r1, r2);
            });
        }
        // 合并容器后的最终结果
        R last = completableFuture.get();
        // 将R转为最终的结果类型A
        return collector.finisher().apply(last);
    }


    public <R, T, A> R dealWithElement(Collection<T> data, Collector<T, R, A> collector) {
        // 初始化一个容器
        R container = collector.supplier().get();
        // 遍历data集合，将每个元素通过accumulator函数进行规约
        for (T t : data) {
            collector.accumulator().accept(container, t);
        }
        return container;
    }

}
