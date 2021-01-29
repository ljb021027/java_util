package com.MyUtils.learn.sort.heaprec;


/**
 * @author ljb
 * @since 2018/12/30
 */
public class StackX {
    private int maxsize;
    private Params[] stackActivity;
    private int top;

    public StackX(int s){                 //对栈中的成员变量进行初始化
        maxsize = s;
        stackActivity = new Params[maxsize];
        top = -1;
    }

    public void Push(Params a){           //入栈操作
        stackActivity[++top] = a;
    }

    public Params Pop(){                 //出栈操作
        return stackActivity[top--];
    }

    public Params peek(){              //获取栈顶元素
        return stackActivity[top];
    }
}

