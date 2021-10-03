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
            //创建表
            users.createTable();
            person.createTable();
//            //删除表
//            users.deleteTable();
//            person.deleteTable();
            // 插入数据
            users.dataInsert("zhangsan","123456");
            person.dataInsert("zhangsan","张三","20","10001");
//            // 删除某条数据
//            users.dataDeleteSearch("zhangsan");
//            person.dataDeleteSearch("张三");

            if (conn.jdbcExit()){
                // 关闭连接
                System.out.println("\n已关闭与数据库的连接");
            }
        }

    }

}