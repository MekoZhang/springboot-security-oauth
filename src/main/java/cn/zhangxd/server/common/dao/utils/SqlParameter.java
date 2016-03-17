package cn.zhangxd.server.common.dao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于持久化的参数实体对象
 * Created by zhangxd on 16/3/14.
 */
public class SqlParameter {

    /**
     * 操作的数据库表名，必须
     */
    private String tableName = null;

    /**
     * 表的主键列表，必须
     */
    private List<String> keys = new ArrayList<>();

    /**
     * 数据行，必须
     */
    private Map<String, Object> record = null;

    public SqlParameter() {
    }

    public SqlParameter(String tableName) {
        this.tableName = tableName;
    }

    public SqlParameter addKey(String key) {
        this.keys.add(key);
        return this;
    }

    public List<String> getKeys() {
        return keys;
    }

    public SqlParameter setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, Object> getRecord() {
        return record;
    }

    /**
     * 设置数据行,格式为Map<String, Object>
     *
     * @param record 数据行
     */
    public void setRecord(Map<String, Object> record) {
        this.record = record;
    }

    /**
     * 设置数据行,格式为Map<Enum<?>, Object>
     *
     * @param record 数据行
     */
    public void setRecord2(Map<? extends Enum<?>, Object> record) {
        this.record = new HashMap<>();

        for (Map.Entry<? extends Enum<?>, Object> entry : record.entrySet()) {
            this.record.put(entry.getKey().toString(), entry.getValue());
        }
    }
}