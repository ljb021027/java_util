package com.MyUtils.learn.leetcode;

/**
 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
 * <p>
 * 示例: 
 * <p>
 * 输入: s = 7, nums = [2,3,1,2,4,3]
 * 输出: 2
 * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
 * 进阶:
 * <p>
 * 如果你已经完成了O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-size-subarray-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author liujiabei
 * @since 2019/7/15
 */
public class minimum {

//    public static int minSubArrayLen(int s, int[] nums) {
//        int min = 0;
//        int start = 0;
//        int end = 1;
//        int sum = 0;
//        while (start <= nums.length - 2) {
//            if (end == nums.length) {
//                start++;
//                end = start + 1;
//                continue;
//            }
//            sum += nums[end++];
//            if (sum >= s) {
//                if (min==0){
//                    min = end - start + 1;
//                }else{
//                    min = (end - start + 1) < min ? (end - start + 1) : min;
//                }
//                sum -= nums[start++];
//            }
//        }
//        return min;
//    }


    //2,3,1,2,4,3
    public static int minSubArrayLen(int s, int[] nums) {

        int n = nums.length;

        // i: 左边界，j: 右边界，sum: 窗口和，result: 返回值
        int i = 0, j = 0, sum = 0, result = n;


        // 由于左边界必须小于右边界（i <= j ），判断条件可写为 j < n
        while(j < n) {

            // sum = [i,j) 的和，所以需要加上j的值
            if(sum + nums[j]< s){
                sum += nums[j++];

            }else {

                // i、j为下标，故需要 + 1
                result = Math.min(result, j - i + 1);
                sum -= nums[i++];
            }
        }
        return result;
    }

    public static void main(String[] args) {

        int s = 7;
        int[] nums = new int[]{2, 3, 1, 2, 4, 3};
        int i = minSubArrayLen(s, nums);
        System.out.println(i);
    }

}
