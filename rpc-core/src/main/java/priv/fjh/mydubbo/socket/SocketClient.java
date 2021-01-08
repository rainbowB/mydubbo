package priv.fjh.mydubbo.socket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.RpcClient;
import priv.fjh.mydubbo.RpcServer;
import priv.fjh.mydubbo.dto.RpcRequest;
import priv.fjh.mydubbo.dto.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author fjh
 * @date 2020/12/10 10:42
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class SocketClient implements RpcClient {

    private final String host;
    private final int port;

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e) {
            log.error("调用时有错误发生：", e);
            return null;
        }
    }

}
