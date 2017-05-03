package com.example.myandroidlibrary.okhttp.response;

/**
 * Created by weinp on 2017/5/3.
 */

import android.os.Handler;
import android.os.Looper;

import com.example.myandroidlibrary.okhttp.exception.OkHttpException;
import com.example.myandroidlibrary.okhttp.listener.DisposeDataHandle;
import com.example.myandroidlibrary.okhttp.listener.DisposeDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import adutil.ResponseEntityToModule;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author wienp
 * @function
 * 1.执行我们的回调函数
 * 2.异常处理
 * 3.转发消息到我们的UI线程
 * 4.将json数据转化为实体对象
 */

public class CommonJsonCallBack implements Callback {
    protected final String RESULT_CODE = "ecode";
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;


    //注音这里的Handler使用的是android.os包下面的Handler类
    private Handler mDeliveryHandler;
    private DisposeDataHandle handle;

    public CommonJsonCallBack(DisposeDataHandle handle) {
        this.handle = handle;
        mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handle.mListener.onFailure(new OkHttpException(NETWORK_ERROR, e.getMessage()));
            }
        });
    }


    //真正的响应函数
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();

        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResopnse(result);
            }
        });
    }

    /**
     * 用来处理json数据
     *
     * @param result
     */
    private void handleResopnse(Object result) {
        if (result == null && result.toString().trim().equals("")) {
            handle.mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {
            JSONObject jsonresult = new JSONObject(result.toString());
            if(jsonresult.has(RESULT_CODE)){
                //从json对象中取出我们的响应码，若为0，则是正确的响应
                if(jsonresult.getInt(RESULT_CODE) == RESULT_CODE_VALUE){
                    if(handle.mClass==null){
                        handle.mListener.onSucess(result);
                    }else{
                        Object obj = ResponseEntityToModule.parseJsonObjectToModule(jsonresult,handle.mClass);
                        //正确的转化了实体对象
                        if(obj !=null){
                            handle.mListener.onSucess(obj);
                        }else{
                            //返回的json不是合法的Json
                            handle.mListener.onFailure(new OkHttpException(JSON_ERROR , EMPTY_MSG));
                        }
                    }
                }else{
                    handle.mListener.onFailure(new OkHttpException(OTHER_ERROR , jsonresult.get(RESULT_CODE)));
                }
            }
        } catch (Exception e) {
            handle.mListener.onFailure(new OkHttpException(OTHER_ERROR , e.getMessage()));
        }
    }

}
