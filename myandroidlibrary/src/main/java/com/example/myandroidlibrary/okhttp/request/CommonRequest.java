package com.example.myandroidlibrary.okhttp.request;

/**
 * Created by weinp on 2017/5/3.
 */

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * @author weinp
 * @function 封装OkHttp的Request
 */
public class CommonRequest {

    public static Request createPostRequest(String url , RequestParams params){
        return createPostRequest(url , params , null);
    }

    /**
     *
     * @param url
     * @param params
     * @param headerParams
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params, RequestParams headerParams) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> map : params.urlParams.entrySet()) {
                formBuilder.add(map.getKey(), map.getValue());
            }
        }

        Headers.Builder mHeadersBuilder = new Headers.Builder();
        if (headerParams != null) {
            for (Map.Entry<String, String> map : headerParams.urlParams.entrySet()
                    ) {
                mHeadersBuilder.add(map.getKey(),map.getValue());
            }
        }

        FormBody mFormBody = formBuilder.build();
        Headers mHeaders = mHeadersBuilder.build();

        Request request = new Request.Builder().url(url).post(mFormBody).headers(mHeaders).build();
        return request;
    }


    public static Request onCreateGetRequest(String url , RequestParams params){
        return onCreateGetRequest(url , params , null);
    }
    /**
     * 生成Get请求
     * @param url
     * @param params
     * @param headerParams
     * @return
     */
    public static Request onCreateGetRequest(String url , RequestParams params , RequestParams headerParams){
        StringBuilder stringBuilder = new StringBuilder(url).append("?");
        if(params!=null)
        {
            for (Map.Entry<String , String >map:
                 params.urlParams.entrySet()) {
                stringBuilder.append(map.getKey()+"="+map.getValue()+"&&");
            }
        }

        Headers.Builder mHeadersBuilder  = new Headers.Builder();
        if(headerParams!=null){
            for (Map.Entry<String , String > map  :headerParams.urlParams.entrySet()
                 ) {
                mHeadersBuilder.add(map.getKey() , map.getValue());
            }
        }

        Headers mHeaders = mHeadersBuilder.build();

        Request request = new Request.Builder().url(stringBuilder.substring(0,stringBuilder.length()-1).toString())
                .get()
                .headers(mHeaders)
                .build();
        return request;
    }

}
