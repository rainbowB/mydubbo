package priv.fjh.mydubbo.impl;

import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.Hello;
import priv.fjh.mydubbo.HelloService;
import priv.fjh.mydubbo.annotation.Service;

/**
 * @author fjh
 * @date 2020/12/7 15:51
 * @Description:
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(Hello hello) {
        log.info("接收到：{}", hello.getMessage());
        return "这是调用的返回值，id=" + hello.getId() +", msg=" + hello.getMessage();
    }
}
