package com.MyUtils.learn.tree;

import java.util.*;

/**
 * 原二叉树：
 * 4
 * /   \
 * 2     7
 * / \   / \
 * 1   3 6   9
 * <p>
 * 反转后的二叉树：
 * <p>
 * 4
 * /   \
 * 7     2
 * / \   / \
 * 9   6 3   1
 *
 * @author liujiabei
 * @date 202024 13:26
 */
public class ReverseTree {


    public static void main(String[] args) {
        TreeNode2 tn = new TreeNode2(4, new TreeNode2(2, new TreeNode2(1, null, null), new TreeNode2(3, null, null)), new TreeNode2(7, new TreeNode2(6, null, null), new TreeNode2(9, null, null)));
        List<Integer> integers = tn.preEach();
        System.out.println(integers);

        tn.preEach2(tn);
    }

    public TreeNode2 reverse(TreeNode2 tn) {
        if (tn == null) {
            return null;
        }
        reverse(tn.leftNode);
        reverse(tn.rightNode);
        TreeNode2 tmp = tn.leftNode;
        tn.leftNode = tn.rightNode;
        tn.rightNode = tmp;
        return tn;
    }


}


class TreeNode2 {
    int val;
    TreeNode2 leftNode;
    TreeNode2 rightNode;

    public TreeNode2(int val, TreeNode2 leftNode, TreeNode2 rightNode) {
        this.val = val;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * 用栈 循环前序遍历
     * @return
     */
    public List<Integer> preEach() {
        List<Integer> result = new ArrayList<>();
        LinkedList<TreeNode2> stack = new LinkedList<>();
        TreeNode2 cur = this;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                result.add(cur.val);
                stack.push(cur);
                cur = cur.leftNode;
            } else {
                TreeNode2 pop = stack.pop();
                cur = pop.rightNode;
            }
        }
        return result;
    }

    /**
     * 递归前序遍历
     * @param tn
     */
    public void preEach2(TreeNode2 tn) {
        System.out.println(tn.val);
        if (tn.leftNode != null) {
            preEach2(tn.leftNode);
        }
        if (tn.rightNode != null) {
            preEach2(tn.rightNode);
        }
    }

//    public void aa() {
//        LinkedList<TreeNode> stack = new LinkedList<>();
//        TreeNode pNode = root;
//        while (pNode != null || !stack.isEmpty()) {
//            if (pNode != null) {
//                System.out.print(pNode.val + "  ");
//                stack.push(pNode);
//                pNode = pNode.left;
//            } else { //pNode == null && !stack.isEmpty()
//                TreeNode node = stack.pop();
//                pNode = node.right;
//            }
//        }
//
//    }

}


