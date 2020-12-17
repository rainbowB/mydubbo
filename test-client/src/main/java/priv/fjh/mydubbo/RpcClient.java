package priv.fjh.mydubbo;

import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.dto.RpcRequest;

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
public class RpcClient {

    public Object sendRequest(RpcRequest rpcRequest, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("调用时有错误发生：", e);
            return null;
        }
    }

}
