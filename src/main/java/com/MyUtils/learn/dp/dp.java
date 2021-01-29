package com.MyUtils.learn.dp;

/**
 * @author liujiabei
 * @date 2020/8/6 20:29
 */
public class dp {

    public static void main(String[] args) {
        int[] M = new int[]{3, 5, 6, 4};
        int[] N = new int[]{5, 6, 5, 7};

//        String s = ZeroOnePack(10, 4, M, N);
//        System.out.println(s);
        int i = ZeroOnePack2(10, 4, M, N);
        System.out.println(i);
    }

//    public static void main(String[] args) {
//        int[] M = new int[]{3555, 3557, 3592, 3336, 2852, 2026,
//                1708, 3405, 1742, 1358, 4272, 2654
//
//        };
//
//        int[] N = M;
//
////        String s = ZeroOnePack(10, 4, M, N);
////        System.out.println(s);
//        int i = ZeroOnePack2(10000, 5, M, N);
//        System.out.println(i);
//    }

    public static int ZeroOnePack2(int T, int X, int[] M, int[] N) {
        //动态规划
        int[] dp = new int[T + 1];
        for (int i = 1; i < X + 1; i++) {
            //逆序实现
            for (int j = T; j >= M[i - 1]; j--) {
                dp[j] = Math.max(dp[j - M[i - 1]] + N[i - 1], dp[j]);
            }
        }
        return dp[T];
    }

    /**
     * 0-1背包问题
     *
     * @param T 背包容量
     * @param X 物品种类
     * @param M 物品重量
     * @param N 物品价值
     * @return
     */
    public static String ZeroOnePack(int T, int X, int[] M, int[] N) {


        //初始化动态规划数组
        int[][] dp = new int[X + 1][T + 1];
        //为了便于理解,将dp[i][0]和dp[0][j]均置为0，从1开始计算
        for (int i = 1; i < X + 1; i++) {
            for (int j = 1; j < T + 1; j++) {
                //如果第i件物品的重量大于背包容量j,则不装入背包
                //由于weight和value数组下标都是从0开始,故注意第i个物品的重量为weight[i-1],价值为value[i-1]
                if (M[i - 1] > j)
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - M[i - 1]] + N[i - 1]);
            }
        }
        //则容量为V的背包能够装入物品的最大值为
        int maxValue = dp[X][T];
        //逆推找出装入背包的所有商品的编号
        int j = T;
        String numStr = "";
        for (int i = X; i > 0; i--) {
            //若果dp[i][j]>dp[i-1][j],这说明第i件物品是放入背包的
            if (dp[i][j] > dp[i - 1][j]) {
                numStr = i + " " + numStr;
                j = j - M[i - 1];
            }
            if (j == 0)
                break;
        }
        return numStr;
    }


    /**
     * 0-1背包问题
     * @param V 背包容量
     * @param N 物品种类
     * @param weight 物品重量
     * @param value 物品价值
     * @return
     */
//    public static String ZeroOnePack(int V,int N,int[] weight,int[] value){
//
//        //初始化动态规划数组
//        int[][] dp = new int[N+1][V+1];
//        //为了便于理解,将dp[i][0]和dp[0][j]均置为0，从1开始计算
//        for(int i=1;i<N+1;i++){
//            for(int j=1;j<V+1;j++){
//                //如果第i件物品的重量大于背包容量j,则不装入背包
//                //由于weight和value数组下标都是从0开始,故注意第i个物品的重量为weight[i-1],价值为value[i-1]
//                if(weight[i-1] > j)
//                    dp[i][j] = dp[i-1][j];
//                else
//                    dp[i][j] = Math.max(dp[i-1][j],dp[i-1][j-weight[i-1]]+value[i-1]);
//            }
//        }
//        //则容量为V的背包能够装入物品的最大值为
//        int maxValue = dp[N][V];
//        //逆推找出装入背包的所有商品的编号
//        int j=V;
//        String numStr="";
//        for(int i=N;i>0;i--){
//            //若果dp[i][j]>dp[i-1][j],这说明第i件物品是放入背包的
//            if(dp[i][j]>dp[i-1][j]){
//                numStr = i+" "+numStr;
//                j=j-weight[i-1];
//            }
//            if(j==0)
//                break;
//        }
//        return numStr;
//}

}
