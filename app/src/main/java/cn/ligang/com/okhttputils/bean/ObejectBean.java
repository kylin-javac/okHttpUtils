package cn.ligang.com.okhttputils.bean;

import java.util.List;

/**
 * Created by javac on 2016/8/1.
 * Email:kylin_javac@outlook.com
 * Desc:
 */
public class ObejectBean {
    private String error;
    private List<ResultsBean> resultsBeen;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ResultsBean> getResultsBeen() {
        return resultsBeen;
    }

    public void setResultsBeen(List<ResultsBean> resultsBeen) {
        this.resultsBeen = resultsBeen;
    }

    @Override
    public String toString() {
        return "ObejectBean{" +
                "error='" + error + '\'' +
                ", resultsBeen=" + resultsBeen +
                '}';
    }
}
