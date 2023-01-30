package com.utils.stream;

import cn.hutool.core.bean.BeanUtil;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @Author Wang Junwei
 * @Date 2022/10/26 15:07
 * @Description 分组相关
 */
public class StreamGroupByUsage {

    public static void main(String[] args) {
        List<Student> students = Student.getStudent();
        // 自定义键
        // 字段映射
        Map<String, List<Student>> filedKey = students.stream().collect(Collectors.groupingBy(Student::getCourse));
        // 组合字段
        Map<String, List<Student>> combineFiledKey = students.stream().collect(Collectors.groupingBy(student -> student.getClazz() + "#" + student.getCourse()));

        // 自定义键的生成规则
        // 根据两级范围
        Map<Boolean, List<Student>> customRangeKey = students.stream().collect(Collectors.groupingBy(student -> student.getScore() > 60));
        // 根据多级范围
        Map<String, List<Student>> customMultiRangeKey = students.stream().collect(Collectors.groupingBy(student -> {
            if (student.getScore() < 60) {
                return "C";
            } else if (student.getScore() < 80) {
                return "B";
            }
            return "A";
        }));


        // 将不同课程的学生进行分类
        Map<String, List<Student>> groupByCourse = students.stream().collect(Collectors.groupingBy(Student::getCourse));
        Map<String, List<Student>> groupByCourse1 = students.stream().collect(Collectors.groupingBy(Student::getCourse, Collectors.toList()));
        // 上面的方法中，最终返回默认是HashMap，键值对中的值默认是ArrayList，可以通过下面的方法自定义返回结果、值的类型
        Map<String, List<Student>> groupByCourse2 = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, HashMap::new, Collectors.toList()));
        // 增加平均值计算
        Map<String, Double> groupAverage = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.averagingLong(Student::getScore)));


        // 求和
        Map<String, Integer> groupSum = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.summingInt(Student::getScore)));
        // 计数
        Map<String, Long> groupCount = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.counting()));
        // 同组最小值
        Map<String, Optional<Student>> groupMin = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.minBy(Comparator.comparing(Student::getCourse))));
        Map<String, Student> groupMin2 = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.collectingAndThen(Collectors.minBy(Comparator.comparing(Student::getCourse)), op -> op.orElse(null))));
        // 同组最大值
        Map<String, Optional<Student>> groupMax = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.maxBy(Comparator.comparing(Student::getCourse))));
        // 同时统计同组的最大值、最小值、计数、求和、平均数信息
        Map<String, DoubleSummaryStatistics> groupStat = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.summarizingDouble(Student::getScore)));
        groupStat.forEach((k, v) -> {
            double average = v.getAverage();
            v.getCount();
            v.getMax();
            v.getMin();
            v.getSum();
        });

        // 切分结果，同时统计大于60和小于60分的人的信息
        Map<String, Map<Boolean, List<Student>>> groupPartition = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.partitioningBy(s -> s.getScore() > 60)));
        Map<String, Map<Boolean, Long>> groupPartitionCount = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.partitioningBy(s -> s.getScore() > 60, Collectors.counting())));
        // 增加映射功能
        Map<String, List<String>> groupMapping = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.mapping(Student::getName, Collectors.toList())));
        Map<String, List<OutstandingStudent>> groupMapping2 = students.stream()
                .filter(s -> s.getScore() > 60)
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.mapping(s -> BeanUtil.copyProperties(s, OutstandingStudent.class), Collectors.toList())));
        // 增加规约函数，计算每科的最高分学生的信息
        Map<String, Optional<Student>> groupCourseMax = students.stream().parallel()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Student::getScore)))));
        // 增加规约函数，计算每科总分
        Map<String, Integer> groupCalcSum = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.reducing(0, Student::getScore, Integer::sum)));
        // 统计各科的学手姓名
        Map<String, String> groupMapperJoin = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.mapping(Student::getName, Collectors.joining())));
        Map<String, String> groupMapperThenLink = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse,
                        Collectors.collectingAndThen(Collectors.mapping(Student::getName, Collectors.joining("，")), s -> "学生名单：" + s)));
        System.out.println(groupMapperThenLink);



    }

}
