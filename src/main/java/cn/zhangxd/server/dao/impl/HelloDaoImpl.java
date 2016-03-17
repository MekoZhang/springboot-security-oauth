package cn.zhangxd.server.dao.impl;

import cn.zhangxd.server.common.dao.BaseDao;
import cn.zhangxd.server.dao.IHelloDao;
import cn.zhangxd.server.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangxd on 16/3/10.
 */
@Repository("helloDao")
public class HelloDaoImpl extends BaseDao implements IHelloDao {

    @Override
    public List<User> findUsers() {
        String sql = "SELECT * FROM user;";
        logger.info("helloDao");
        return getJdbcTemplate().query(sql, User.class);
    }
}
