package priv.fjh.mydubbo.utils.zk;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fjh
 * @date 2021/1/10 10:46
 * @Description:
 */
@Slf4j
@NoArgsConstructor
public class CuratorUtils {

    private static final int BASE_SLEEP_TIME = 1000;
    private static final int MAX_RETRIES = 3;
    private static final String CONNECT_STRING = "192.168.23.128:2181";
    public static final String ZK_REGISTER_ROOT_PATH = "/servers";
    private static CuratorFramework zkClient;
    private static Map<String, List<String>> serviceAddressMap = new ConcurrentHashMap<>();

    public static CuratorFramework getZkClient(){
        if (zkClient != null && zkClient.getState() == CuratorFrameworkState.STARTED) {
            return zkClient;
        }
        RetryPolicy retryPolicy = new RetryNTimes(MAX_RETRIES, BASE_SLEEP_TIME);
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }

    /**
     * 创建节点
     * /servers/priv.fjh.mydubbo.HelloService/127.0.0.1:9999
     */
    public static void createNode(CuratorFramework zkClient, CreateMode createMode, String path) throws Exception {
        zkClient.create().creatingParentContainersIfNeeded().withMode(createMode).forPath(path);
    }

    /**
     * 从指定节点下获取的服务子节点列表
     */
    public static List<String> getChildrenNodes(CuratorFramework zkClient, String serviceName){
        if (serviceAddressMap.containsKey(serviceName)) {
            return serviceAddressMap.get(serviceName);
        }
        List<String> result = Collections.emptyList();
        String servicePath = ZK_REGISTER_ROOT_PATH + "/" + serviceName;
        try {
            result = zkClient.getChildren().forPath(servicePath);
            serviceAddressMap.put(serviceName, result);
            registerWatcher(zkClient, serviceName);
        } catch (Exception e) {
            log.error("occur exception:", e);
        }
        return result;
    }

    /**
     * 注册监听,动态发现服务节点的变化
     * Path Cache用来监控一个ZNode的子节点.当一个子节点增删改时，Path Cache会改变它的状态，
     * 会包含最新的子节点，子节点的数据和状态，而状态的更变将通过PathChildrenCacheListener通知。
     * zookeeper原生watcher监听需要手动反复注册，而curator使用cache能够自动为开发者处理反复注册监听
     */
    private static void registerWatcher(CuratorFramework zkClient, String serviceName) throws Exception {
        String servicePath = ZK_REGISTER_ROOT_PATH + "/" + serviceName;
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, servicePath, true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            List<String> serviceAddresses = curatorFramework.getChildren().forPath(servicePath);
            serviceAddressMap.put(serviceName, serviceAddresses);
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start();
    }

}
