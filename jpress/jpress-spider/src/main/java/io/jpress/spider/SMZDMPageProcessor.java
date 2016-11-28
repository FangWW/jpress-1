package io.jpress.spider;

import com.jayway.jsonpath.JsonPath;
import com.jfinal.log.Log;
import io.jpress.message.Actions;
import io.jpress.message.MessageKit;
import io.jpress.spider.bean.ContentSpider;
import io.jpress.spider.inter.SpriderInterface;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Json;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.5.1
 */
public class SMZDMPageProcessor implements PageProcessor, SpriderInterface {
    //http://m.smzdm.com/search/ajax_search_list?article_date=2016-11-01+10%3A17%3A54&type=tag&search_key=%E7%A5%9E%E4%BB%B7%E6%A0%BC&channel=youhui
    //http://m.smzdm.com/search/ajax_search_list?type=fenlei&search_key=gehuhuazhuang&channel=youhui&article_date=2016-11-02+15%3A25%3A36
    private Log logger = Log.getLog(getClass());
    public static final String SMZDM_GHMZ = "http://m.smzdm.com/search/ajax_search_list?type=fenlei&search_key=gehuhuazhuang";
    public static final String SMZDM_URL = SMZDM_GHMZ + "&channel=youhui&article_date=";
    public static final String SMZDM_URL_HAITAO = SMZDM_GHMZ + "&channel=haitao&article_date=";
    //    public static final String YQF_URL = "http://m.smzdm.com/ajax_home_list_show?time_sort=";
    public static final String SMZDM_P_URL = "http://m.smzdm.com/p/";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000 * 30).setRetrySleepTime(1000 * 60);
    private int count;
    private int page_size = 20;
    public static final int RECYCLE_NUM = 1;
    private Spider mSpider;

    @Override
    public void process(Page page) {
        synchronized (SMZDMPageProcessor.class) {
            String url = page.getUrl().toString();
            if (url != null && url.startsWith(SMZDM_GHMZ)) {
                Json json = page.getJson();
                String time_s = JsonPath.read(json.get(), "$.article_date");
                String message = JsonPath.read(json.get(), "$.message");
                if (message.equals("0")) {
                    logger.info(time_s);
                    logger.info("============");
                    String data = JsonPath.read(json.get(), "$.data").toString();
//                logger.info(data);
                    Html html = new Html(data);
                    List<String> b = html.xpath("li/h2/text()").all();
                    List<String> a = html.xpath("li/a/@href|//h2/text()|//div/em/text()").all();
                    int size = a.size();
                    if (size % page_size == 0)
                        for (int i = 0; i < size / 3; i++) {
                            logger.info("url" + a.get(i) + "标题" + a.get(i + page_size) + "价格" + a.get(i + page_size * 2));
                            page.addTargetRequest(a.get(i));
                        }
                    count++;
                    if (count < RECYCLE_NUM) {
                        page.addTargetRequest(SMZDM_URL + URLEncoder.encode(time_s));
                    } else {
                        page.addTargetRequest(SMZDM_URL_HAITAO + URLEncoder.encode(format.format(Calendar.getInstance().getTime())));
                        page.addTargetRequest(getUrl());
                        count = 0;
                    }
                }
            } else if (url != null && url.startsWith(SMZDM_P_URL)) {
                Html html = page.getHtml();
                /**
                 * 图片
                 * 标题
                 * url详情
                 * 详情
                 */
                html.xpath("//div[@class='owl-carousel']/div/img/@src").get();
                html.xpath("//div[@class='details-title']/div[@class='title']/text()").get();
                html.xpath("//div[@class='details-title']/a/@href").get();
                html.xpath("//article/p/text()").get();

                List<String> detail = html.xpath("//div[@class='details-title']/div[@class='title']/text()" +
                        "|//div[@class='details-title']/div[@class='title']/span/text()" +
                        "|//div[@class='details-title']/a/@href" +
                        "|//header[@class='head_banner']/text()" +
                        "|//div[@class='owl-carousel']/div/img/@src" +
                        "|//article/p").all();
                List imgs = new ArrayList<String>();
                ContentSpider contentSpider = new ContentSpider();
                contentSpider.setImg(imgs);

                List<BigInteger> ids = new ArrayList<>();
                ids.add(BigInteger.valueOf(1));//tag  数据库 分类 id
                ids.add(BigInteger.valueOf(2));//categroy  数据库 分类 id

                for (int i = 0, size = detail.size(); i < size; i++) {
                    String str = detail.get(i);
                    str.replaceAll("值友", "");
                    str.replaceAll("什么值得买", "");
                    str.replaceAll("SMZDM", "");
                    str.replaceAll("smzdm", "");
                    switch (i) {
                        case 0://标题
                            contentSpider.setTitle(str);
                            break;
                        case 1://子标题
                            contentSpider.setSubTitle(str);
                            break;
                        case 2://链接
                            contentSpider.setLink(str);
                            break;
                        case 3://标题
                            if (str.trim().startsWith("海淘")) {
                                ids.add(BigInteger.valueOf(3));//数据库 分类 id
                            }
                            break;
                        default://图片&文本
                            if (str.startsWith("http")) {
                                imgs.add(str);
                            } else {
                                contentSpider.setText(contentSpider.getText() + str);
                            }
                            break;

                    }



                    if (i == size - 1) {
                        contentSpider.setIds(ids);
                        MessageKit.sendMessage(Actions.SPIDER_GET, contentSpider);
                    }
                }
                for (String info : detail) {
                    logger.info(info);
                }
            }
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider.create(new SMZDMPageProcessor()).addUrl("http://m.smzdm.com/p/6542544/").thread(1).run();
        new SMZDMPageProcessor().spriderStart();
    }

    @Override
    public void spriderStart() {

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
        mSpider = Spider.create(this).addUrl(getUrl(),
                SMZDM_URL_HAITAO + URLEncoder.encode(format.format(Calendar.getInstance().getTime()))).setSpiderListeners(spiderListener).thread(1);
//        mSpider = Spider.create(this).addUrl(YQF_URL + System.currentTimeMillis() / 1000).thread(1);
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
        return SMZDM_URL + URLEncoder.encode(format.format(Calendar.getInstance().getTime()));
    }

    @Override
    public boolean isRunning() {
        return mSpider != null && mSpider.getStatus() == Spider.Status.Running;
    }
}


