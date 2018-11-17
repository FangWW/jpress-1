package io.jpress.spider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.jpress.spider.bean.CategoryBean;
import okhttp3.*;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LZ13toWordPress {
    public static final String host = "https://f907e830-129a-4a69-b355-e54be2e65284.coding.io";
    public static final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvZjkwN2U4MzAtMTI5YS00YTY5LWIzNTUtZTU0YmUyZTY1Mjg0LmNvZGluZy5pbyIsImlhdCI6MTUyMjMwMTg4OSwibmJmIjoxNTIyMzAxODg5LCJleHAiOjE1MjI5MDY2ODksImRhdGEiOnsidXNlciI6eyJpZCI6IjEifX19.MElXR-0FV2hEB2KSx3dhrgndFtkc7v1J_1kYLyVeI7U";
    private Connection con;
    private OkHttpClient mClient;
    Gson mGson = new Gson();
    HashMap<String, String> categoryMap = new HashMap<>();

    public static void main(String[] args) {
        LZ13toWordPress lz13GeneraterMD = new LZ13toWordPress();
        lz13GeneraterMD.openDatabase();
        lz13GeneraterMD.dealCategory();
        lz13GeneraterMD.dealFile();
    }


    public void dealCategory() {
        try {
            getAllCategory();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM chickensoup.article_info group by article_category";
            ResultSet rs_exsit = statement.executeQuery(sql);
            while (rs_exsit.next()) {
                final String article_category = rs_exsit.getString("article_category");
                if (!TextUtils.isEmpty(categoryMap.get(article_category))) {
                    continue;
                }
                RequestBody requestBodyPost = new FormBody.Builder()
                        .add("name", article_category)
                        .build();
                Request request = new Request.Builder()
                        .url(host + "/wp-json/wp/v2/categories")
                        .post(requestBodyPost)
                        .addHeader("Authorization", token)
                        .build();
                String result = mClient.newCall(request).execute().toString();
                System.out.println(article_category);
                System.out.println(result);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        System.out.println(e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        System.out.println(article_category);
//                        System.out.println(response.toString());
//                    }
//                });
                Thread.sleep(1000);
            }
            getAllCategory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllCategory() throws IOException {
        categoryMap.clear();
        Request requestCategories = new Request.Builder()
                .url(host + "/wp-json/wp/v2/categories?per_page=100")
                .addHeader("Authorization", token)
                .build();
        String jsonCategroy = mClient.newCall(requestCategories).execute().body().string();
        System.out.println(jsonCategroy);
        List list = new ArrayList();
        JsonArray array = new JsonParser().parse(jsonCategroy).getAsJsonArray();
        if (array != null)
            for (final JsonElement elem : array) {
                CategoryBean e = mGson.fromJson(elem, CategoryBean.class);
                list.add(e);
                if (e != null) {
                    categoryMap.put(e.getName(), e.getId());
                }
            }
    }

    public void dealFile() {
        try {
            Statement statement = con.createStatement();
//            String sql = "SELECT * FROM chickensoup.article_info LIMIT 10";
            String sql = "SELECT * FROM chickensoup.article_info where id>2635";
            ResultSet rs_exsit = statement.executeQuery(sql);
            while (rs_exsit.next()) {
                final String article_Name = rs_exsit.getString("article_Name");
                String article_category = rs_exsit.getString("article_category");
                String article_date = rs_exsit.getString("article_date");
                String article_source = rs_exsit.getString("article_source");
                String article_author = rs_exsit.getString("article_author");
                int Id = rs_exsit.getInt("Id");
//                TxtExport.creatTxtFile(article_Name);

                StringBuffer sb = new StringBuffer();
//                sb.append("title: ").append(article_Name).append("\n")
//                        .append("date: ").append(article_date).append("\n")
//                        .append("comments: true\n")
//                        .append("categories: ").append(article_category).append("\n")
////                        .append("permalink: :categories/:title/\n")
//                        .append("\n---\n")
//                        .append("\n");
                if (!TextUtils.isEmpty(article_author)) {
                    sb.append(article_author).append("\n\n");
                } else {
                    sb.append("　　文/");
                    sb.append(article_source).append("\n\n");
                }
                Statement statement_detail = con.createStatement();
                String sql_detail = "SELECT * FROM chickensoup.article_detail where article_id =".concat(String.valueOf(Id));
                ResultSet rs_detail = statement_detail.executeQuery(sql_detail);
                while (rs_detail.next()) {

                    sb.append("　　");
                    String article_line = rs_detail.getString("article_line").trim();
//                    if (article_line.contains("努力")) {
//                        sb.append(article_line.replaceAll("努力", "[努力](http://chickensoup.top)")).append("\n\n");
//                    } else if (article_line.contains("奋斗")) {
//                        sb.append(article_line.replaceAll("奋斗", "[奋斗](http://chickensoup.top)")).append("\n\n");
//                    } else if (article_line.contains("加油")) {
//                        sb.append(article_line.replaceAll("加油", "[加油](http://www.chickensoup.top)")).append("\n\n");
//                    } else {
                    sb.append(article_line).append("\n\n");
//                    }
                }
                RequestBody requestBodyPost = new FormBody.Builder()
                        .add("title", article_Name)
                        .add("content", sb.toString())
                        .add("categories", categoryMap.get(article_category))
                        .add("status", "publish")
                        .build();
                Request request = new Request.Builder()
                        .url(host + "/wp-json/wp/v2/posts")
                        .post(requestBodyPost)
                        .addHeader("Authorization", token)
                        .build();
                Response execute = mClient.newCall(request).execute();
                String result = execute.toString();
                System.out.println(Id + ":" + article_Name);
                System.out.println(result);
//                Call call = mClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        System.out.println(e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        System.out.println(article_Name);
//                        System.out.println(response.toString());
//                    }
//                });

                Thread.sleep(1000);
                execute.close();

//                TxtExport.writeTxtFile(sb.toString());
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

        mClient = new OkHttpClient.Builder()
                .build();


        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://192.168.8.161:3306/chickensoup?useUnicode=true&characterEncoding=utf-8";
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
