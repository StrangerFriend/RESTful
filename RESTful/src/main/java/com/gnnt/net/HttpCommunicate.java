package com.gnnt.net;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/********************************************************************************
 *HttpCommunicate 2020/2/8
 *<p>
 *类说明:网络通讯库 ，这个库需要以对象的形式调用，因为要支持多个baseurl<br/>
 * 使用前需要传入application
 * UserId  Token 放在header中
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public class HttpCommunicate {

    public static Application application;

    private Retrofit mRetrofit;

    /**
     * 用户id  放在header中
     */
    private String mUserId = "";
    /**
     * tokend 放在header中
     */
    private String mToken = "";

    public HttpCommunicate(String baseUrl){
        //如果url  结尾没有 / 则添加
        if(!baseUrl.endsWith("/")){
            baseUrl = baseUrl + "/";
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();
    }

    public void setUserIdAndToken(String userId , String token){
        mUserId = userId;
        mToken = token;
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
     * @param classOf
     * @param callback
     * @param <T>
     */
    public <T> void getParams(String endUrl, Map<String,String> params, final Class<T> classOf
            , final com.gnnt.net.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        if(params == null){
            params = new HashMap<>();
        }
        Service service = mRetrofit.create(Service.class);
        Call<ResponseBody> response = service.getParams(endUrl,mUserId,mToken, params);
        doResponse(response,classOf,callback);

    }

    /**
     * post请求 json形式
     * @param endUrl
     * @param paramObject
     * @param classOf
     * @param callback
     * @param <T>
     */
    public <T> void postObject(String endUrl, Object paramObject, final Class<T> classOf
            , final com.gnnt.net.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        Service service = mRetrofit.create(Service.class);
        Gson gson = new Gson();
        String gsonStr = gson.toJson(paramObject);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),gsonStr);
        Call<ResponseBody> response = service.postObject(endUrl,mUserId,mToken, body);
        doResponse(response,classOf,callback);
    }

    /**
     * post请求 键值对 表单形式
     * @param endUrl
     * @param paramMap
     * @param classOf
     * @param callback
     * @param <T>
     */
    public <T> void postParams(String endUrl, Map<String,String> paramMap, final Class<T> classOf
            , final com.gnnt.net.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        Service service = mRetrofit.create(Service.class);
        FormBody.Builder builder = new FormBody.Builder();//application/x-www-form-urlencoded 内部已经设置
        for (String key : paramMap.keySet()) {
            builder.add(key,paramMap.get(key));
        }
        Call<ResponseBody> response = service.postParams(endUrl,mUserId,mToken, builder.build());
        doResponse(response,classOf,callback);
    }


    /**
     * put请求 json形式
     * @param endUrl
     * @param paramObject
     * @param classOf
     * @param callback
     * @param <T>
     */
    public <T> void putObject(String endUrl, Object paramObject, final Class<T> classOf
            , final com.gnnt.net.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        Service service = mRetrofit.create(Service.class);
        Gson gson = new Gson();
        String gsonStr = gson.toJson(paramObject);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),gsonStr);
        Call<ResponseBody> response = service.putObject(endUrl,mUserId,mToken, body);
        doResponse(response,classOf,callback);
    }

    /**
     * put请求 键值对 表单形式
     * @param endUrl
     * @param paramMap
     * @param classOf
     * @param callback
     * @param <T>
     */
    public <T> void putParams(String endUrl, Map<String,String> paramMap, final Class<T> classOf
            , final com.gnnt.net.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        Service service = mRetrofit.create(Service.class);
        FormBody.Builder builder = new FormBody.Builder();//application/x-www-form-urlencoded 内部已经设置
        for (String key : paramMap.keySet()) {
            builder.add(key,paramMap.get(key));
        }
        Call<ResponseBody> response = service.putParams(endUrl, mUserId,mToken,builder.build());
        doResponse(response,classOf,callback);
    }

    /**
     * delete请求
     * @param endUrl
     * @param params
     * @param classOf
     * @param callback
     * @param <T>
     */
    public <T> void deleteParams(String endUrl, Map<String,String> params, final Class<T> classOf
            , final com.gnnt.net.Callback<T> callback){
        endUrl = doEndUrl(endUrl);
        if(params == null){
            params = new HashMap<>();
        }
        Service service = mRetrofit.create(Service.class);
        Call<ResponseBody> response = service.deleteParams(endUrl,mUserId,mToken, params);
        doResponse(response,classOf,callback);

    }

    /**
     * 处理response
     * @param response
     * @param classOf
     * @param callback
     * @param <T>
     */
    private <T> void doResponse(Call<ResponseBody> response, final Class<T> classOf, final com.gnnt.net.Callback callback){
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful()){
                        //成功
                        String bodyStr = getResponseStr(response);
                        if(TextUtils.isEmpty(bodyStr)){
                            callback.response(1,"",null);
                        }else {
                            //把服务端返回的报文进行映射
                            if(classOf == null || classOf == String.class){
                                //不需要映射 直接返回String
                                callback.response(1,"",bodyStr);
                            }else {
                                Gson gson = new Gson();
                                T t = gson.fromJson(bodyStr, classOf);
                                callback.response(1,"",t);
                            }
                        }
                    }else {
                        //失败
                        //{"code":500,"msg":"系统内部异常!","args":null}
                        String bodyStr = response.errorBody().string();
                        Gson gson = new Gson();
                        Map<String,String> mapError = gson.fromJson(bodyStr, HashMap.class);
                        if(mapError == null){
                            mapError = new HashMap<>();
                        }
//                        String code = mapError.get("code");
                        String msg = mapError.get("msg");
                        callback.response(-1,msg != null ? msg : "",null);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                callback.response(-1,application.getString(R.string.net_error),null);
            }
        });
    }

    private String getResponseStr(Response<ResponseBody> response){
        try {
            return response.body().string();
        }catch (Exception e){

        }
        return "";
    }


}
