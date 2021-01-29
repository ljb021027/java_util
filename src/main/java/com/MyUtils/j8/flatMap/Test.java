package com.MyUtils.j8.flatMap;

import com.MyUtils.j8.People;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 多个list 合并去重
 *
 * @author liujiabei
 * @since 2019/8/29
 */
public class Test {

    public static void main(String[] args) {
        List<People> list1 = new ArrayList<>();
        list1.add(new People("ljb", "26", 1));
        list1.add(new People("qwe", "26", 1));
        list1.add(new People("asd", "26", 1));
        list1.add(new People("zxc", "26", 1));


        List<People> list2 = new ArrayList<>();
        list2.add(new People("ljb", "26", 1));
        list2.add(new People("1", "26", 1));
        list2.add(new People("2", "26", 1));
        list2.add(new People("3", "26", 1));

        //重写了对象的equals和hashcode 可以直接用.distinct()
        List<People> collect = Stream.of(list1, list2)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(collect);

        //不需要重写的办法
        List<People> collect1 = Stream.concat(list1.stream(), list2.stream()).filter(distinctByKey(p -> p.getName())).collect(Collectors.toList());
        System.out.println(collect1);

    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }
}

