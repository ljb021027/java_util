package com.MyUtils.learn;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.DelayQueue;

/**
 * @author liujiabei
 * @date 2020/7/24 11:36
 */
public class TestNull {
    public static void main(String[] args) {

        allocateMoney(100, 10);
//
//        int[] ints = randomMoney(100, 10, 1, 30);
//        int sum = 0;
//        for (int anInt : ints) {
//            sum += anInt;
//        }
//        System.out.println(sum);
//        System.out.println(Arrays.toString(ints));

        BigDecimal[] bigDecimals = redPack(new BigDecimal(100), 10);
        int index = 0;
        BigDecimal total = new BigDecimal(0);
        for (BigDecimal bigDecimal : bigDecimals) {
            System.out.println(++index + ":" + bigDecimal.toString());
            total = total.add(bigDecimal);
        }
        System.out.println("all:" + total.toString());

    }

    //最少分得红包数
    private static final double min = 1;
    //最多分得红包数占比
    private static final double percentMax = 0.20;

    public static void allocateMoney(double money, int peopleNum) {
        double minMoney = min;
        double maxMoney = percentMax * money;
        int shareMoney = 0;
        double sum = 0;
        for (int i = 0; i < peopleNum; i++) {
            double v = money - maxMoney * (peopleNum - i - 1);
            double v1 = money - minMoney * (peopleNum - i - 1);
            minMoney = minMoney > v ? minMoney : v;
            maxMoney = maxMoney < v1 ? maxMoney : v1;
            System.out.println(minMoney+":"+maxMoney);
            shareMoney = (int) Math.floor((maxMoney - minMoney) * Math.random() + minMoney);
            money = money - shareMoney;
            sum += shareMoney;
            System.out.println("第" + (i + 1) + "个人抢到:" + shareMoney + "元");
        }
        System.out.println("要分配的红包总额为:" + sum + "元");
    }

    public static int[] randomMoney(double money, int peopleNum, double minMoney, double maxMoney) {
        int[] result = new int[10];
        double remaining = money;
        for (int i = 0; i < peopleNum; i++) {
            if (remaining < maxMoney) {
                maxMoney = remaining;
            }
            double v = (maxMoney - minMoney) * Math.random();
            int curMoney = (int) (v + minMoney);
            remaining = remaining - curMoney;
            result[i] = curMoney;
        }
        return result;
    }

    public static final int minPer = 1;
    public static final int maxPer = 20;

    public static BigDecimal[] redPack(BigDecimal totalMoney, int people) {
        if (minPer * people > 100) {
            throw new RuntimeException("minPer error");
        }
        BigDecimal[] result = new BigDecimal[people];
        BigDecimal min = new BigDecimal(minPer).divide(new BigDecimal(100)).multiply(totalMoney);
        BigDecimal remain = totalMoney;

        for (int i = 0; i < people; i++) {
            // 随机一个金额
            BigDecimal v = new BigDecimal(Math.random() * maxPer / 100).multiply(totalMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (v.compareTo(min) < 0) {
                v = min;
            }
            if (v.compareTo(remain) > 0) {
                v = remain;
            }
            BigDecimal totalMin = new BigDecimal(people - i - 1).multiply(min);
            if (remain.subtract(v).compareTo(totalMin) < 0) {
                result[i] = remain.subtract(totalMin);
                for (int j = i + 1; j < people; j++) {
                    result[j] = min;
                }
                break;
            }
            result[i] = v;
            remain = remain.subtract(v);

        }
        return result;
    }
}
