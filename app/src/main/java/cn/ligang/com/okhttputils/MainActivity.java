package cn.ligang.com.okhttputils;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.ligang.viewinject.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.ligang.com.okhttputils.bean.ObejectBean;
import cn.ligang.com.okhttputils.bean.ResultsBean;
import cn.ligang.com.okhttputils.utils.okUtils;

public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.recycleView)
    private RecyclerView rec;
    @ViewInject(R.id.sw)
    private SwipeRefreshLayout sw;
    private List<ResultsBean> ResultsBeans = new ArrayList<>();
    okUtils utils = okUtils.getInstance();//获取okhttp工具类的实例
    int num = 5;
    public static final String PATH = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/";
    private int lastVisibleItem;//当前页最后一个item
    MyrecycleAdapter adapter;//适配器
    LinearLayoutManager linearLayoutManager;//线性布局管理
    StaggeredGridLayoutManager staggeManager;//瀑布流布局管理器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.ligang.viewinject.ViewUtils.inject(this);
        /**
         * 通过okhttp的封装类中的异步请求Json对象的方法,实现数据下载
         */
        utils.asyncJsonString(PATH + num, new okUtils.Fun4() {
            @Override
            public void Result(JSONObject jsonObject) {
                List<ResultsBean> list = JsonParse(jsonObject);
                ResultsBeans.addAll(list);
                Vertical();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        staggeManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new MyrecycleAdapter(MainActivity.this, ResultsBeans);


        /**
         * 下拉加载更多
         */
        rec.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /**
                 * 判断当前位置是否到了最后一个,加载更多
                 */
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {

                    utils.asyncJsonString(PATH + (++num), new okUtils.Fun4() {
                        @Override
                        public void Result(JSONObject jsonObject) {
                            List<ResultsBean> list = JsonParse(jsonObject);
                            adapter.addMoreItem(list);
                        }
                    });
                }
            }

            /**
             * 获取到当前页面最后一个item
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });


        /**
         * 模拟下拉刷新
         */
       sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       SystemClock.sleep(1000);
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               sw.setRefreshing(false);
                               adapter.notifyDataSetChanged();
                           }
                       });

                   }
               }).start();
           }
       });


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


    /**
     * 设置成瀑布流的形式
     */
    public void Staggered() {
        rec.setLayoutManager(staggeManager);
        rec.setAdapter(adapter);
    }

    /**
     * 设置成纵向平铺的形式
     */
    public void Vertical() {
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(adapter);
    }


    /**
     * 当前activity添加菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 根据菜单ID 做出相应操作
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ver:
                Vertical();
                break;
            case R.id.stagg:
                Staggered();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
