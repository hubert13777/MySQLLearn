package Demo;

import JDBCServer.Database;
import JDBCServer.MyTablePerson;
import JDBCServer.MyTableUsers;

public class Mission01 {
    public static void main(String[] args) {
        Database conn = new Database();

        if(conn.jdbcConnection()==1){
            System.out.println("数据库连接成功\n");
            MyTableUsers users=new MyTableUsers(conn);
            MyTablePerson person=new MyTablePerson(conn);
            //1.创建表
            users.createTable();
            person.createTable();
            System.out.println();
            users.dataAllSelect();
            person.dataAllSelect();
            System.out.println();

            // 2.插入数据
            users.dataInsert("ly","123456");
            users.dataInsert("liming","345678");
            users.dataInsert("test","11111");
            users.dataInsert("test1","12345");

            person.dataInsert("ly","雷力",null,null);
            person.dataInsert("liming","李明","25",null);
            person.dataInsert("test","测试用户","20","13388449933");

            users.dataAllSelect();
            person.dataAllSelect();

            //3.插入数据
            person.dataInsertReplace(users,"ly","王五",null,null);
            person.dataInsertReplace(users,"test2","测试用户2",null,null);
            person.dataInsertReplace(users,"test1","测试用户1","33",null);
            person.dataInsertReplace(users,"test","张三","23","18877009966");
            person.dataInsertReplace(users,"admin","admin",null,null);
            //输出数据信息
            users.dataAllSelect();
            person.dataAllSelect();

//            //删除表
//            users.deleteTable();
//            person.deleteTable();
            if (conn.jdbcExit()){
                // 关闭连接
                System.out.println("\n已关闭与数据库的连接");
            }
        }

    }

}