package Demo;

import java.sql.*;

public class ConnectionTest {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "7878555";
        String tablename = "students";

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            if (!con.isClosed())
                System.out.println("成功连接到数据库");
            //创建statement类对象，用来执行SQL语句
            Statement state = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from " + tablename;
            //rs用来存放获取的数据集
            ResultSet rs = state.executeQuery(sql);
            System.out.println("----------------");
            System.out.println("ID" + "\t" + "姓名" + "\t" + "年龄" + "\t" + "性别");
            System.out.println("----------------");

            String id, name, sex;
            int age;
            while (rs.next()) {
                id = rs.getString("ID");
                name = rs.getString("Name");
                age = rs.getInt("Age");
                sex = rs.getString("Sex");

                System.out.println(id + "\t" + name + "\t" + age + "\t" + sex);
            }
            System.out.println("----------------");
            rs.close();
            state.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("can't find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("数据库数据获取成功！");
        }
    }
}