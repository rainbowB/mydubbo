package priv.fjh.mydubbo.impl;

import priv.fjh.mydubbo.ByeService;
import priv.fjh.mydubbo.annotation.Service;

/**
 * @author fjh
 * @date 2021/1/19 20:43
 * @Description:
 */
@Service
public class ByeServiceImpl implements ByeService {
    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
