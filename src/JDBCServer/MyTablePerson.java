package JDBCServer;

import javax.xml.crypto.Data;

public class MyTablePerson extends MyTable {
    private String tableName="person";
    private Database database;
    private String sql;
    public MyTablePerson(Database one){
        database=one;
    }

}
