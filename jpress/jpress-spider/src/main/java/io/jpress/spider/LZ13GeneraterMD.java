package io.jpress.spider;

import org.apache.http.util.TextUtils;

import java.sql.*;

public class LZ13GeneraterMD {
    private Connection con;

    public static void main(String[] args) {
        LZ13GeneraterMD lz13GeneraterMD = new LZ13GeneraterMD();
        lz13GeneraterMD.openDatabase();
        lz13GeneraterMD.dealFile();
    }

    public void dealFile() {
        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM chickensoup.article_info LIMIT 4150";
            ResultSet rs_exsit = statement.executeQuery(sql);
            while (rs_exsit.next()) {
                String article_Name = rs_exsit.getString("article_Name");
                String article_category = rs_exsit.getString("article_category");
                String article_date = rs_exsit.getString("article_date");
                String article_source = rs_exsit.getString("article_source");
                String article_author = rs_exsit.getString("article_author");
                int Id = rs_exsit.getInt("Id");
                TxtExport.creatTxtFile(article_Name);

                StringBuffer sb = new StringBuffer();
                sb.append("title: ").append(article_Name).append("\n")
                        .append("date: ").append(article_date).append("\n")
                        .append("comments: true\n")
                        .append("categories: ").append(article_category).append("\n")
                        .append("\n---\n")
                        .append("\n");
                if (!TextUtils.isEmpty(article_author)) {
                    sb.append(article_author).append("\n\n");
                }
                Statement statement_detail = con.createStatement();
                String sql_detail = "SELECT * FROM chickensoup.article_detail where article_id =".concat(String.valueOf(Id));
                ResultSet rs_detail = statement_detail.executeQuery(sql_detail);
                while (rs_detail.next()) {
                    //sb.append("       ");
                    String article_line = rs_detail.getString("article_line").trim();
                    if (article_line.contains("女孩")) {
                        sb.append(article_line.replaceAll("女孩", "[女孩](http://chickensoup.top)")).append("\n\n");
                    } else {
                        sb.append(article_line).append("\n\n");
                    }
                }
                TxtExport.writeTxtFile(sb.toString());
//                TxtExport.writeTxtFile("title: 2017年上HTTPS： Ubuntu Apache 使用Let's Encrypt 免费SSL证书\n" +
//                        "date: 2017-01-02 00:26:56\n" +
//                        "comments: true\n" +
//                        "categories: \n" +
//                        "\n" +
//                        "---\n" +
//                        "\n" +
//                        "       auther\n" +
//                        "\n" +
//                        "      在chrome56版本中非https的网址直接会将之前的感叹号变成直接提示出来‘不安全’，如下。\n" +
//                        "\n" +
//                        "      在chrome56版本中非https的网址直接会将之前的感叹号变成直接提示出来‘不安全’，如下。\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() {
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/chickensoup?useUnicode=true&characterEncoding=utf-8";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "123456";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            System.out.println("数据库数据成功获取！！");
        }
    }


}
