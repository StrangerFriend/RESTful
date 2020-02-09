package com.gnnt.net;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/********************************************************************************
 *Service 2020/2/8
 *<p>
 *类说明:<br/>
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public interface Service {


//    @Headers({
//            "UserID: application/vnd.github.v3.full+json",
//            "Token: Retrofit-Sample-App"
//    })
    @GET("{end}")
    Call<ResponseBody> getParams(@Path(value = "end", encoded = true) String end
            ,@Header("UserID") String userId,@Header("Token") String Token
            ,@QueryMap Map<String, String> params);

    @POST("{end}")
    Call<ResponseBody> postParams(@Path(value = "end", encoded = true) String end
            ,@Header("UserID") String userId,@Header("Token") String Token
            , @Body RequestBody requestVO);

    @POST("{end}")
    Call<ResponseBody> postObject(@Path(value = "end", encoded = true) String end
            ,@Header("UserID") String userId,@Header("Token") String Token
            , @Body RequestBody requestVO);

    @PUT("{end}")
    Call<ResponseBody> putParams(@Path(value = "end", encoded = true) String end
            ,@Header("UserID") String userId,@Header("Token") String Token
            , @Body RequestBody requestVO);

    @PUT("{end}")
    Call<ResponseBody> putObject(@Path(value = "end", encoded = true) String end
            ,@Header("UserID") String userId,@Header("Token") String Token
            , @Body RequestBody requestVO);

    @GET("{end}")
    Call<ResponseBody> deleteParams(@Path(value = "end", encoded = true) String end
            ,@Header("UserID") String userId,@Header("Token") String Token
            , @QueryMap Map<String, String> params);
}
