package com.MyUtils.xml;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author liujiabei
 * @since 2019/5/17
 */
public class Student {

    public static void main(String[] args){
        String str = "[\"执行董事\",\"总经理\",\"法定代表人\"]";
        System.out.println(str.split(",").length);
    }
}
