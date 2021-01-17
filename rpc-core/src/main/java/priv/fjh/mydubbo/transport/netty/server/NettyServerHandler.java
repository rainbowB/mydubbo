package priv.fjh.mydubbo.transport.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.handler.RequestHandler;
import priv.fjh.mydubbo.dto.RpcRequest;
import priv.fjh.mydubbo.dto.RpcResponse;
import priv.fjh.mydubbo.provider.ServiceProviderImpl;
import priv.fjh.mydubbo.provider.ServiceProvider;
import priv.fjh.mydubbo.utils.concurrent.ThreadPoolFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author fjh
 * @date 2020/12/31 9:44
 * @Description:
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final String THREAD_NAME_PREFIX = "netty-server-handler-rpc-pool";
    private final RequestHandler requestHandler;
    private final ExecutorService threadPool;

    public NettyServerHandler() {
        this.requestHandler = new RequestHandler();
        this.threadPool = ThreadPoolFactory.createDefaultThreadPool(THREAD_NAME_PREFIX);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        threadPool.execute(() -> {
            log.info("server handle message from client by thread:{}", Thread.currentThread().getName());
            log.info("服务器接收到请求: {}", msg);
            //执行目标方法（客户端需要执行的方法）并且返回方法结果
            Object result = requestHandler.handle(msg);
            //返回方法执行结果给客户端
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.getSuccess(msg.getRequestId(), result));
            future.addListener(ChannelFutureListener.CLOSE);
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
