package cn.zhangxd.server.common.dao.utils;

import java.io.Serializable;

/**
 * SQL预编译属性实体
 * Created by zhangxd on 16/3/14.
 */
public class SqlResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 预处理的SQL语句
     */
    private String sql;

    /**
     * 预处理SQL真实的值列表
     */
    private Object[] values;

    public SqlResult(String sql, Object[] values) {
        super();
        this.sql = sql;
        this.values = values.clone();
    }

    public String getSql() {
        return sql;
    }

    public Object[] getValues() {
        return values.clone();
    }

}