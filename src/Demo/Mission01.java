package Demo;

import JDBCServer.Database;
import JDBCServer.MyTablePerson;
import JDBCServer.MyTableUsers;

public class Mission01 {
    public static void main(String[] args) {
        Database conn = new Database();

        if(conn.jdbcConnection()==1){
            System.out.println("数据库连接成功");
            MyTableUsers users=new MyTableUsers(conn);
            MyTablePerson person=new MyTablePerson(conn);
            // 创建表
            users.createTable();
            person.createTable();
            // 插入数据
            users.dataInsert("zhangsan","123456");
            person.dataInsert("zhangsan","张三","20","10001");


            conn.jdbcExit();
        }

    }

}