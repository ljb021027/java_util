package com.MyUtils.learn.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author ljb
 * @since 2018/11/15
 */
public class MergeSortArray {

    private Iterator<Integer> array1;

    private Iterator<Integer> array2;

    public List<Integer> out = new ArrayList<>();

    public Integer temp;

    private static final Integer MAX = 1000000;

    MergeSortArray(Iterator<Integer> array1, Iterator<Integer> array2) {
        this.array1 = array1;
        this.array2 = array2;
    }

    public boolean hasNext() {
        return array1.hasNext() || array2.hasNext();
    }

    public Integer next() {
        Integer next1 = null;
        if (array1.hasNext()) {
            next1 = array1.next();
        }
        Integer next2 = null;
        if (array2.hasNext()) {
            next2 = array2.next();
        }
        Integer low;
        Integer high;
        if (next1 == null) {
            return next2;
        }
        if (next1 > next2) {
            low = next2;
            high = next1;
        } else {
            low = next1;
            high = next2;
        }

        if (temp != null && temp > low) {
            out.add(out.size() - 1, low);

        } else {
            temp = high;
            out.add(low);
        }

        out.add(high);
//        if (next1 > next2) {
//            temp = next1;
//            return next1 * MAX + next2;
//        } else {
//            temp = next2;
//            return next2 * MAX + next1;
//        }
        return next1 != null ? next1 : next2;
    }

    public static void main(String[] args) {
        Iterator<Integer> array1 = Arrays.asList(1, 2, 3, 5, 7, 11).iterator();
        Iterator<Integer> array2 = Arrays.asList(2, 4, 6, 8, 9).iterator();
        MergeSortArray sortArray = new MergeSortArray(array2, array1);
//        Iterable<Integer> outArray = new ArrayList<>();
//        while (sortArray.hasNext()) {
//            Integer next = sortArray.next();
//            Integer low = next % MAX;
//            Integer high = next / MAX;
//            if (sortArray.temp != null && sortArray < high) {
//
//            }
//            ((ArrayList<Integer>) outArray).add(low);
//            ((ArrayList<Integer>) outArray).add(high);
//
//        }

        while (sortArray.hasNext()) {
            sortArray.next();
        }

        List<Integer> outArray = sortArray.out;
        for (Integer integer : outArray) {
            System.out.println(integer);
        }
    }


}



