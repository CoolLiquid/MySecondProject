package com.example.myandroidlibrary.okhttp;

/**
 * Created by tgnet on 2017/5/3.
 */

import com.example.myandroidlibrary.okhttp.https.HttpsUtils;
import com.example.myandroidlibrary.okhttp.response.CommonJsonCallBack;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wienp
 * @Function 请求的发送 ，请求参数的配置 ，Https配置
 */
public class CommonOkhttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    //为我们的Client配置参数
    static {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .followRedirects(true).hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                }).sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());

        mOkHttpClient = okHttpBuilder.build();
    }


    //写参数的时候，使用子接口代替父接口，是合法的
    public static Call sendRequest(Request request, CommonJsonCallBack commCallback) {
        Call call = null;
        if (request != null && commCallback != null) {
            call = mOkHttpClient.newCall(request);
            call.enqueue(commCallback);
        }
        return call;
    }


}
