package JDBCServer;

import JDBCServer.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

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
            System.out.println(getTableName() + ": 创建表失败,请重试!");
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
            System.out.println(getTableName() + ": 已存在name=[" + name + "]的数据，添加失败！");
            return false;
        } else if(dataJudge("username",username)){
            System.out.println(getTableName() + ": 已存在username=[" + name + "]的数据，添加失败!");
            return false;
        }
        setSql("insert into person values (?,?,?,?);");
        int res = getDatabase().simpleImplement(getSql(), username, name, age, teleno);
        if (res == -1) {
            System.out.println(getTableName() + ": 数据[" + username + "]添加失败!");
            return false;
        } else {
            System.out.println(getTableName() + ": 数据[" + username + "]添加成功");
            return true;
        }
    }

    /**
     * 更新特定字段值的数据，若数据不存在会提示，注意条件只能是值相等
     *
     * @param key   条件字段名
     * @param value 条件字段值
     * @return 返回true表示更新成功
     */
    public boolean dataUpdate(String key, String value, String username, String name, String age, String teleno) {
        if (columnJudge(key) == false) {  //检查此字段名是否存在
            System.out.println(getTableName() + ": 条件字段名不存在!");
            return false;
        } else {     // 检查这条数据是否存在
            try {
                ResultSet temp=dataSelect(key, value);
                if (temp == null || !temp.next()) { //数据不存在
                    System.out.println(getTableName() + ": 数据不存在，请尝试插入!");
                    return false;
                }else {
                    String oldName=temp.getString("name");
                    if(oldName.equals(name)){
                        System.out.println(getTableName()+": 主键name值相同，无法更新!");
                        return false;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        String sql = "update " + getTableName() + " set username=?,name=?,age=?,teleno=? where " + key + "=?;";
        int res = getDatabase().simpleImplement(sql, username, name, age, teleno, value);
        if (res == -1) {
            System.out.println(getTableName() + ": 更新数据["+username+"]出错!");
            return false;
        } else {
            System.out.println(getTableName() + ": 更新数据["+username+"]成功");
            return true;
        }
    }

    /**
     * 带有替换功能的插入，若原来已经有数据则更新，没有则正常添加，但需要在another表中插入该username(若已有就不用了)
     *
     * @param another 另一个表的类
     * @return 返回true则插入成功
     */
    public boolean dataInsertReplace(MyTableUsers another, String username, String name, String age, String teleno) {
        ResultSet rs = dataSelect("username", username); //先查看自身是否存在这个数据
        try {
            if (rs.next()) { //说明本来已存在这个数据，需要更新
                dataUpdate("username", username, username, name, age, teleno);
                return true;
            } else {  //说明数据并不存在，需要直接添加并在another中也加上该username(如果users中没有的话)
                dataInsert(username, name, age, teleno);
                if (!usersSearch(another, username)) {
                    another.dataInsert(username, "888888");
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(getTableName() + ": 插入数据[" + username + "]出错！");
            return false;
        }
    }

    /**
     * 查找users表中是否存在指定username字段值的数据
     *
     * @param username username字段的值
     * @return 返回true表示users表中存在该username
     */
    public boolean usersSearch(MyTableUsers users, String username) {
        ResultSet rs = users.dataSelect("username", username);
        try {
            if (rs == null || !rs.next()) return false;
            else return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
