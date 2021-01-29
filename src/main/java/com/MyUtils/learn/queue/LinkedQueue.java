package com.MyUtils.learn.queue;

/**
 * 链表实现队列
 *
 * @author ljb
 * @since 2018/12/9
 */
public class LinkedQueue<T> {

    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T item;
        Node<T> next;
//        Node<T> prev;
    }

    public boolean enqueue(T t) {
        Node node = new Node();
        node.item = t;
        if (head == null) {
            head = node;
            tail = node;
            return true;
        }
        tail.next = node;
        tail = node;
        return true;
    }

    public T dequeue() {
        if (head == null) {
            return null;
        }
        Node node = head;
        head = head.next;
        //防止内存泄漏
        if (head == null) {
            tail = null;
        }
        return (T) node.item;
    }

    public static void main(String[] args) {
        LinkedQueue<String> linkedQueue = new LinkedQueue<>();
        linkedQueue.enqueue("ljb");
        linkedQueue.enqueue("ljb1");
        linkedQueue.enqueue("ljb2");
        String dequeue = linkedQueue.dequeue();
        System.out.println(dequeue);
        String dequeue1 = linkedQueue.dequeue();
        System.out.println(dequeue1);
        String dequeue2 = linkedQueue.dequeue();
        System.out.println(dequeue2);
        String dequeue3 = linkedQueue.dequeue();
        System.out.println(dequeue3);


    }


}
