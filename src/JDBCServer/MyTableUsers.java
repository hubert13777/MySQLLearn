package JDBCServer;

public class MyTableUsers extends MyTable {
    private String tableName="users";
    private Database database;
    private String sql;
    public MyTableUsers(Database one) {
        database=one;
    }
}
