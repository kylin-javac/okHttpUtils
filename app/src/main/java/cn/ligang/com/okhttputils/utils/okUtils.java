package cn.ligang.com.okhttputils.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by javac on 2016/8/1.
 * Email:kylin_javac@outlook.com
 * Desc:okhttp封装类
 */
public class okUtils {
    private static OkHttpClient okHttpClient;
    private static Handler handler;

    //单例模式获取对象实例
    private okUtils() {
        okHttpClient = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    private static okUtils utils = new okUtils();

    public static okUtils getInstance() {
        return utils;
    }


    /**
     * 异步请求加载成功并返回json字符串
     *
     * @param url
     * @param callback
     */
    public void asyncJsonString(String url, final Fun4 callback) {
        final Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessForjsonobject(response.body().string(), callback);
                }
            }
        });

    }

    /**
     * 异步加载成功并返回bytes数组
     *
     * @param url
     * @param callback
     */
    public void asynByte(String url, final Fun2 callback) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessForbyte(response.body().bytes(), callback);
                }
            }
        });

    }

    /**
     * 异步加载成功并返回bimap对象
     *
     * @param url
     * @param callback
     */
    public void asynBimap(String url, final Fun3 callback) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    byte bs[] = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
                    onSuccessForbimap(bitmap, callback);
                }
            }
        });
    }

    /**
     * 异步加载成功并返回string字符串
     *
     * @param callback
     */

    public void asynString(String url, final Fun1 callback) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessForjson(response.body().string(), callback);
                }
            }
        });

    }

    /**
     * 返回成功后的结果要在主线程中进行处理
     */
    public void onSuccessForjson(final String json, final Fun1 callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.Result(json);
                }
            }
        });
    }

    public void onSuccessForbyte(final byte bs[], final Fun2 callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.Result(bs);
                }
            }
        });

    }

    public void onSuccessForbimap(final Bitmap bitmap, final Fun3 callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.Result(bitmap);
                }
            }
        });
    }

    public void onSuccessForjsonobject(final String jsonValue, final Fun4 callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    try {
                        callback.Result(new JSONObject(jsonValue));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 返回的是json串
     */
    public interface Fun1 {
        void Result(String json);

    }

    /**
     * 返回的是byte数组
     */
    public interface Fun2 {
        void Result(byte[] bytes);
    }

    /**
     * 返回的是bitmap
     */
    public interface Fun3 {
        void Result(Bitmap bitmap);
    }

    /**
     * 返回的是json对象
     */
    public interface Fun4 {
        void Result(JSONObject jsonObject);
    }

}
