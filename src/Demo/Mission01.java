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
            users.createTable();
            person.createTable();
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            users.deleteTable();
            person.deleteTable();

            conn.jdbcExit();
        }

    }

}