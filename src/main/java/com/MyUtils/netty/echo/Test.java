package com.MyUtils.netty.echo;

/**
 * @author ljb
 * @since 2018/11/18
 */
public class Test {

    public static void main(String[] args) throws Exception {
        EchoServer.main(new String[] {"8888"});
    }
}

class TestClient{
    public static void main(String[] args) throws Exception {
        EchoClient.main(new String[] {"localhost","8888"});
    }
}
