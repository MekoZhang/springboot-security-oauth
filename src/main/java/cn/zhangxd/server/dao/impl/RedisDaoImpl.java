package cn.zhangxd.server.dao.impl;

import cn.zhangxd.server.common.dao.BaseRedisDao;
import cn.zhangxd.server.dao.IRedisDao;
import cn.zhangxd.server.domain.User;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangxd on 16/3/15.
 */
@Repository("redisDao")
public class RedisDaoImpl extends BaseRedisDao implements IRedisDao {

    @Override
    public List<User> findUsers() {

        ValueOperations<String, String> ops = getRedisTemplate().opsForValue();

        String key = "spring.boot.redis.test";
        if (!getRedisTemplate().hasKey(key)) {
            ops.set(key, "foo");
        }

        logger.info("Found key " + key + ", value=" + ops.get(key));
        return null;
    }
}
