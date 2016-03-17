package cn.zhangxd.server.dao.impl;

import cn.zhangxd.server.common.dao.BaseMongoDao;
import cn.zhangxd.server.dao.IMongoDao;
import cn.zhangxd.server.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangxd on 16/3/15.
 */
@Repository("mongoDao")
public class MongoDaoImpl extends BaseMongoDao implements IMongoDao {

    @Override
    public List<User> findUsers() {
        User a = new User();
        a.setId(123);
        User b = new User();
        b.setId(456);
        getMongoTemplate().save(a);
        getMongoTemplate().save(b);

        logger.info("mongoDao");
        for (User user : getMongoTemplate().findAll(User.class)) {
            System.out.println(user.getId());
        }
        return null;
    }
}
