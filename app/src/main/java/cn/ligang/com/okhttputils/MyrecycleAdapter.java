package cn.ligang.com.okhttputils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.ligang.com.okhttputils.bean.ResultsBean;

/**
 * Created by javac on 2016/8/1.
 * Email:kylin_javac@outlook.com
 * Desc:RecycleView的适配器
 */
public class MyrecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<ResultsBean> list;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView
    public static final int PULLUP_LOAD_MORE = 0; //上拉加载更多
    public static final int LOADING_MORE = 1; //正在加载中
    private int load_more_status = 0; //上拉加载更多状态

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param list    数据源
     */
    public MyrecycleAdapter(Context context, List<ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * list集合中的个数加1
     */
    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    /**
     * 根据不同的类型返回不同的适配器类型
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new LgViewHold(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.footview, parent, false);
            FootViewHolder footview1 = new FootViewHolder(view);
            return footview1;
        }
        return null;
    }


    /**
     * 根据适配器的类型加载不同的数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LgViewHold) {
            ResultsBean newsBean = list.get(position);
            LgViewHold lgViewHold = (LgViewHold) holder;
            lgViewHold.tex.setText(newsBean.getDesc());
            Picasso.with(context).load(newsBean.getUrl()).into(lgViewHold.img);
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_more.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_more.setText("正在加载更多数据...");
                    break;
            }
        }

    }

    /**
     * 进行判断是普通Item布局还是FootView布局
     *
     * @param position
     * @return
     */
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 加载更多并更新
     *
     * @param newDatas
     */
    public void addMoreItem(List<ResultsBean> newDatas) {
        list.addAll(newDatas);
        notifyDataSetChanged();
    }

    /**
     * 正文的布局
     */
    class LgViewHold extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView tex;

        public LgViewHold(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tex = (TextView) itemView.findViewById(R.id.title);
        }
    }


    /**
     * 底部FootView布局
     */
    public class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_more;

        public FootViewHolder(View view) {
            super(view);
            foot_more = (TextView) view.findViewById(R.id.more);
        }
    }


}