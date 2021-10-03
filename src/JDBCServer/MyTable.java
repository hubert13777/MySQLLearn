package JDBCServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class MyTable {
    private Database database = null;
    private String sql;
    private String tableName = "table";
    private String primaryKey = "main";
    private ArrayList<String> column = new ArrayList<>();

    protected void setDatabase(Database database) {
        this.database = database;
    }

    protected void setSql(String sql) {
        this.sql = sql;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected Database getDatabase() {
        return database;
    }

    protected String getSql() {
        return sql;
    }

    protected String getTableName() {
        return tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    protected void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public ArrayList<String> getColumn() {
        return column;
    }

    public void setColumn(String... str) {
        Collections.addAll(column, str);
    }

    /**
     * 判断与数据库的连接是否存在
     *
     * @return 返回true说明连接正常
     */
    protected boolean conJudge() {
        if (getDatabase() == null) return false;
        else return true;
    }

    /**
     * 判断表是否存在
     *
     * @return 返回true表示存在
     */
    protected boolean tableJudge() {
        if (getDatabase().hasTable(getTableName()) == false) return false;
        else return true;
    }

    /**
     * 检测表中是否含有此字段名
     *
     * @param key 等待检查的字段名
     * @return 返回true表示存在此字段名
     */
    protected boolean columnJudge(String key) {
        if (getColumn().contains(key)) return true;
        else return false;
    }

    /**
     * 判断是否存在指定键值对的数据
     *
     * @param key   字段名
     * @param value 字段值
     * @return 返回true则说明数据存在
     */
    protected boolean dataJudge(String key, String value) {
        if (!columnJudge(key)) {
            System.out.println("表中不存在此字段!");
            return false;
        }
        ResultSet temp = dataSelect(key, value);
        try {
            if (temp == null || !temp.next()) return false;   //没有数据
            else return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除该表，若表不存在会输出提示
     *
     * @return 返回true表示删除成功
     */
    public boolean deleteTable() {
        if (database == null) {
            System.out.println("未连接数据库！");
            return false;
        }
        if (database.hasTable(tableName)) {
            sql = "DROP TABLE " + tableName + ";";
            int res = database.simpleImplement(sql);
            setDatabase(null);
            if (res == 0) {
                System.out.println(tableName + ": 删除表成功");
                return true;
            } else {
                System.out.println("删除表操作执行出错!");
                return false;
            }
        } else {
            System.out.println("表 " + tableName + " 不存在，删除操作终止");
            setDatabase(null);
            return false;
        }
    }

    /**
     * 用来插入数据
     */
    public void dataInsert() {
        System.out.println("父类函数测试，没有插入任何数据");
    }

    /**
     * 删除指定字段值的数据
     * @param key 字段名
     * @param value 主键的字段值
     * @return 返回true表示删除成功
     */
    public boolean dataDeleteSearch(String key,String value) {
        // 检查是否存在连接、表和数据
        if (conJudge() == false) {
            System.out.println("与数据库连接中断，请重试！");
            return false;
        } else if (tableJudge() == false) {
            System.out.println("奇怪，数据库中并不存在这张表！");
            return false;
        } else if (!dataJudge(key, value)) {
            System.out.println(getTableName() + ": 表中没有 " + key + " 为 [" + value + "] 的数据!");
            return false;
        }

        setSql("delete from " + tableName + " where " + key + "=?;");
        int res = getDatabase().simpleImplement(getSql(), value);
        if (res == -1) {
            System.out.println("删除失败！");
            return false;
        } else {
            System.out.println(getTableName() + ": [" + value + "]删除成功");
            return true;
        }
    }

    /**
     * 删除表中所有数据
     *
     * @return 返回true表示删除成功
     */
    public boolean dataDeleteAll() {
        // 检查是否存在连接和表
        if (conJudge() == false) {
            System.out.println("与数据库连接中断，请重试！");
            return false;
        } else if (tableJudge() == false) {
            System.out.println("奇怪，数据库中并不存在这张表！");
            return false;
        }
        setSql("delete from " + tableName + ";");
        int res = getDatabase().simpleImplement(getSql());
        if (res == -1) return false;
        else return true;
    }

    /**
     * 查找指定键对应的值的数据是否存在
     *
     * @param key   字段名
     * @param value 要查询的字段值
     * @return 返回true说明存在这条数据
     */
    public ResultSet dataSelect(String key, String value) {
        if (!columnJudge(key)) {
            System.out.println("表中不存在此字段!");
            return null;
        }
        setSql("select * from " + getTableName() + " where " + key + "=?;");
        ResultSet rs = getDatabase().queryImplement(getSql(), value);
        return rs;
    }

    /**
     * 用来输出表的所有数据
     *
     * @return 返回false表示打印出错
     */
    public boolean dataAllSelect() {
        String sql = "select * from " + getTableName();
        ResultSet rs = getDatabase().queryImplement(sql);
        try {
            int count = getColumn().size();
            String[] temp = new String[count + 1];
            System.out.println("[表 " + getTableName()+"]");
            for(int i=1;i<=getColumn().size();i++) System.out.printf("------------");
            System.out.println();
            for (int i = 0; i < count; i++) {
                System.out.printf(getColumn().get(i));
                int len = getColumn().get(i).length();
                for (len = len + 1; len <= 12; len++) System.out.printf(" ");
            }
            System.out.println();
            for(int i=1;i<=getColumn().size();i++) System.out.printf("------------");
            System.out.println();
            // 输出表中数据
            while (rs.next()) {
                for (int i = 0; i < count; i++) {
                    temp[i] = rs.getString(getColumn().get(i));
                    if (temp[i] != null) System.out.printf("%-12s",temp[i]);
                    else System.out.printf("            ");  //当MySQL中数据为null时的输出
                }
                System.out.println();
            }
            for(int i=1;i<=getColumn().size();i++) System.out.printf("------------");
            System.out.println();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
