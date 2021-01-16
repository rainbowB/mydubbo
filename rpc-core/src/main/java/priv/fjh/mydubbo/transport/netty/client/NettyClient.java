package priv.fjh.mydubbo.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.codec.CommonDecoder;
import priv.fjh.mydubbo.codec.CommonEncoder;
import priv.fjh.mydubbo.dto.RpcRequest;
import priv.fjh.mydubbo.dto.RpcResponse;
import priv.fjh.mydubbo.registry.ServiceRegistry;
import priv.fjh.mydubbo.registry.ZkServiceRegistry;
import priv.fjh.mydubbo.serializer.KryoSerializer;
import priv.fjh.mydubbo.transport.RpcClient;
import priv.fjh.mydubbo.utils.checker.RpcMessageChecker;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author fjh
 * @date 2020/12/31 9:35
 * @Description:
 */
@Slf4j
public class NettyClient implements RpcClient {

    private static final Bootstrap bootstrap;
    private final ServiceRegistry serviceRegistry;

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new KryoSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    public NettyClient() {
        serviceRegistry = new ZkServiceRegistry();
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        AtomicReference<Object> atomicReference = new AtomicReference<>(null);
        try {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            ChannelFuture future = bootstrap.connect(inetSocketAddress).sync();
            log.info("客户端连接到服务器 {}", inetSocketAddress);
            Channel channel = future.channel();
            if(channel.isActive()) {
                channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener)future1 -> {
                    if(future1.isSuccess()) {
                        log.info("客户端发送消息: {}", rpcRequest);
                    } else {
                        future1.channel().close();
                        log.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                //校验 RpcResponse 和 RpcRequest
                RpcMessageChecker.check(rpcResponse, rpcRequest);
                atomicReference.set(rpcResponse.getData());
            } else {
                System.exit(0);
            }
        } catch (InterruptedException e) {
            log.error("发送消息时有错误发生: ", e);
        }
        return atomicReference.get();
    }
}
