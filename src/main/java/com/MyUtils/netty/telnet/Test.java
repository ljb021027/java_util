package com.MyUtils.netty.telnet;

/**
 * @author ljb
 * @since 2018/11/18
 */
public class Test {

    public static void main(String[] args) {
        NettyTelnetServer nettyTelnetServer = new NettyTelnetServer();
        try {
            nettyTelnetServer.open();
        } catch (InterruptedException e) {
            nettyTelnetServer.close();
        }
    }
}
