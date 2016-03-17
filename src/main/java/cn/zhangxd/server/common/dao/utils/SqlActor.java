package cn.zhangxd.server.common.dao.utils;

import cn.zhangxd.server.common.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 构造SQL语句帮助类
 * Created by zhangxd on 16/3/14.
 */
public class SqlActor {

    private String tableName = null;
    private List<String> keys = null;
    private Map<String, Object> record = null;

    /**
     * 数据库关键字常量
     */
    private static final String SQLSEP = ", ";
    private static final String INSERT = "INSERT INTO ";
    private static final String UPDATE = "UPDATE ";
    private static final String DELETE = "DELETE FROM ";
    private static final String VALUES = " VALUES ";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String SET = " SET ";

    public SqlActor(SqlParameter sqlParameter) {
        this.tableName = sqlParameter.getTableName();
        this.keys = sqlParameter.getKeys();
        this.record = sqlParameter.getRecord();
    }

    /**
     * 生成 INSERT 语句
     *
     * @return 对象数据，格式如下：
     * new Object[] {"INSERT INTO T (C1, C2) VALUES (?, ?)", new Object[] {"A", "B"}}
     * 包含 预处理的SQL 和 数据列表
     */
    public SqlResult buildInsertSql() {
        Object[] pros = new Object[record.entrySet().size()];

        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        int index = 0;
        for (Entry<String, Object> entry : record.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            fields.append(SQLSEP).append(field);
            values.append(SQLSEP).append("?");

            pros[index++] = calVal(value);
        }
        fields.delete(0, SQLSEP.length());
        values.delete(0, SQLSEP.length());

        String sql = INSERT + this.tableName + "(" + fields + ")" + VALUES + "(" + values + ")";

        return new SqlResult(sql, pros);
    }

    /**
     * 生成 UPDATE 语句
     *
     * @return 对象数据，格式如下：
     * new Object[] {"UPDATE T SET C2=? WHERE C1=?", new Object[] {"A", "B"}}
     * 包含 预处理的SQL 和 数据列表
     */
    public SqlResult buildUpdateSql() {
        List<Object> pros = new ArrayList<>();

        StringBuilder fields = new StringBuilder();
        for (Entry<String, Object> entry : record.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            if (keys.contains(field)) {
                continue;
            }

            fields.append(SQLSEP).append(field).append("=?");

            pros.add(calVal(value));
        }
        fields.delete(0, SQLSEP.length());

        StringBuilder condition = new StringBuilder();
        for (String key : keys) {
            Object o = record.get(key);
            condition.append(AND).append(key).append("=?");
            pros.add(calVal(o));
        }

        if (condition.length() > 0) {
            fields.append(WHERE).append(condition.substring(AND.length()));
        }

        String sql = UPDATE + this.tableName + SET + fields;

        return new SqlResult(sql, pros.toArray(new Object[pros.size()]));
    }

    private Object calVal(Object o) {
        if (o == null) {
            return "";
        }

        return o;
    }

    public static void main(String[] args) {
        Map<String, Object> record = new HashMap<>();
        record.put("colA", "A");
        record.put("colB", "B");
        record.put("colC", "C");
        record.put("colD", "D");

        SqlParameter sqlParameter = new SqlParameter("tableA");
        sqlParameter.addKey("colA").addKey("colB");
        sqlParameter.setRecord(record);

        SqlActor sqlActor = new SqlActor(sqlParameter);
        SqlResult insertResult = sqlActor.buildInsertSql();
        SqlResult updateResult = sqlActor.buildUpdateSql();
        System.out.println(insertResult.getSql()
                + "\t\t\t args:[ " + StringHelper.join(insertResult.getValues(), "\t") + "]");
        System.out.println(updateResult.getSql()
                + "\t\t\t args:[ " + StringHelper.join(updateResult.getValues(), "\t") + "]");


        //enumeration
        Map<Enum1, Object> record2 = new HashMap<>();
        record2.put(Enum1.A, "a");
        record2.put(Enum1.B, "b");
        record2.put(Enum1.C, "c");
        SqlParameter sqlParameter2 = new SqlParameter("tableB");
        sqlParameter2.addKey(Enum1.A.toString()).addKey(Enum1.B.toString());
        sqlParameter2.setRecord2(record2);

        SqlActor sqlActor2 = new SqlActor(sqlParameter2);
        SqlResult insertResult2 = sqlActor2.buildInsertSql();
        SqlResult updateResult2 = sqlActor2.buildUpdateSql();
        System.out.println(insertResult2.getSql()
                + "\t\t\t args:[ " + StringHelper.join(insertResult2.getValues(), "\t") + "]");
        System.out.println(updateResult2.getSql()
                + "\t\t\t args:[ " + StringHelper.join(updateResult2.getValues(), "\t") + "]");

    }

    enum Enum1 {
        A, B, C
    }
}