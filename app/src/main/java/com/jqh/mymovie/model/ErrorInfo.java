package com.jqh.mymovie.model;

import com.google.gson.annotations.Expose;
import com.jqh.mymovie.AppManager;

/**
 * Created by jiangqianghua on 18/1/13.
 */

public class ErrorInfo {

    public static final int ERROR_TYPE_HTTP = 1;

    public static  final int ERROR_TYPE_URL = 2 ;

    public static final  int ERROR_TYPE_FATAL = 3;

    public static final  int ERROR_TYPE_DATA_CONVERT = 4;

    public static final  int ERROR_TYPE_DATA_PARSE_JSON= 5;
    @Expose //区分实体中不想被序列化的属性，自身包含序列化和反序列化
    int type;
    @Expose
    String tag;

    @Expose
    String functionName ;

    @Expose
    String className ;

    @Expose
    Site site ;

    @Expose
    String reason ;  // 错误原因

    @Expose
    String exceptionString;

    @Expose
    String url;

    public ErrorInfo(int siteId,int type)
    {
        site = new Site(siteId, AppManager.getContext());
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExceptionString() {
        return exceptionString;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "type=" + type +
                ", tag='" + tag + '\'' +
                ", functionName='" + functionName + '\'' +
                ", className='" + className + '\'' +
                ", site=" + site +
                ", reason='" + reason + '\'' +
                ", exceptionString='" + exceptionString + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
