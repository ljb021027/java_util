package com.MyUtils;

/**
 * @author liujiabei
 * @date 2020/9/9 15:42
 */
public class main {

    public static void main(String[] args) {
        System.out.println(1);

        int a = 0x440;
        int a1 = 0x70;
        char b = (char)a;
        char b1 = (char)a1;
        char c = "p".charAt(0);
        System.out.println("".equals("p"));
        System.out.println(b);
        System.out.println(b1);

        for (int i = 110; i < 150; i++) {
            System.out.println(i + " " +(char)i);
        }
        for (int i = 1070; i < 1100; i++) {
            System.out.println(i + " " +(char)i);
        }
    }
}
