package io.jpress.spider.bean;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by fjw on 10/31/16.
 */
public class ContentSpider {
    private List<String> img;
    private String text = "";
    private String title;
    private String subTitle;
    private String link;
    // 文字 tag和categroy
    private List<BigInteger> ids;

    public List<BigInteger> getIds() {
        return ids;
    }

    public void setIds(List<BigInteger> ids) {
        this.ids = ids;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
