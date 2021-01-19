package priv.fjh.mydubbo.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import priv.fjh.mydubbo.loadbalance.LoadBalance;
import priv.fjh.mydubbo.loadbalance.RandomLoadBalance;
import priv.fjh.mydubbo.utils.zk.CuratorUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author fjh
 * @date 2021/1/10 10:35
 * @Description: 基于 zookeeper 实现服务注册中心
 */
@Slf4j
public class ZkServiceRegistry implements ServiceRegistry{

    private final CuratorFramework zkClient;
    private final LoadBalance loadBalance;

    public ZkServiceRegistry() {
        this.zkClient = CuratorUtils.getZkClient();
        this.loadBalance = new RandomLoadBalance();
    }

    @Override
    public void registerService(String serviceName, InetSocketAddress inetSocketAddress) {
        //根节点下注册子节点：服务
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + serviceName;
        //服务子节点下注册子节点：服务地址
        //由于inetSocketAddress.toString()=/127.0.0.1:9999，自带"/"，所以不需要手动添加"/"
        String addressPath = servicePath + inetSocketAddress.toString();
        try {
            if(zkClient.checkExists().forPath(servicePath) == null){
                CuratorUtils.createNode(zkClient, CreateMode.EPHEMERAL, addressPath);
                log.info("节点创建成功，节点为:{}", addressPath);
            } else {
                log.info("节点已经存在:{}", addressPath);
            }
        } catch (Exception e) {
            log.error("节点创建失败:", e);
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        List<String> serviceAddresses = CuratorUtils.getChildrenNodes(zkClient, serviceName);
        String serviceAddress = loadBalance.selectServiceAddress(serviceAddresses);
        log.info("成功找到服务地址:{}", serviceAddress);
        String[] socketAddressArray = serviceAddress.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
