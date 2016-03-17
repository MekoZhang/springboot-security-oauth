package cn.zhangxd.server.common.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 扩展 org.springframework.jdbc.core.JdbcTemplate
 * Created by zhangxd on 16/3/10.
 */
public class CustomJdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {

    /**
     * Construct a new JdbcTemplate for bean usage.
     * <p>Note: The DataSource has to be set before using the instance.
     *
     * @see #setDataSource
     */
    public CustomJdbcTemplate() {
        super();
    }

    /**
     * Construct a new JdbcTemplate, given a DataSource to obtain connections from.
     * <p>Note: This will not trigger initialization of the exception translator.
     *
     * @param dataSource the JDBC DataSource to obtain connections from
     */
    public CustomJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Construct a new JdbcTemplate, given a DataSource to obtain connections from.
     * <p>Note: Depending on the "lazyInit" flag, initialization of the exception translator
     * will be triggered.
     *
     * @param dataSource the JDBC DataSource to obtain connections from
     * @param lazyInit   whether to lazily initialize the SQLExceptionTranslator
     */
    public CustomJdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    /**
     * 查询. 以List<实体类>的格式返回数据.
     *
     * @param sql   SQL语句
     * @param clazz 实体类型
     * @return List<实体类>
     * @throws DataAccessException
     */
    public <T> List<T> query(String sql, Class<T> clazz) throws DataAccessException {
        return super.query(sql, new BeanPropertyRowMapper<>(clazz));
    }

    /**
     * 查询. 以List<实体类>的格式返回数据.
     *
     * @param sql   SQL语句
     * @param args  SQL预处理参数
     * @param clazz 实体类型
     * @return List<实体类>
     * @throws DataAccessException
     */
    public <T> List<T> query(String sql, Object[] args, Class<T> clazz) throws DataAccessException {
        return super.query(sql, args, new BeanPropertyRowMapper<>(clazz));
    }

    /**
     * 查询. 以实体类的格式返回数据.
     * <pre>
     * 如果无匹配记录，返回null;
     * 如果匹配多条，返回第一条;
     * </pre>
     *
     * @param sql   SQL语句
     * @param clazz 实体类型
     * @return 实体对象T的实例
     * @throws DataAccessException
     */
    public <T> T queryForBean(String sql, Class<T> clazz) throws DataAccessException {
        List<T> list = this.query(sql, clazz);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询. 以实体类的格式返回数据.
     * <pre>
     * 如果无匹配记录，返回null;
     * 如果匹配多条，返回第一条;
     * </pre>
     *
     * @param sql   SQL语句
     * @param args  SQL预处理参数
     * @param clazz 实体类型
     * @return 实体对象T的实例
     * @throws DataAccessException
     */
    public <T> T queryForBean(String sql, Object[] args, Class<T> clazz) throws DataAccessException {
        List<T> list = this.query(sql, args, clazz);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询. 以Map的格式返回数据.
     * <pre>
     * 如果无匹配记录，返回null;
     * 如果匹配多条，返回第一条;
     * </pre>
     *
     * @param sql SQL语句
     * @return java.util.Map接口对象.
     * @throws DataAccessException
     */
    public Map<String, Object> queryForMap(String sql) throws DataAccessException {
        List<Map<String, Object>> list = super.queryForList(sql);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询. 以Map的格式返回数据.
     * 如果无匹配记录，返回null;
     * 如果匹配多条，返回第一条;
     *
     * @param sql  SQL语句
     * @param args SQL预处理参数
     * @return java.util.Map接口对象.
     * @throws DataAccessException
     */
    public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
        List<Map<String, Object>> list = super.queryForList(sql, args);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
