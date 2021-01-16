package priv.fjh.mydubbo.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.dto.RpcRequest;
import priv.fjh.mydubbo.dto.RpcResponse;
import priv.fjh.mydubbo.provider.ServiceProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author fjh
 * @date 2020/12/30 10:26
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class RequestHandlerThread implements Runnable{

    private Socket socket;
    private RequestHandler requestHandler;

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = requestHandler.handle(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.getSuccess(rpcRequest.getRequestId(), result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            log.error("调用或发送时有错误发生：", e);
        }
    }
}
