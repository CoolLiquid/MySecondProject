package com.example.myandroidlibrary.okhttp.listener;

/**
 * Created by weinp on 2017/5/3.
 */


/**
 * 自定义事件监听事件
 */
public interface DisposeDataListener {
    /**
     * 请求成功回调的事件处理
     * @param responseObj
     */
    public void onSucess(Object responseObj);

    /**
     * 请求失败回调的事件处理
     * @param responseObj
     */
    public void onFailure(Object responseObj);
}
