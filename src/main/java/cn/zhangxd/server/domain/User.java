package cn.zhangxd.server.domain;

import java.io.Serializable;
import java.util.*;

/**
 * 用户Domain
 * Created by zhangxd on 16/3/17.
 */
public class User implements Serializable {

    private Integer id;

    private String name;

    private String login;

    private String password;

    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(User user) {
        super();
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.roles = user.getRoles();
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
