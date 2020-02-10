package com.gnnt.net.util;

import android.app.Application;
import android.text.TextUtils;

import com.gnnt.net.R;
import com.gnnt.net.Service;
import com.gnnt.net.vo.ErrorResponseVO;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/********************************************************************************
 *HttpRequest 2020/2/8
 *<p>
 *类说明:网络通讯库 ，这个库需要以对象的形式调用，因为要支持多个baseurl<br/>
 * 使用前需要传入application
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public class HttpRequest {

    public static Application application;

    private Retrofit mRetrofit;

    private String mBaseUrl = "";

    private String mToken = "";

    public HttpRequest(String baseUrl){
        //如果url  结尾没有 / 则添加
        if(!baseUrl.endsWith("/")){
            baseUrl = baseUrl + "/";
        }

        mBaseUrl = baseUrl;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
//                .client(httpClient.build())
                .build();
    }

    /**
     * 如果endurl已 / 开头则去掉
     */
    private String doEndUrl(String endurl){
        if(endurl.startsWith("/")){
            endurl = endurl.substring(1);
        }
        return endurl;
    }

    /**
     * get请求
     * @param endUrl
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void get(String endUrl, Map<String,String> params
            , final com.gnnt.net.callbacks.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        if(params == null){
            params = new HashMap<>();
        }
        callback.beforeRequest();
        Service service = mRetrofit.create(Service.class);
        Call<ResponseBody> response = service.getParams(endUrl, params, callback.headerMapParams());
        doResponse(response,callback);

    }

    /**
     * post请求 json形式
     * @param endUrl
     * @param paramMapOrObject 可以传入map对象 vo对象 内部会自动转成json字符串
     * @param callback
     * @param <T>
     */
    public <T> void post(String endUrl, Object paramMapOrObject
            , final com.gnnt.net.callbacks.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        callback.beforeRequest();
        Service service = mRetrofit.create(Service.class);
        Gson gson = new Gson();
        String gsonStr = gson.toJson(paramMapOrObject);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),gsonStr);
        Call<ResponseBody> response = service.postObject(endUrl, body, callback.headerMapParams());
        doResponse(response,callback);
    }

    /**
     * post请求 键值对 表单形式
     * @param endUrl
     * @param paramMap
     * @param callback
     * @param <T>
     *
//     *     JSONObject
     */
    public <T> void paramsPost(String endUrl, Map<String,String> paramMap
            , final com.gnnt.net.callbacks.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        callback.beforeRequest();
        Service service = mRetrofit.create(Service.class);
        FormBody.Builder builder = new FormBody.Builder();//application/x-www-form-urlencoded 内部已经设置
        for (String key : paramMap.keySet()) {
            builder.add(key,paramMap.get(key));
        }
        Call<ResponseBody> response = service.postParams(endUrl, builder.build(), callback.headerMapParams());
        doResponse(response,callback);
    }


    /**
     * put请求 json形式
     * @param endUrl
     * @param paramMapOrObject  可以传入map对象 vo对象 内部会自动转成json字符串
     * @param callback
     * @param <T>
     */
    public <T> void put(String endUrl, Object paramMapOrObject
            , final com.gnnt.net.callbacks.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        callback.beforeRequest();
        Service service = mRetrofit.create(Service.class);
        Gson gson = new Gson();
        String gsonStr = gson.toJson(paramMapOrObject);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),gsonStr);
        Call<ResponseBody> response = service.putObject(endUrl, body, callback.headerMapParams());
        doResponse(response,callback);
    }

    /**
     * put请求 键值对 表单形式
     * @param endUrl
     * @param paramMap
     * @param callback
     * @param <T>
     */
    public <T> void paramsPut(String endUrl, Map<String,String> paramMap
            , final com.gnnt.net.callbacks.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        callback.beforeRequest();
        Service service = mRetrofit.create(Service.class);
        FormBody.Builder builder = new FormBody.Builder();//application/x-www-form-urlencoded 内部已经设置
        for (String key : paramMap.keySet()) {
            builder.add(key,paramMap.get(key));
        }
        Call<ResponseBody> response = service.putParams(endUrl,builder.build(), callback.headerMapParams());
        doResponse(response,callback);
    }

    /**
     * delete请求
     * @param endUrl
     * @param params
     * @param classOf
     * @param callback
     * @param <T>
     */
    public <T> void delete(String endUrl, Map<String,String> params, final Class<T> classOf
            , final com.gnnt.net.callbacks.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        if(params == null){
            params = new HashMap<>();
        }
        callback.beforeRequest();
        Service service = mRetrofit.create(Service.class);
        Call<ResponseBody> response = service.deleteParams(endUrl, params, callback.headerMapParams());
        doResponse(response,callback);

    }

    /**
     * 处理response
     * @param response
     * @param callback
     * @param <T>
     */
    private <T> void doResponse(Call<ResponseBody> response, final com.gnnt.net.callbacks.Callback<T> callback){
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful()){
                        //成功
                        String bodyStr = getResponseStr(response);//成功信息
                        if(TextUtils.isEmpty(bodyStr)){
                            callback.successResponse(null);
                        }else {
                            //把服务端返回的报文进行映射
                            if(callback.getClz() == null || callback.getClz() == String.class){
                                //不需要映射 直接返回String
                                callback.successResponse((T) bodyStr);
                            }else {
                                Gson gson = new Gson();
                                T t = gson.fromJson(bodyStr, callback.getClz());
                                callback.successResponse(t);
                            }
                        }
                    }else {
                        //失败
                        //{"code":500,"msg":"系统内部异常!","args":null}
                        String bodyStr = response.errorBody().string();//失败信息
                        Gson gson = new Gson();
                        ErrorResponseVO errorResponseVO = gson.fromJson(bodyStr, ErrorResponseVO.class);
                        if(callback.afterResponseError(errorResponseVO)){
                            return;
                        }
                        callback.failResponse(errorResponseVO.getCode(),errorResponseVO.getMsg());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                callback.failResponse(-1,application.getString(R.string.net_error));
            }
        });
    }

    private String getResponseStr(Response<ResponseBody> response){
        try {
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
