package com.MyUtils.netty.telnet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author ljb
 * @since 2018/11/18
 */
public class NettyTelnetServer {

    private ServerBootstrap serverBootstrap;

    private static final int PORT = 8888;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void open() throws InterruptedException {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipe = ch.pipeline();
                        // 如果客户端60秒没有任何请求，就关闭客户端链接
                        pipe.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        pipe.addLast(new StringDecoder());
                        pipe.addLast(new StringEncoder());
                        // 将业务处理器放在最后
                        pipe.addLast(new NettyTelnetHandler());
                    }
                });

        Channel channel = serverBootstrap.bind(PORT).sync().channel();
        channel.closeFuture().sync();


    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }


}
