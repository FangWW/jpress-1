package io.jpress.spider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jfinal.log.Log;
import io.jpress.message.Actions;
import io.jpress.message.MessageKit;
import io.jpress.spider.bean.ContentSpider;
import io.jpress.spider.inter.SpriderInterface;
import io.jpress.spider.selenium.SeleniumDownloader;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import okhttp3.OkHttpClient;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Json;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.5.1
 */
public class BaiduGaokaoPageProcessor implements PageProcessor, SpriderInterface {
    private Log logger = Log.getLog(getClass());
    public static final String list_url = "https://wk.baidu.com/topic/composition2017/getList?pn={count}&rn=10&content_type=1";
    public static final String detail_url = "https://wk.baidu.com/view/";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000 * 30).setRetrySleepTime(1000 * 60);
    private int count;
    private int page_size = 20;
    public static final int RECYCLE_NUM = 1;
    private Spider mSpider;

    private Connection con;

    @Override
    public void process(Page page) {
        synchronized (BaiduGaokaoPageProcessor.class) {
            String url = page.getUrl().toString();
            if (url != null && url.startsWith(detail_url)) {

                String md5 = url.replace(detail_url, "");

                Html html = new Html(page.getRawText());
//                List<String> detail = html.xpath("//p[@class='reader-word-layer']/text()").all();
//                String i = "";
//                for (String info : detail) {
////                    logger.info(info);
//                    info = info.trim();
//
//                    if (" ".equals(info) || "".equals(info)) {
//
//                        if (!"".equals(i)) {
//                            try {
//                                Statement statement = con.createStatement();
//                                String insert_info_sql = "INSERT INTO zuowencontent VALUES (null,\"" +
//                                        md5 + "\",\" " +
//                                        i + "\")";
//                                statement.execute(insert_info_sql);
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                        System.out.println("");
//                        i = "";
//                    } else {
//                        i += info;
//                        System.out.print(info);
//                    }
//                }
                List<String> detail = html.xpath("//p[@class='txt']/text()").all();
                for (String info : detail) {
                    System.out.println(info);
                    try {
                        Statement statement = con.createStatement();
                        String insert_info_sql = "INSERT INTO zuowencontent VALUES (null,\"" +
                                md5 + "\",\" " +
                                info + "\")";
                        statement.execute(insert_info_sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                String rawText = page.getRawText();
                String jsonText = rawText.substring(84, rawText.length() - 20);
//                JsonObject json = new Gson().toJson(jsonText);
                Json json = new Json(jsonText);
//                Json json = page.getJson();
                JSONArray datalist = JsonPath.read(json.get(), "$.data.list");
                for (Object jsonElement : datalist) {
                    JSONObject item = (JSONObject) jsonElement;
                    System.out.println(item.get("tag"));
                    System.out.println(item.get("title"));
                    System.out.println(item.get("year"));
                    System.out.println(item.get("paperArea"));
                    System.out.println(item.get("abstract"));
                    Object md5 = item.get("md5");
                    System.out.println(md5);
                    page.addTargetRequest(detail_url + md5);

                    try {
                        Statement statement = con.createStatement();
                        String insert_info_sql = "INSERT INTO zuowentitle VALUES (null, \"" +
                                item.get("tag") + "\",\"" +
                                item.get("title") + "\",\"" +
                                item.get("year") + "\",\"" +
                                item.get("paperArea") + "\",\"" +
                                item.get("abstract") + "\",\" " +
                                item.get("md5") + "\")";
                        statement.execute(insert_info_sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                count++;
                page.addTargetRequest(getUrl());
//                if (count < RECYCLE_NUM) {
//                    page.addTargetRequest(SMZDM_URL + URLEncoder.encode(time_s));
//                } else {
//                    page.addTargetRequest(SMZDM_URL_HAITAO + URLEncoder.encode(format.format(Calendar.getInstance().getTime())));
//                    page.addTargetRequest(getUrl());
//                    count = 0;
//                }
            }
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider.create(new SMZDMPageProcessor()).addUrl("http://m.smzdm.com/p/6542544/").thread(1).run();
        BaiduGaokaoPageProcessor baiduGaokaoPageProcessor = new BaiduGaokaoPageProcessor();
        baiduGaokaoPageProcessor.openDatabase();
        baiduGaokaoPageProcessor.spriderStart();
    }

    @Override
    public void spriderStart() {

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
        mSpider = Spider.create(this).addUrl(getUrl()).setSpiderListeners(spiderListener).thread(1)
                .setDownloader(new SeleniumDownloader("D:\\Program Files\\chromedriver.exe"));
        mSpider.run();
    }

    @Override
    public void spriderStop() {
        mSpider.stop();
    }

    /**
     * 2016-11-01 15:26:16
     *
     * @return
     */
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String getUrl() {
        return list_url.replace("{count}", count + "");
    }

    @Override
    public boolean isRunning() {
        return mSpider != null && mSpider.getStatus() == Spider.Status.Running;
    }

    public void openDatabase() {
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://192.168.8.161:3306/lastQuestion?useUnicode=true&characterEncoding=utf-8";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "root";
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


