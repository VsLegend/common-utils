package com.utils.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Wang Junwei
 * @Date 2022/4/8 16:27
 * @Description
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutstandingStudent {

    private String name;

    private Integer age;

    private String clazz;

    private Integer score;

    private String course;

    public static String score(String name, String name1) {
        return name + name1;
    }

    public static OutstandingStudent sum(OutstandingStudent student1, OutstandingStudent student2) {
        student1.setScore(student1.getScore() + student2.getScore());
        return student1;
    }

    public static List<OutstandingStudent> getStudent() {
        return Stream.of(
                OutstandingStudent.builder().name("小张").age(16).clazz("高一1班").course("历史").score(88).build(),
                OutstandingStudent.builder().name("小李").age(16).clazz("高一3班").course("数学").score(12).build(),
                OutstandingStudent.builder().name("小王").age(17).clazz("高二1班").course("地理").score(44).build(),
                OutstandingStudent.builder().name("小红").age(18).clazz("高二1班").course("物理").score(67).build(),
                OutstandingStudent.builder().name("李华").age(15).clazz("高二2班").course("数学").score(99).build(),
                OutstandingStudent.builder().name("小潘").age(19).clazz("高三4班").course("英语").score(100).build(),
                OutstandingStudent.builder().name("小聂").age(20).clazz("高三4班").course("物理").score(32).build()
        ).collect(Collectors.toList());
    }
}
