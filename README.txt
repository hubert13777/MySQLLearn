DataBase
1）jdbcConnection：读取配置文件，连接SQL，返回boolean，true成功
2）simpleImplement：执行不需要返回数据集的SQL语句，返回int表示改变的行数，-1则未成功
3）queryImplement：执行需要返回ResultSet的SQL语句，返回数据集
4）jdbcExit：断开连接，返回boolean，true成功
5）listAllTable：查看当前数据库下的所有表，返回int表示表的个数
6）hasTable：查看是否有这个表，返回boolean，true表示有此表