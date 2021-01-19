package priv.fjh.mydubbo.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @author fjh
 * @date 2021/1/19 10:07
 * @Description: 随机
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddresses) {
        if(serviceAddresses == null || serviceAddresses.size() == 0){
            return null;
        }
        return serviceAddresses.get(new Random().nextInt(serviceAddresses.size()));
    }
}
