package com.MyUtils.learn;

import java.util.List;

/**
 * @author ljb
 * @since 2019/2/18
 */
public class ReverseList {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        public String print() {
            String r = this.val + "";
            ListNode temp = next;
            while (temp != null) {
                r += "->" + temp.val;
                temp = temp.next;
            }
            return r;
        }
    }

    // 1.就地反转法
    public static ListNode reverseList1(ListNode head) {
        if (head == null)
            return head;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev = dummy.next;
        ListNode pCur = prev.next;
        while (pCur != null) {
            prev.next = pCur.next;
            pCur.next = dummy.next;
            dummy.next = pCur;
            pCur = prev.next;
        }
        return dummy.next;
    }

    public static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode now = head;
        while (now != null) {
            ListNode next = now.next;
            now.next = prev;
            prev = now;
            now = next;
        }
        return prev;
    }

    public static ListNode reverse2(ListNode head) {
        ListNode dummy = new ListNode(-1);
        //当前节点
        ListNode pCur = head;
        while (pCur != null) {
            //1,用临时节点保存下一个节点
            ListNode next = pCur.next;
            //2,断开"当前节点"和"下一个节点"
            pCur.next = dummy.next;
            //3,dummy下一个节点指向"当前节点"
            dummy.next = pCur;
            //4,当前节点移动到下一个节点
            pCur = next;
        }
        return dummy.next;
    }


    public static void main(String[] args) {
        ListNode listNode = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println(listNode.print());
//        ListNode listNode1 = reverseList1(listNode);
        ListNode listNode2 = reverse2(listNode);
        System.out.println(listNode2.print());

    }
}
