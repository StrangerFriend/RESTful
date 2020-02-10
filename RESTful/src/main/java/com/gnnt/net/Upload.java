package com.gnnt.net;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/********************************************************************************
 *Upload 2020/2/8
 *<p>
 *类说明:图片上传<br/>
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public class Upload {

    /**
     * 上传图片
     * @param url 请求完整地址
     * @param userId 用户id
     * @param token token
     * @param callback 回调接口
     */
    public static void uploadLogo(String url, String userId, String token,Callback callback){
//        String resultPath = BitmapUtil.compressImage(getApplication(),path);
        String resultPath = url;
        File file = new File(resultPath);
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        MediaType FORM_CONTENT_TYPE = MediaType.parse("text/x-markdown; charset=UTF-8");//字符编码为GBK
//        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"U\""), RequestBody.create(FORM_CONTENT_TYPE, userId))
//                .addPart(Headers.of("Content-Disposition", "form-data; name=\"SI\""), RequestBody.create(FORM_CONTENT_TYPE, token));
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody requestBody = builder.build();
        final Request request = new Request.Builder()
                .addHeader("UserID",userId)
                .addHeader("Token",token)
                .url(url).post(requestBody).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                uploadErrorLiveData.postValue("上传失败");
                callback.fail("上传失败");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String json = response.body().string();
//                    uploadSuccessLiveData.postValue(json);
                    callback.success();
                }else {
                    callback.fail("上传失败");
                }
            }
        });
    }

    public interface Callback {
        void success();
        void fail(String message);
    }

}
