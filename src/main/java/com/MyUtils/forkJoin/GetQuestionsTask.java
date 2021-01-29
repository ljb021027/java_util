package com.MyUtils.forkJoin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by ljb on 2018/1/16.
 */
public class GetQuestionsTask extends RecursiveTask<List> {

    //参数map
    private Map map;
    //参数list==只放题型
    private List questionTypeList;

    public GetQuestionsTask(List questionTypeList,Map map) {
        this.questionTypeList = questionTypeList;
        this.map=map;
    }

    @Override
    protected List compute() {
//        System.out.println(questionTypeList.size());
        List list = new ArrayList();
        if (questionTypeList.size() < 2) {
            // 抽题
            list = getQuestions(questionTypeList,map);
        } else {
            int size = questionTypeList.size();
            int mid = size / 2;
            GetQuestionsTask task1 = new GetQuestionsTask(
                    questionTypeList.subList(0, mid),map);
            GetQuestionsTask task2 = new GetQuestionsTask(
                    questionTypeList.subList(mid, size),map);
            invokeAll(task1, task2);
            try {
                list = groupResults(task1.get(), task2.get());
            } catch (InterruptedException e) {

                e.printStackTrace();
            } catch (ExecutionException e) {

                e.printStackTrace();
            }
        }

        return list;
    }

    //合并抽题结果
    private List groupResults(List list1, List list2) {
        System.out.println(Thread.currentThread().getName()+"开始合并结果......");
        // 合并返回结果
        List list = new ArrayList();
        list.addAll(list1);
        list.addAll(list2);
        System.out.println(Thread.currentThread().getName()+"合并结果结束......");
        return list;
    }

    // 抽题
    private List getQuestions(List questTypeList,Map map) {
        List list = new ArrayList();
        for(int i=0;i<questTypeList.size();i++){
            System.out.println(Thread.currentThread().getName()+"开始抽题......"+questionTypeList.get(i).toString());
            //假数据，向list中放试题
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"抽题结束..."+questionTypeList.get(i).toString());
        }
        return list;
    }

    public void execute(){
        //该池的线程数量不会超过0*7fff个（32767）
        //池中维护着ForkJoinWorkerThread对象数组，数组大小由parallelism属性决定，parallelism默认为处理器个数
        ForkJoinPool pool = new ForkJoinPool();
        GetQuestionsTask task = new GetQuestionsTask(questionTypeList, map);
        pool.execute(task);
        // 试题列表=task.get()
        try {
            List finalList = task.get();
            System.out.println("最终结果个数：" + finalList.size());
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        Map<String, Object> map = new HashMap<String, Object>();
        GetQuestionsTask task1 = new GetQuestionsTask(
                list,map);
        task1.execute();
    }

}
