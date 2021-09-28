package JDBCServer;

import java.io.*;
import java.sql.*;

public class Database {
    private String driver;
    private Connection con;
    private ResultSet rs;
    private Statement state;

    public boolean jdbcConnection() {  //true成功
        String url="", username="", password="";
        try {
            InputStream f=new FileInputStream("init.ini");
            BufferedReader bf=new BufferedReader(new InputStreamReader(f));
            String str;
            StringBuilder name=new StringBuilder();
            StringBuilder inf=new StringBuilder();
            char c;
            while(true){
                str=bf.readLine();
                if(str==null) break;
                name.delete(0,name.length());
                inf.delete(0,inf.length());
                int i;
                for(i=0;i<str.length();i++){
                    c=str.charAt(i);
                    if(c!='=') name.append(c);
                    else break;
                }
                for(i=i+1;i<str.length();i++){
                    c=str.charAt(i);
                    inf.append(c);
                }
                switch (name.toString()){
                    case "DRIVER":
                        driver=inf.toString();
                        break;
                    case "URL":
                        url=inf.toString();
                        break;
                    case "USERNAME":
                        username=inf.toString();
                        break;
                    case "PASSWORD":
                        password=inf.toString();
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
            System.out.println("驱动加载失败!");
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

    public int simpleImplement(String sql){ //-1表示执行未成功
        if(con==null){
            System.out.println("未连接数据库！");
            return  -1;
        }
        try {
            state=con.createStatement();
            int res=state.executeUpdate(sql); //执行SQL语句

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet queryImplement(String sql){
        if(con==null) {
            System.out.println("未连接数据库！");
            return rs;
        }
        try {
            state=con.createStatement();
            rs=state.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean jdbcExit() { //true表示正常关闭
        try {
            if(state!=null) this.state.close();
            if(rs!=null) this.rs.close();
            if(con!=null) {
                this.con.close();
                System.out.println("与 " + con.getCatalog() + " 的连接已关闭");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int listAllTable(boolean flag){  //返回表的个数,参数为true则打印
        if(con==null){
            System.out.println("未连接数据库！");
            return -1;
        }
        int tableCount=0;
        try {
            rs=con.getMetaData().getTables(con.getCatalog(),null,null,null);
            while(rs.next()){
                tableCount++;
                if (flag) System.out.println(tableCount+rs.getString("TABLE_NAME"));
            }
            return tableCount;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
