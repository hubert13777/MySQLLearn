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

    public Boolean jdbcExit() { //true表示正常关闭
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
}
