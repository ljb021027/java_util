package com.MyUtils.thread;

import java.util.List;

/**
 * @author liujiabei
 * @since 2018/11/5
 */
public interface ThreadPool {

    void execute(Runnable task);

    void execute(Runnable[] task);

    void execute(List<Runnable> task);

    //返回执行任务的个数
    int getExecuteTaskNumber();

    //返回任务队列的长度 即还没处理的任务个数
    int getWaitTaskNumber();

    //返回工作线程的个数
    int getWorkThreadNumber();

    //关闭线程池
    void destory();

}
