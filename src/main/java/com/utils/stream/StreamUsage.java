package com.utils.stream;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Wang Junwei
 * @Date 2022/4/8 16:30
 * @Description
 */
public class StreamUsage {

    public static void main(String[] args) {
        List<Student> students = Student.getStudent();

        // 检视元素
        List<String> peek = students.stream()
                .filter(s -> s.getScore() > 60)
                .peek(System.out::println)
                .map(Student::getName)
                .peek(System.out::println)
                .collect(Collectors.toList());
        System.out.println(peek);

        // 流转集合
        List<Student> studentList = students.stream().collect(Collectors.toList());
        // 流转map
        HashMap<String, Student> map = students.stream().collect(HashMap::new, (hashMap, student) -> hashMap.put(student.getName(), student), HashMap::putAll);
        System.out.println(studentList);
        System.out.println(map);

        // 获取学生姓名的新列表
        List<String> names = students.stream().map(Student::getName).collect(Collectors.toList());
        List<Integer> ages = students.stream().mapToInt(Student::getAge).boxed().collect(Collectors.toList());
        System.out.println(names);
        System.out.println(ages);

        // 只获取高一的数学成绩
        List<Student> math = students.stream()
                .filter(student -> student.getClazz().contains("高一") && "数学".equals(student.getCourse()))
                .collect(Collectors.toList());
        System.out.println(math);


        // 取第二页，每页五条数据
        List<Student> page = students.stream().skip(5).limit(5).collect(Collectors.toList());
        System.out.println(page);

        // 按成绩排序，成绩相同时根据年龄排倒序
        List<Student> sorted = students.stream()
                .sorted(Comparator.comparing(Student::getScore, Comparator.reverseOrder()).thenComparing(Student::getAge, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        // 普通写法
        List<Student> sorted2 = students.stream()
                .sorted((c1, c2) -> {
                    if (c1.getScore() > c2.getScore()) {
                        return -1;
                    } else if (c1.getScore().equals(c2.getScore())) {
                        if (c1.getAge()>c2.getAge()) {
                            return -1;
                        } else if (c1.getAge() < c2.getAge()){
                            return 1;
                        }
                        return 0;
                    }
                    return 1;
                })
                .collect(Collectors.toList());

        System.out.println(sorted);
        System.out.println(sorted2);

        // 分数的最大值 最小值
        Student max = students.stream().max(Comparator.comparing(Student::getScore)).orElse(null);
        Student min = students.stream().min(Comparator.comparing(Student::getScore)).orElse(null);
        // 如下写法获取最大值，这总写法只能获取到值，没法关联用户，跟上面的写法各有用途
        int max2 = students.stream().mapToInt(Student::getScore).max().orElse(0);
        int min2 = students.stream().mapToInt(Student::getScore).min().orElse(0);
        System.out.println(max);
        System.out.println(min);

        // 去重 统计所有科目
        List<String> courses = students.stream().map(Student::getCourse).distinct().collect(Collectors.toList());
        System.out.println(courses);


        // 计数求及格的学生人数 求分数总和，以及分数的平均值
        long count = students.stream().filter(student -> student.getScore() > 60).count();
        Integer sum = students.stream().map(Student::getScore).reduce(Integer::sum).orElse(-1);
        Integer sum2 = students.stream().mapToInt(Student::getScore).sum();
        double average = students.stream().mapToDouble(Student::getScore).average().orElse(0D);
        System.out.println(count + "-" + sum + "-" + average);


        // 合并所有姓名
        String reduce = students.stream().reduce(new StringBuffer(), (result, student) -> result.append(student.getName()), (r1, r2) -> r1.append(r2.toString())).toString();
        // 简单写法，通过map和reduce操作的显式组合，能更简单的表示
        String reduce1 = students.stream().map(Student::getName).reduce(String::concat).orElse("");
        System.out.println(reduce);

        // 将不同课程的学生进行分类
        HashMap<String, List<Student>> groupByCourse = (HashMap<String, List<Student>>)students.stream()
                .collect(Collectors.groupingBy(Student::getCourse));
        // 上面的方法中，最终返回默认是HashMap，键值对中的值默认是ArrayList，可以通过下面的方法自定义返回结果、值的类型
        HashMap<String, List<Student>> groupByCourse1 = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, HashMap::new, Collectors.toList()));
        // 增加映射功能
        HashMap<String, List<String>> groupMapping = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, HashMap::new, Collectors.mapping(Student::getName, Collectors.toList())));
        // 增加规约函数，计算每科总分
        HashMap<String, Integer> groupCalcSum = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, HashMap::new, Collectors.reducing(0, Student::getScore, Integer::sum)));
        // 增加平均值计算
        HashMap<String, Double> groupCalcAverage = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, HashMap::new, Collectors.averagingDouble(Student::getScore)));
        System.out.println(groupByCourse);
        System.out.println(groupMapping);
        System.out.println(groupCalcSum);
        System.out.println(groupCalcAverage);
    }

}
