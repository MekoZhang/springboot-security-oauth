package cn.zhangxd.server.base;

import cn.zhangxd.server.common.jdbc.CustomJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * JdbcTemplate 数据源配置类
 * Created by zhangxd on 16/3/10.
 */
@Configuration
public class JdbcTemplateConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public CustomJdbcTemplate jdbcTemplate() {
        return new CustomJdbcTemplate(this.dataSource);
    }
}
