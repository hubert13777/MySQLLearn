package JDBCServer;

public class MyTableUsers extends MyTable {
    public MyTableUsers(Database one) {
        setDatabase(one);
        setTableName("users");
    }

    /**
     * 先判断表是否已经存在,若不存在创建表users
     * @return 返回true表示创建成功
     */
    public boolean createTable(){
        if(getDatabase().hasTable(getTableName())){
            System.out.println(getTableName()+" 表已存在，不需要再创建");
            return false;
        }
        setSql("CREATE TABLE users (username varchar(10) not null primary key," +
                "pass varchar(8) not null default \"888888\");");
        int res=getDatabase().simpleImplement(getSql());
        if(res==-1) {
            System.out.println("创建表失败,请重试!");
            return false;
        }
        System.out.println(getTableName()+" 创建成功");
        return true;
    }

    /**
     * 向user表中插入指定字段值的数据
     * @return 返回true表示插入成功
     */
    public boolean dataInsert(String username,String pass){
        if(username==null||pass==null){
            System.out.println("username和pass字段值不能为空");
            return false;
        }
        setSql("insert into users values (\""+username+"\",\""+pass+"\");");
        getDatabase().simpleImplement(getSql());
        return true;
    }
}
