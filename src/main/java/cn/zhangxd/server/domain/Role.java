package cn.zhangxd.server.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色Domain
 * Created by zhangxd on 16/3/17.
 */
public class Role implements GrantedAuthority {

    private Integer id;

    private String name;

    private List<User> users = new ArrayList<>();

    @Override
    public String getAuthority() {
        return this.name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
