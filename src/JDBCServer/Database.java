package JDBCServer;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * 最底层，负责连接数据库并发送SQL语句
 *
 * @author 何天辰
 * @version v0.8
 */
public class Database {
    private String driver;
    private Connection con;
    private ResultSet rs;
    private Statement state;

    /**
     * 连接MySQL数据库，连接信息从./init.ini读取
     *
     * @return 返回true表示连接成功
     */
    public boolean jdbcConnection() {  //true成功
        String url = "", username = "", password = "";
        try {
            InputStream f = new FileInputStream("init.ini");
            BufferedReader bf = new BufferedReader(new InputStreamReader(f));
            String str;
            StringBuilder name = new StringBuilder();
            StringBuilder inf = new StringBuilder();
            char c;
            while (true) {
                str = bf.readLine();
                if (str == null) break;
                name.delete(0, name.length());
                inf.delete(0, inf.length());
                int i;
                for (i = 0; i < str.length(); i++) {
                    c = str.charAt(i);
                    if (c != '=') name.append(c);
                    else break;
                }
                for (i = i + 1; i < str.length(); i++) {
                    c = str.charAt(i);
                    inf.append(c);
                }
                switch (name.toString()) {
                    case "DRIVER":
                        driver = inf.toString();
                        break;
                    case "URL":
                        url = inf.toString();
                        break;
                    case "USERNAME":
                        username = inf.toString();
                        break;
                    case "PASSWORD":
                        password = inf.toString();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // 尝试连接
        try {
            Class.forName(this.driver);
            this.con = DriverManager.getConnection(url, username, password);
            if (!this.con.isClosed())
                System.out.println("成功连接到数据库");
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败,连接终止!");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.out.println("数据库连接失败!");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * @return 返回true表示正常关闭
     */
    public boolean jdbcExit() {
        try {
            if (state != null) this.state.close();
            if (rs != null) this.rs.close();
            if (con != null) {
                this.con.close();
                System.out.println("与 " + con.getCatalog() + " 的连接已关闭");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param sql 需要执行的SQL语句
     * @return 返回值为SQL语句改变的行数，若为-1则表明出错
     */
    protected int simpleImplement(String sql) {
        if (con == null) {
            System.out.println("未连接数据库！");
            return -1;
        }
        try {
            state = con.createStatement();
            int res = state.executeUpdate(sql); //执行SQL语句

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @param sql 需要执行的SQL语句
     * @return 返回数据集
     */
    protected ResultSet queryImplement(String sql) {
        if (con == null) {
            System.out.println("未连接数据库！");
            return rs;
        }
        try {
            state = con.createStatement();
            rs = state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 用于获取数据库中所有的表名
     *
     * @return 返回ArrayList数组，储存表名，若获取失败或数据库为空则为null
     */
    public ArrayList<String> listAllTable() {
        if (con == null) {
            System.out.println("未连接数据库！");
            return null;
        }
        ArrayList<String> tables = new ArrayList<>();
        try {
            rs = con.getMetaData().getTables(con.getCatalog(), null, null, null);
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败!");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用于判断数据库中是否有指定名字的表
     *
     * @param tableName 想要寻找的表名
     * @return 返回true表示该数据库中有这个表
     */
    public boolean hasTable(String tableName) {
        try {
            rs = con.getMetaData().getTables(con.getCatalog(), null, tableName, null);
            if (rs.next()) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
