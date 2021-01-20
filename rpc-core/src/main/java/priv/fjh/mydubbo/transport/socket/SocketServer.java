package priv.fjh.mydubbo.transport.socket;

import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.provider.ServiceProviderImpl;
import priv.fjh.mydubbo.registry.ServiceRegistry;
import priv.fjh.mydubbo.registry.ZkServiceRegistry;
import priv.fjh.mydubbo.transport.AbstractRpcServer;
import priv.fjh.mydubbo.transport.RpcServer;
import priv.fjh.mydubbo.provider.ServiceProvider;
import priv.fjh.mydubbo.handler.RequestHandler;
import priv.fjh.mydubbo.handler.RequestHandlerThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author fjh
 * @date 2020/12/10 10:53
 * @Description:
 */
@Slf4j
public class SocketServer extends AbstractRpcServer {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host, int port) {
        super.host = host;
        super.port = port;
        super.serviceRegistry = new ZkServiceRegistry();
        super.serviceProvider = new ServiceProviderImpl();
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    @Override
    public void start(){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("服务器正在启动...");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                log.info("客户端连接！Ip为：" + socket.getInetAddress());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("连接时有错误发生：", e);
        }
    }
}
