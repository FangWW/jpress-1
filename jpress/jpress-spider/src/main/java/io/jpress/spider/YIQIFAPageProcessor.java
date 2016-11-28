package io.jpress.spider;

import com.jfinal.log.Log;
import io.jpress.spider.inter.SpriderInterface;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.5.1
 */
public class YIQIFAPageProcessor implements PageProcessor, SpriderInterface {
    private Log logger = Log.getLog(getClass());
    public static final String YQF_URL = "http://m.smzdm.com/search/ajax_search_list?type=fenlei&search_key=gehuhuazhuang&channel=youhui&article_date=";
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000 * 60 * 2).setRetrySleepTime(1000 * 60);
    private int count;
    private int page_size = 20;
    public static final int RECYCLE_NUM = 1;
    private Spider mSpider;

    @Override
    public void process(Page page) {
        synchronized (YIQIFAPageProcessor.class) {}

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider.create(new SMZDMPageProcessor()).addUrl("http://m.smzdm.com/p/6542544/").thread(1).run();
        new YIQIFAPageProcessor().spriderStart();
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
        mSpider = Spider.create(this).addUrl(getUrl()).setSpiderListeners(spiderListener).thread(1);
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
        return YQF_URL + URLEncoder.encode(format.format(Calendar.getInstance().getTime()));
    }

    @Override
    public boolean isRunning() {
        return mSpider != null && mSpider.getStatus() == Spider.Status.Running;
    }
}
