package JDBCServer;

import JDBCServer.Database;

public class MyTablePerson extends MyTable {
    public MyTablePerson(Database one) {
        setDatabase(one);
        setTableName("person");
        setPrimaryKey("name");
        setColumn("username", "name", "age", "teleno");
    }

    /**
     * 先判断表是否已经存在,若不存在创建表person
     *
     * @return 返回true表示创建成功
     */
    public boolean createTable() {
        if (tableJudge() == true) {
            System.out.println(getTableName() + ": 已存在，不需要再创建");
            return false;
        }
        setSql("CREATE TABLE person (username varchar(10) not null," +
                "name varchar(20) not null primary key," +
                "age int unsigned, teleno char(11));");
        int res = getDatabase().simpleImplement(getSql());
        if (res == -1) {
            System.out.println(getTableName() +": 创建表失败,请重试!");
            return false;
        }
        System.out.println(getTableName() + ": 创建表成功");
        return true;
    }

    /**
     * 向person表中插入指定字段值的数据，只有age和teleno可以为null
     *
     * @return 返回true表示插入成功
     */
    public boolean dataInsert(String username, String name, String age, String teleno) {
        if (username == null || name == null) {
            System.out.println("username和name字段值不能为空");
            return false;
        } else if (conJudge() == false) {
            System.out.println("与数据库连接中断，添加失败！");
            return false;
        } else if (dataJudge("name", name) == true) {
            System.out.println(getTableName() + ": 已存在[" + name + "]，添加失败！");
            return false;
        }
        setSql("insert into person values (?,?,?,?);");
        int res = getDatabase().simpleImplement(getSql(), username, name, age, teleno);
        if (res == -1) {
            System.out.println(getTableName() + ": 数据添加失败！");
            return false;
        } else {
            System.out.println(getTableName() + ": 数据添加成功");
            return true;
        }
    }
}
