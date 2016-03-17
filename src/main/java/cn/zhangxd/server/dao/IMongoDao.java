package cn.zhangxd.server.dao;

import cn.zhangxd.server.domain.User;

import java.util.List;

/**
 * Created by zhangxd on 16/3/10.
 */
public interface IMongoDao {

    List<User> findUsers();

}
