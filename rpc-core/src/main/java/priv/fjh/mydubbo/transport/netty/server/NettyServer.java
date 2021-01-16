package priv.fjh.mydubbo.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.provider.ServiceProvider;
import priv.fjh.mydubbo.provider.ServiceProviderImpl;
import priv.fjh.mydubbo.registry.ServiceRegistry;
import priv.fjh.mydubbo.registry.ZkServiceRegistry;
import priv.fjh.mydubbo.transport.RpcServer;
import priv.fjh.mydubbo.codec.CommonDecoder;
import priv.fjh.mydubbo.codec.CommonEncoder;
import priv.fjh.mydubbo.serializer.KryoSerializer;

import java.net.InetSocketAddress;

/**
 * @author fjh
 * @date 2020/12/31 9:35
 * @Description:
 */
@Slf4j
public class NettyServer implements RpcServer {

    private final String host;
    private final int port;
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
        serviceRegistry = new ZkServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        serviceProvider.addServiceProvider(service);
        serviceRegistry.registerService(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    @Override
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new CommonEncoder(new KryoSerializer()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture cf = serverBootstrap.bind(host, port).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("启动服务器时有错误发生: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
