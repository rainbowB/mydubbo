package priv.fjh.mydubbo;

import priv.fjh.mydubbo.annotation.ServiceScan;
import priv.fjh.mydubbo.transport.netty.server.NettyServer;


/**
 * @author fjh
 * @date 2021/1/3 15:36
 * @Description:
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.start();
    }
}
