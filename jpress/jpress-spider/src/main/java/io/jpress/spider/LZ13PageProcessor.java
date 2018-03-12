package io.jpress.spider;

import com.jfinal.log.Log;
import io.jpress.spider.inter.SpriderInterface;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.5.1
 */
public class LZ13PageProcessor implements PageProcessor, SpriderInterface {
    //http://m.smzdm.com/search/ajax_search_list?article_date=2016-11-01+10%3A17%3A54&type=tag&search_key=%E7%A5%9E%E4%BB%B7%E6%A0%BC&channel=youhui
    //http://m.smzdm.com/search/ajax_search_list?type=fenlei&search_key=gehuhuazhuang&channel=youhui&article_date=2016-11-02+15%3A25%3A36
    private Log logger = Log.getLog(getClass());

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000*36).setRetrySleepTime(1000 * 60);
    //private int count = 71;
    private int count = 142;
    private Spider mSpider;

 //   private static final String list_url = "http://m.lz13.cn/lizhi/lizhiwenzhang-";
   // private static final String detail_url = "http://m.lz13.cn/lizhiwenzhang/";
    private static final String list_url = "http://m.lz13.cn/lizhi/jingdianyulu-";
    private static final String detail_url = "http://m.lz13.cn/jingdianyulu/";

    @Override
    public void process(Page page) {
        synchronized (LZ13PageProcessor.class) {
            try {  String url = page.getUrl().toString();
            Html html = page.getHtml();
            if (url != null && url.startsWith(list_url)) {
//                List<String> detail = html.xpath("@div[@class='tablist newlist']/ul/li/a/@href|//div[@class='tablist newlist']/ul/li/a/text()").all();
                List<String> list = html.xpath("//div[@class='tablist newlist']/ul/li/a/@href").all();
                for (String s : list) {

                    page.addTargetRequest(s);
                }
                count--;
                if (count>0){

                    page.addTargetRequest(getUrl());
                }

            } else if (url != null && url.startsWith(detail_url)) {
                List<String> list = html.xpath("//div[@class='headtitle']/h1/text()|//div[@class='headtitle']/p/a/text()|//div[@id='endtext']/p/text()").all();
                    Statement statement = con.createStatement();
                    String sql = "SELECT * FROM chickensoup.article_info WHERE article_Name ='" + list.get(0) + "'";
                    ResultSet rs_exsit = statement.executeQuery(sql);
                    while (rs_exsit.next()) {
                        return;
                    }
                    boolean has_author=list.get(3).contains("文/");
                    String insert_info_sql =
                            "INSERT INTO `chickensoup`.`article_info` (`article_Name`, `article_category`, `article_date`, `article_source`, `article_author`) VALUES ('" +
                                    list.get(0) + "','" + list.get(1) + "', '', 'lz13', '" +
                                    ( has_author?list.get(3) :"")+ "')";
                    statement.execute(insert_info_sql);
               //     String sql = "SELECT * FROM chickensoup.article_info WHERE article_Name ='" + list.get(0) + "'";
                    ResultSet rs = statement.executeQuery(sql);
                    String id = null;
                    while (rs.next()) {
                        id = rs.getString("id");
                        break;
                    }
                    for (int i = has_author?4:3; i < list.size(); i++) {
                        if (!con.isClosed()) {
                            String insert_line_sql =
                                    "INSERT INTO `chickensoup`.`article_detail` (`article_Id`, `article_line`) VALUES ('" +
                                            id + "', '" + list.get(i) + "')";
                            statement.execute(insert_line_sql);
                        }
                    }


            }    } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider.create(new SMZDMPageProcessor()).addUrl("http://m.smzdm.com/p/6542544/").thread(1).run();
        new LZ13PageProcessor().spriderStart();
    }

    //声明Connection对象
    Connection con;

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
            //2.创建statement类对象，用来执行SQL语句！！
//            Statement statement = con.createStatement();
//            //要执行的SQL语句
//            String insert_info_sql = "INSERT INTO `chickensoup`.`article_info` (`article_Name`, `article_category`, `article_date`, `article_source`) VALUES ('3', '888', '5', '5')";
//            statement.execute(insert_info_sql);
//            String sql = "SELECT * FROM chickensoup.article_info";
//            //3.ResultSet类，用来存放获取的结果集！！
//            ResultSet rs = statement.executeQuery(sql);
//            System.out.println("-----------------");
//            System.out.println("执行结果如下所示:");
//            System.out.println("-----------------");
//            System.out.println("姓名" + "\t" + "职称");
//            System.out.println("-----------------");
//
//            String job = null;
//            String id = null;
//            while (rs.next()) {
//                //获取stuname这列数据
//                job = rs.getString("article_source");
//                //获取stuid这列数据
//                id = rs.getString("article_date");
//
//                //输出结果
//                System.out.println(id + "\t" + job);
//            }
//            rs.close();
//            con.close();
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

    @Override
    public void spriderStart() {
        openDatabase();
//        List<String[]> httpProxyPool = new ArrayList<>();
//        httpProxyPool.add(new String[]{"101.53.101.172", "9999"});
//        getSite().setHttpProxyPool(httpProxyPool);


        List spiderListener = new ArrayList<SpiderListener>();
        spiderListener.add(new SpiderListener() {
            @Override
            public void onSuccess(Request request) {

            }

            @Override
            public void onError(Request request) {
                logger.info("onError=>" + request.getUrl());
                mSpider.addRequest(new Request(getUrl()));
            }
        });
        mSpider = Spider.create(this).addUrl(getUrl()).setSpiderListeners(spiderListener).thread(1);
//        mSpider = Spider.create(this).addUrl(YQF_URL + System.currentTimeMillis() / 1000).thread(1);
        mSpider.run();
    }

    @Override
    public void spriderStop() {
        mSpider.stop();
    }


    private String getUrl() {
        return list_url + count + ".html";
    }

    @Override
    public boolean isRunning() {
        return mSpider != null && mSpider.getStatus() == Spider.Status.Running;
    }
}


