package com.MyUtils.learn.queue;

/**
 * 数组实现队列
 *
 * @author ljb
 * @since 2018/12/9
 */
public class ArrayQueue<T> {

    private Object[] items;
    private int size;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int size) {
        this.size = size;
        items = new Object[size];
    }

    //入队
    public boolean enqueue(T t) {
        if (tail == size) {
            if (head == 0){
                return false;
            }
            for (int i=head;i<tail;i++){
                items[i - head] = items[i];
            }
            head=0;
            tail -= head;
        }
        items[tail++] = t;
        return true;
    }


    //出队
    public T dequeue() {
        if (head == tail) {
            return null;
        }
        return (T) items[head++];
    }

    public static void main(String[] args){
        ArrayQueue<String> arrayQueue = new ArrayQueue<>(4);
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
