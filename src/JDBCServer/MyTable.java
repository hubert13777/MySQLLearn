package JDBCServer;

import JDBCServer.Database;

public class MyTable {
    private Database database=null;
    private String sql;
    private String tableName="table";

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

    /**
     * 删除该表，若表不存在会输出提示
     * @return 返回true表示删除成功
     */
    public boolean deleteTable(){
        if(database==null){
            System.out.println("未连接数据库！");
            return false;
        }
        if(database.hasTable(tableName)){
            sql="DROP TABLE "+tableName+";";
            int res=database.simpleImplement(sql);
            if(res==0) {
                System.out.println("删除成功!");
                return true;
            }
            else {
                System.out.println("删除表操作执行出错!");
                return false;
            }
        }else{
            System.out.println("表 "+tableName+" 不存在");
            return false;
        }
    }
    public boolean dataInsert(){
        System.out.println("测试，没有插入任何数据");
        return true;
    }

}
