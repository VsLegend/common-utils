package com.utils.stream;

import com.utils.list.ListUtil;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collector;

/**
 * @Author Wang Junwei
 * @Date 2022/11/1 9:37
 * @Description
 */
public class CollectorUsageDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Student> student = Student.getStudent();
        // Collectors.joining()
        Set<Collector.Characteristics> characteristics = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED));
        Collector<Student, AtomicInteger, Integer> collector = new SimpleCollector<>(AtomicInteger::new, (AtomicInteger i, Student s) -> i.addAndGet(s.getAge()),
                (i, i1) -> {
                    i.addAndGet(i1.get());
                    return i;
                }, AtomicInteger::get, characteristics);
        Integer execute = execute(executorService, student, collector);
        System.out.println(execute);

    }

    public static <T, R, A> A execute(ExecutorService threadPool, Collection<T> data, Collector<T, R, A> collector) throws ExecutionException, InterruptedException {
        Objects.requireNonNull(threadPool, "threadPool");
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(collector, "collector");
        // 查询特征，判断是否要进行分段处理
        Set<Collector.Characteristics> characteristics = collector.characteristics();
        int segment = 1;
        if (characteristics.contains(Collector.Characteristics.CONCURRENT)) {
            segment = data.size() / Runtime.getRuntime().availableProcessors() + 1;
        }
        // 集合分段用于多线程，以便不会对同一数据多次计算
        Collection<Collection<T>> segmentList = ListUtil.segmentList(data, segment);
        List<CompletableFuture<R>> completableFutureList = new ArrayList<>(segmentList.size());
        for (Collection<T> collection : segmentList) {
            // 并发情况下就不能保证累积函数执行的顺序，也就无法保证最终结果的顺序性（ForEachOrderedTask | ForEachTask）
            CompletableFuture<R> async = CompletableFuture.supplyAsync(() -> {
                return CollectorUsageDemo.dealWithElement(collection, collector);
            });
            completableFutureList.add(async);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
        CompletableFuture<R> result = allOf.thenApply(v -> {
            // 初始化容器 起初初始容器也将作为函数计算的一部分, 这里将容器合并，并返回新的容器
            R r = collector.supplier().get();
            for (CompletableFuture<R> f1 : completableFutureList) {
                R r2 = f1.join();
                r = collector.combiner().apply(r, r2);
            }
            return r;
        });
        // 合并容器后的最终结果
        R last = result.get();
        if (characteristics.contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return (A) last;
        }
        // 将R转为最终的结果类型A
        return collector.finisher().apply(last);
    }


    public static <T, R, A> R dealWithElement(Collection<T> data, Collector<T, R, A> collector) {
        // 初始化一个容器
        R container = collector.supplier().get();
        // 遍历data集合，将每个元素通过accumulator函数进行规约
        for (T t : data) {
            collector.accumulator().accept(container, t);
        }
        return container;
    }

}
