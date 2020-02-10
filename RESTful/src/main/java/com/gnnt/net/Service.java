package com.gnnt.net;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
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
            , @QueryMap Map<String, String> params, @HeaderMap Map<String,String> headerMap);

    @POST("{end}")
    Call<ResponseBody> postParams(@Path(value = "end", encoded = true) String end
            , @Body RequestBody requestVO, @HeaderMap Map<String,String> headerMap);

    @POST("{end}")
    Call<ResponseBody> postObject(@Path(value = "end", encoded = true) String end
            , @Body RequestBody requestVO, @HeaderMap Map<String,String> headerMap);

    @PUT("{end}")
    Call<ResponseBody> putParams(@Path(value = "end", encoded = true) String end
            , @Body RequestBody requestVO, @HeaderMap Map<String,String> headerMap);

    @PUT("{end}")
    Call<ResponseBody> putObject(@Path(value = "end", encoded = true) String end
            , @Body RequestBody requestVO, @HeaderMap Map<String,String> headerMap);

    @DELETE("{end}")
    Call<ResponseBody> deleteParams(@Path(value = "end", encoded = true) String end
            , @QueryMap Map<String, String> params, @HeaderMap Map<String,String> headerMap);
}
