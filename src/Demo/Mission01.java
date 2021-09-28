package Demo;
import JDBCServer.Database;

public class Mission01 {
    public static void main(String[] args) {
        Database conn=new Database();

        if(conn.jdbcConnection()){
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            conn.jdbcExit();
        }
    }
}