package com.MyUtils.learn.queue;

/**
 * 循环数组实现队列
 *
 * @author ljb
 * @since 2018/12/9
 */
public class CircularArrayQueue<T> {
    private Object[] items;
    private int size;
    private int head = 0;
    private int tail = 0;


    public CircularArrayQueue(int size) {
        items = new Object[size];
        this.size = size;
    }

    //入队
    public boolean enqueue(T t) {
        int index = (tail + 1) % size;
        if (index == head) {
            return false;
        }
        items[tail] = t;
        tail = index;
        return true;
    }


    //出队
    public T dequeue() {
        if (head == tail) {
            return null;
        }
        Object item = items[head];
        head = (head + 1) % size;
        return (T) item;
    }

    public static void main(String[] args){
        CircularArrayQueue<String> arrayQueue = new CircularArrayQueue<>(4);
        arrayQueue.enqueue("1");
        String dequeue = arrayQueue.dequeue();
        System.out.println(dequeue);
        arrayQueue.enqueue("2");
        String dequeue2 = arrayQueue.dequeue();
        System.out.println(dequeue2);
        arrayQueue.enqueue("3");
        String dequeue3 = arrayQueue.dequeue();
        System.out.println(dequeue3);

    }
}
