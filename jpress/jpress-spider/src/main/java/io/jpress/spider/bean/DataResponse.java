package io.jpress.spider.bean;

public class DataResponse<T> {
    private T mData;
    private boolean isSuccess;
    private String mMsg;
    private int mCode;
    private String mUrl;
    private String mUrlPaht;

    public DataResponse() {
    }

    public DataResponse(T data, boolean isSuccess, String msg, int code, String url) {
        mData = data;
        this.isSuccess = isSuccess;
        mMsg = msg;
        mCode = code;
        mUrl = url;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlPaht() {
        return mUrlPaht;
    }

    public void setUrlPaht(String urlPaht) {
        mUrlPaht = urlPaht;
    }

//    public boolean isHit(String path) {
//        if (getUrlPaht() == null) {
//            return false;
//        } else {
//            return getUrlPaht().endsWith(path.startsWith("../") ? path.replace("../", "") : Config.API_VERSION.concat(path));
//        }
//    }
}