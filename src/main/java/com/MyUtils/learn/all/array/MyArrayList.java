package com.MyUtils.learn.all.array;

import com.alipay.api.domain.AlipaySecurityRiskRainscoreQueryModel;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liujiabei
 * @date 2020/6/6 21:39
 */
public class MyArrayList<T> {

    private Object[] array;
    private int size;


    public void add(T t) {
        int newSize = size + 1;
        if (newSize > array.length) {
            int newCapacity = size + (size >> 1);
            array = Arrays.copyOf(array, newCapacity);
        }
        this.array[this.size++] = t;
    }

    public void remove(T t) {
        for (int index = 0; index < size; index++) {
            int numMoved = size - index - 1;
            System.arraycopy(array, index + 1, array, index, numMoved);
        }
    }

    public T get(int index) {
        return (T) array[index];
    }

    public MyArrayList(int size) {
        this.size = 0;
        this.array = new Object[size];

    }

    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>(2);
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("5");
        list.add("6");
        list.add("7");


        String s = list.get(2);
        System.out.println(s);

    }
}
