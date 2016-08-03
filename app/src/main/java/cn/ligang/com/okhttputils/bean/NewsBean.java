package cn.ligang.com.okhttputils.bean;

/**
 * author: 李刚 on 2016/8/1 16:16
 * email: kylin_javac@outlook.com
 * desc :
 */
public class NewsBean {
    private int id;
    private String listimage;
    private String title;


    @Override
    public String toString() {
        return "NewsBean{" +
                "id=" + id +
                ", listimage='" + listimage + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getListimage() {
        return listimage;
    }

    public void setListimage(String listimage) {
        this.listimage = listimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
