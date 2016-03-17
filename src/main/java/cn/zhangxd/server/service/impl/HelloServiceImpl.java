package cn.zhangxd.server.service.impl;

import cn.zhangxd.server.common.service.BaseService;
import cn.zhangxd.server.dao.IHelloDao;
import cn.zhangxd.server.dao.IMongoDao;
import cn.zhangxd.server.dao.IRedisDao;
import cn.zhangxd.server.domain.User;
import cn.zhangxd.server.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangxd on 16/3/10.
 */
@Service("helloService")
public class HelloServiceImpl extends BaseService implements IHelloService {

    @Autowired
    private IHelloDao helloDao;

    @Autowired
    private IRedisDao redisDao;

    @Autowired
    private IMongoDao mongoDao;

    @Override
//    @Cacheable(value = "users", keyGenerator = "methodKeyGenerator")
    public List<User> findUsers() {
//        String a = null;
//        a.toString();
        logger.info("没有使用缓存");
        redisDao.findUsers();

        mongoDao.findUsers();

        return helloDao.findUsers();
    }
}
