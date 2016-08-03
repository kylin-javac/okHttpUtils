package cn.ligang.com.okhttputils.bean;

/**
 * author: 李刚 on 2016/8/1 16:55
 * email: kylin_javac@outlook.com
 * desc :
 */
public class PhotosBean {
    private DatasBean datasBean;

    public DatasBean getDatasBean() {
        return datasBean;
    }

    public void setDatasBean(DatasBean datasBean) {
        this.datasBean = datasBean;
    }

    @Override
    public String toString() {
        return "PhotosBean{" +
                "datasBean=" + datasBean +
                '}';
    }
}
