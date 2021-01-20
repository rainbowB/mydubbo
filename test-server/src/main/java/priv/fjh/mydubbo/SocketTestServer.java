package priv.fjh.mydubbo;

import priv.fjh.mydubbo.impl.HelloServiceImpl;
import priv.fjh.mydubbo.transport.RpcServer;
import priv.fjh.mydubbo.transport.socket.SocketServer;

/**
 * @author fjh
 * @date 2020/12/10 14:01
 * @Description:
 */
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer socketServer = new SocketServer("127.0.0.1", 9000);
        socketServer.start();
    }
}
