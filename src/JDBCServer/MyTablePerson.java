package JDBCServer;

import JDBCServer.Database;

public class MyTablePerson extends MyTable {
    public MyTablePerson(Database one){
        setDatabase(one);
        setTableName("person");
    }

    /**
     * 先判断表是否已经存在,若不存在创建表person
     * @return 返回true表示创建成功
     */
    public boolean createTable(){
        if(getDatabase().hasTable(getTableName())){
            System.out.println(getTableName()+" 表已存在，不需要再创建");
            return false;
        }
        setSql("CREATE TABLE person (username varchar(10) not null," +
                "name varchar(20) not null primary key," +
                "age int unsigned, teleno char(11));");
        int res=getDatabase().simpleImplement(getSql());
        if(res==-1) {
            System.out.println("创建表失败,请重试!");
            return false;
        }
        System.out.println(getTableName()+" 创建成功");
        return true;
    }

    /**
     * 向person表中插入指定字段值的数据，只有age和teleno可以为null
     * @return 返回true表示插入成功
     */
    public boolean dataInsert(String username,String name,String age,String teleno){
        if(username==null||name==null){
            System.out.println("username和name字段值不能为空");
            return false;
        }
        setSql("insert into person values (\""+username+"\",\""+name+
                "\","+age+",\""+teleno+"\");");
        getDatabase().simpleImplement(getSql());
        return true;
    }

}
