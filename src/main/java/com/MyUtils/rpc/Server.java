package com.MyUtils.rpc;

import java.io.IOException;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public interface Server {
    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    public int getPort();
}
