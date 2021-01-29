package com.MyUtils.j8;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liujiabei
 * @since 2019/1/10
 */
public class StreamGroupBy {

    public static void main(String[] args) {
        List<People> peopleList = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            People people = new People();
            people.setName("ljb");
            people.setAge("25");
            people.setCount(i);
            peopleList.add(people);
        }
        for (int i = 0; i < 4; i++) {
            People people = new People();
            people.setName("ljb");
            people.setAge("24");
            people.setCount(i);
            peopleList.add(people);
        }

        Map<String, List<People>> collect = peopleList.stream().collect(Collectors.groupingBy(People::getUni));
        Set<String> strings = collect.keySet();
        peopleList.clear();
        for (String string : strings) {
            List<People> list = collect.get(string);
            list.sort((p1, p2)->p2.getCount()-p1.getCount());
            peopleList.add(list.get(0));
        }

        System.out.println(peopleList);
    }
}

class J8Sort{
    public static void main(String[] args){
        System.out.println("2019-01-01 02:00:00".compareTo("2019-01-01 01:00:00"));

        List<String> aaa = new ArrayList<>();
        aaa.add("2019-01-01 02:00:00");
        aaa.add("2019-01-01 03:00:00");
        aaa.add("2019-01-01 04:00:00");
        aaa.add("2019-01-01 05:00:00");
        aaa.sort((a1,a2)->a1.compareTo(a2));
        System.out.println(aaa);
    }
}
