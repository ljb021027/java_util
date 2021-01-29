package com.MyUtils.rpc;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }

}
