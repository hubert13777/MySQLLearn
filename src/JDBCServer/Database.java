package JDBCServer;

import java.io.*;
import java.sql.*;

public class Database {
    private String driver;
    private Connection con;
    private ResultSet rs;
    private Statement state;

    public Boolean jdbcConnection() {
        String url="", username="", password="";
        try {
            InputStream f=new FileInputStream("init.ini");
            BufferedReader bf=new BufferedReader(new InputStreamReader(f));
            String str;
            StringBuilder temp=new StringBuilder();
            char c;
            while(true){
                str=bf.readLine();
                temp.delete(0,temp.length());
                int i;
                for(i=0;i<str.length();i++){
                    c=str.charAt(i);
                    if(c!='=') temp.append(c);
                    else break;
                }

                if(str==null) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    public Boolean jdbcExit() { //true表示正常关闭
        try {
            this.con.close();
            this.state.close();
            this.rs.close();
            System.out.println("与 " + con.getCatalog() + " 的连接已关闭");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
