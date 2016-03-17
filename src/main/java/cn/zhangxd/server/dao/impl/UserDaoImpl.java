package cn.zhangxd.server.dao.impl;

import cn.zhangxd.server.common.dao.BaseDao;
import cn.zhangxd.server.dao.IUserDao;
import cn.zhangxd.server.domain.Role;
import cn.zhangxd.server.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends BaseDao implements IUserDao {

    @Override
    public User findByLogin(String login) {
        String userSql = " SELECT id,`name`,login,`password` FROM user WHERE login = ? ";
        String roleSql = " SELECT id,`name` FROM role JOIN user_role ON role.id = user_role.role_id WHERE user_role.user_id = ? ";
        User user = getJdbcTemplate().queryForBean(userSql, new Object[]{login}, User.class);
        List<Role> roles = getJdbcTemplate().query(roleSql, new Object[]{user.getId()}, Role.class);
        user.setRoles(roles);
        return user;
    }
}
