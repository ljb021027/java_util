package com.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 11
 * Created by ljb on 2017/8/21.
 */
public class Test1 {

    public static void main(String[] args) {
        getAns(14);
    }

    public List<Integer> totalSum(Integer n){
        List<Integer> list = new ArrayList<Integer>();
        int mix = 1;
        int max = 1;
        int sum = 1;

        while(true){
            if(sum == n){

            }

            if(sum < n){
                max++;
                sum = sum + max;
            }else{

            }
        }

    }

    public static void getAns(int n) {
        int min = 1;
        int sum = 1;
        int max = 1;
        while(min <= n/2+1) {
            if(sum == n) {
                for(int k = min; k<=max; k++) {
                    System.out.println(k+"");
                }
                System.out.println();
                sum = sum - min;
                min ++;
                max ++;
                sum = sum+max;
            }
            if(sum > n) {
                sum = sum - min;
                min ++;
            } else {
                max ++;
                sum = sum + max;
            }
        }
    }
}
