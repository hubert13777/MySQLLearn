package JDBCServer;

import javax.xml.crypto.Data;

public class MyTablePerson extends MyTable {
    private String tableName="person";
    private Database database;
    private String sql;
    public MyTablePerson(Database one){
        database=one;
    }

    /**
     * 先判断表是否已经存在,若不存在创建表person
     * @return 返回true表示创建成功
     */
    public boolean createTable(){
        if(database.hasTable(tableName)){
            System.out.println(tableName+" 表已存在，不需要再创建");
            return false;
        }
        sql="CREATE TABLE person (username varchar(10) not null," +
                "name varchar(20) not null primary key," +
                "age int unsigned, teleno char(11));";
        int res=database.simpleImplement(sql);
        if(res==-1) {
            System.out.println("创建表失败,请重试!");
            return false;
        }
        System.out.println(tableName+" 创建成功");
        return true;
    }

}
