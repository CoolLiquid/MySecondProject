package com.example.myandroidlibrary.okhttp.listener;

/**
 * Created by weinp on 2017/5/3.
 */

/**
 * 数据处理类：json-》实体对象，需要用到类字节，进行实体类的转换
 * 使用DisposeDataListener回调数据
 */
public class DisposeDataHandle {
    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;

    public DisposeDataHandle(DisposeDataListener listener) {
        mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class<?> classzz) {
        mListener = listener;
        mClass = classzz;
    }

}
