package priv.fjh.mydubbo.transport.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.dto.RpcResponse;

/**
 * @author fjh
 * @date 2021/1/3 15:14
 * @Description:
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        log.info(String.format("客户端接收到消息: %s", msg));
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + msg.getRequestId());
        /*
         * AttributeMap 可以看作是一个Channel的共享数据源
         * AttributeMap 的 key 是 AttributeKey，value 是 Attribute
         */
        // 将服务端的返回结果保存到 AttributeMap 上
        ctx.channel().attr(key).set(msg);
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
