package com.MyUtils.learn.leetcode;

/**
 * @author ljb
 * @since 2019/6/7
 */
public class Solution {

    public static int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        return pathSum(root.left, sum) + pathSum(root.right, sum) + f1(root, sum);

    }

    static int f1(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        if (root.val == sum) {
            return 1 + f1(root.left, 0) + f1(root.right, 0);
        }
        return f1(root.left, sum - root.val) + f1(root.right, sum - root.val);
    }


    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(10);
        TreeNode t2 = new TreeNode(5);
        TreeNode t3 = new TreeNode(-3);
        TreeNode t4 = new TreeNode(3);
        TreeNode t5 = new TreeNode(2);
        TreeNode t6 = new TreeNode(11);
        TreeNode t7 = new TreeNode(3);
        TreeNode t8 = new TreeNode(-2);
        TreeNode t9 = new TreeNode(1);


        t1.left = t2;
        t1.right = t3;

        t2.left = t4;
        t2.right = t5;

        t3.right = t6;

        t4.left = t7;
        t4.right = t8;

        t5.right = t9;

        pathSum(t1, 8);


    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}
