package cn.zhangxd.server.common.dao;

import cn.zhangxd.server.common.jdbc.CustomJdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Dao层基类
 * Created by zhangxd on 16/3/10.
 */
public abstract class BaseDao {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomJdbcTemplate customJdbcTemplate;

    public final CustomJdbcTemplate getJdbcTemplate() {
        return this.customJdbcTemplate;
    }

}