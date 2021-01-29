package com.MyUtils.j8;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ljb
 * @since 2018/12/30
 */
public class For {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5000000; i++) {
            list.add("ljb");
        }

        long start = System.currentTimeMillis();
        //21853ms
//        list.forEach(s -> {
//            System.out.println(s);
//        });

        //21850
//        for (String s : list) {
//            System.out.println(s);
//        }

        //22453
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        //22237
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

        long end = System.currentTimeMillis() - start;
        System.out.println("time:" + end);

    }
}


class Test2{
    public static void main(String[] args){
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 5000000; i++) {
            list.add("ljb");
        }

        long start = System.currentTimeMillis();
        //26661
//        list.forEach(s -> {
//            System.out.println(s);
//        });

        //25304
//        for (String s : list) {
//            System.out.println(s);
//        }

        //++++++++
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
        //24218
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        long end = System.currentTimeMillis() - start;
        System.out.println("time:" + end);
    }
}

