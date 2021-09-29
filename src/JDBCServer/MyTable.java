package JDBCServer;

import JDBCServer.Database;

public class MyTable {
    private Database database=null;
    private String sql;
    private String tableName="table";
    public MyTable(){}
    public MyTable(Database one){
        database=one;
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

}
