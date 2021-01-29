package com.MyUtils.learn;

/**
 * 找出唯一 唯二 未重复
 *
 * @author ljb
 * @since 2019/8/3
 */
public class FindRepeat {


    public static void main(String[] args) {
        System.out.println(51 ^ 59);
        System.out.println(8 ^ 59);

//        int[] arr = {19, 11, 56, 7, 19, 76, 7, 6, 6, 89, 45, 56, 76, 23, 89, 45, 23, 11, 3, 5};//8,51
        int[] arr = {3,5};//8,51
        //先获取异或值
        int xor = 0;
        for (int i : arr) {
            xor = xor ^ i;
        }
        //8^51 = 59
        System.out.println(xor);
        //获取特征值
        int checkInt = 1;
        while (true) {
            int checkRes = xor & checkInt;
            if (checkRes > 0) {
                break;
            } else {
                checkInt = checkInt << 1;
            }
        }

        //分组求值
        int first = 0;
        int second = 0;
        for (int i : arr) {
            if ((i & checkInt) > 0) {
                first = first ^ i;
            } else {
                second = second ^ i;
            }
        }
        System.out.println("first=" + first + ",second=" + second);
    }
}
