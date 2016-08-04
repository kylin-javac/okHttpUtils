package cn.ligang.com.okhttputils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.ligang.com.okhttputils.bean.ObejectBean;
import cn.ligang.com.okhttputils.bean.ResultsBean;

/**
 * Created by javac on 2016/8/4.
 * Email:kylin_javac@outlook.com
 * Desc:
 */
public class JsonCallback implements okUtils.HandleMess {
    private  AddList list;

    public JsonCallback(AddList addList) {
        this.list=addList;
    }

    @Override
    public void handle(byte bs[]) {
        try {
            String str = new String(bs);
            System.out.println("----------handle----------------"+str);
            JSONObject jsonObject = new JSONObject(str);
            List<ResultsBean> list1 = JsonParse(jsonObject);
            list.addlist(list1);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public  interface  AddList{
        void addlist(List<ResultsBean> list);
    }


    /**
     * json数据 解析方法
     *
     * @param jsonObject
     */
    private List<ResultsBean> JsonParse(JSONObject jsonObject) {
        try {
            ObejectBean obejectBean = new ObejectBean();
            String erro = jsonObject.getString("error");
            obejectBean.setError(erro);
            JSONArray results = jsonObject.getJSONArray("results");
            ArrayList<ResultsBean> resultsBeens = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                ResultsBean resultsBean = new ResultsBean();
                JSONObject jsonObject1 = results.getJSONObject(i);
                resultsBean.setUrl(jsonObject1.getString("url"));
                resultsBean.setDesc(jsonObject1.getString("desc"));
                resultsBeens.add(resultsBean);
            }
            obejectBean.setResultsBeen(resultsBeens);
            System.out.println(obejectBean);
            List<ResultsBean> list = obejectBean.getResultsBeen();
            return list;
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }
}
