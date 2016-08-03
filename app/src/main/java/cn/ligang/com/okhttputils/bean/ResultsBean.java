package cn.ligang.com.okhttputils.bean;

/**
 * Created by javac on 2016/8/1.
 * Email:kylin_javac@outlook.com
 * Desc:
 */
public class ResultsBean {
    private String url;
    private String desc;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ResultsBean{" +
                "url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
