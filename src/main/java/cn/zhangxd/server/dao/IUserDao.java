package cn.zhangxd.server.dao;

import cn.zhangxd.server.domain.User;

/**
 * 用户Dao
 * Created by zhangxd on 16/3/17.
 */
public interface IUserDao {

    User findByLogin(String login);

}
