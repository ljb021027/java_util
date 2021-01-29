package com.MyUtils.learn.all.array;

import java.util.TreeMap;

/**
 * @author liujiabei
 * @date 2020/6/21 13:00
 */
public class MyLinkedList {
    int val;
    MyLinkedList prev;
    MyLinkedList next;

    public static void main(String[] args) {
        MyLinkedList one = new MyLinkedList(1);
        MyLinkedList two = new MyLinkedList(2);
        MyLinkedList three = new MyLinkedList(3);

        one.next = two;
        one.prev = three;
        two.next = three;
        two.prev = one;
        three.next = one;
        three.prev = two;
        System.out.println(one.print());

        one.addAtHead(0);
        System.out.println(one.print());



    }

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList(int val) {
        this.val = val;

    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index < 0) {
            return -1;
        }
        int i = 0;
        MyLinkedList cur = this;
        while (i > 0 && cur == this) {
            if (i++ == index) {
                return cur.val;
            }
            cur = this.next;
        }
        return -1;

    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        MyLinkedList src = new MyLinkedList(this.val);
        src.next = this.next;
        src.prev = this.prev;

        this.val = val;
        this.next = src;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        MyLinkedList temp = new MyLinkedList(val);
        temp.prev = this.prev;
        temp.next = this;
        this.prev.next = temp;

    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {

    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {

    }

    public String print(){
        MyLinkedList cur = this;
        StringBuilder sb = new StringBuilder();
        while (cur.next != this){
            sb.append(cur.val);
            sb.append("->");
            cur = cur.next;
        }
        sb.append(cur.val);

        return sb.toString();
    }
}
