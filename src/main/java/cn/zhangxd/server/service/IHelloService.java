package cn.zhangxd.server.service;

import cn.zhangxd.server.domain.User;

import java.util.List;

/**
 * Created by zhangxd on 16/3/10.
 */
public interface IHelloService {

    List<User> findUsers();

}
