package com.gnnt.net0208;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.gnnt.net.callbacks.UploadCallback;
import com.gnnt.net.util.HttpFileRequest;
import com.gnnt.net.util.HttpRequest;
import com.gnnt.net.callbacks.Callback;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private HttpRequest httprequest;

    public static String mUserId = "";
    public static String mToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httprequest.application = getApplication();
        httprequest = new HttpRequest("http://124.207.182.189:8091");

        timeZone2GMT("2020-2-11 4:13:27");
        timeGMT2Zone("2020-2-11 19:24:45");
    }

    private final long hour = 360000;
    private void date(){
      /*  Date date = new Date();
        TimeZone timeZone = TimeZone.getDefault();
        String aaaa = "fsfs";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM ddHH:mm:ss 'GMT' yyyy", Locale.US);*/

        Calendar calendar = Calendar.getInstance();
        calendar.getTimeZone().getID();

        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);//标准时间偏移量

        //取得与GMT之间的时间偏移量，例如罗马属于东1区，则时间偏移量为3600000毫秒
//        int dstOffset = calendar.get(Calendar.DST_OFFSET);//夏令时时间偏移量


        Date date = new Date();
        long l2 = date.getTime() - zoneOffset;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date(l2));
        String aaaa = "fsfs";
    }

    /**
     * 时区时间转GMT时间
     * @return
     */
    private String getCurrentGMT(){
        Calendar calendar = Calendar.getInstance();
        //取得与GMT之间的时间偏移量，例如罗马属于东1区，则时间偏移量为3600000毫秒
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);//标准时间偏移量
        Date date = new Date();//获取当前时间
        long gmtLong = date.getTime() - zoneOffset;//获取gmt时间time值
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date(gmtLong));
        return format;
    }

    /**
     * 时区时间转GMT时间
     * @return
     */
    private String timeZone2GMT(String zoneTime){
        try {
            Calendar calendar = Calendar.getInstance();
            //取得与GMT之间的时间偏移量，例如罗马属于东1区，则时间偏移量为3600000毫秒
            int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);//标准时间偏移量

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date zoneDate = sdf.parse(zoneTime);
            long gmtLong = zoneDate.getTime() - zoneOffset;//获取gmt时间值
            String format = sdf.format(new Date(gmtLong));
            return format;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * GMT时间转时区时间
     * @return
     */
    private String timeGMT2Zone(String GmtTime){
        try{
            Calendar calendar = Calendar.getInstance();
            //取得与GMT之间的时间偏移量，例如罗马属于东1区，则时间偏移量为3600000毫秒
            int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);//标准时间偏移量

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long GmtTimeLong = sdf.parse(GmtTime).getTime();
            long zoneTimeLong = GmtTimeLong + zoneOffset;//获取时区对应的时间值
            String format = sdf.format(new Date(zoneTimeLong));
            return format;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 注册
     * @param view
     */
    public void registerclick(View view){
        Map<String,String> param = new HashMap<>();
        param.put("mobilePhone","13088889998");
        param.put("password","1234566788");
        param.put("verifyCode","32323");
        httprequest.paramsPost("/customer/register/register", param
                , new Callback<Object>() {
                    @Override
                    public void successResponse(Object o) {
                        Toast.makeText(getApplication(),"注册成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failResponse(long retcode, String failMessage) {
                        Toast.makeText(getApplication(), failMessage,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 登录
     * @param view
     */
    public void loginClick(View view){
        Map<String,String> params = new HashMap<>();
        params.put("username","13088889998");
        params.put("password","1234566788");

        httprequest.paramsPost("customer/login/loginByPwd", params
                , new Callback<HashMap>() {
                    @Override
                    public void successResponse(HashMap infoMap) {
                        Toast.makeText(getApplication(),"登录成功 token = "+infoMap.get("token"),Toast.LENGTH_SHORT).show();
//                            mUserId = new BigDecimal("" +infoMap.get("userId"))
                        Double doubleUserId = (Double) infoMap.get("userId");
                        mUserId = "" + doubleUserId.intValue();
                        mToken = (String) infoMap.get("token");
                    }

                    @Override
                    public void failResponse(long retcode, String failMessage) {
                        Toast.makeText(getApplication(), failMessage,Toast.LENGTH_SHORT).show();
                    }
                });

        /*httprequest.paramsPost("customer/login/loginByPwd", params
                , new Callback<Object>() {

                    @Override
                    public void successResponse(Object responseInfo) {
                        Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failResponse(long retcode, String failMessage) {

                    }
                });*/

    }

    /**
     * 获取用户信息
     * @param view
     */
    public void getUserInfoClick(View view){
        /*httprequest.get("/customer/common/info",null
                , new CustomCallback<String>() {
                    @Override
                    public void successResponse(String info) {
                        Toast.makeText(getApplication(),info,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failResponse(long retcode, String failMessage) {
                        Toast.makeText(getApplication(), failMessage,Toast.LENGTH_SHORT).show();
                    }
                });*/

        httprequest.get("/customer/common/info",null
                , new CustomCallback<JsonObject>() {
                    @Override
                    public void successResponse(JsonObject info) {
                        Toast.makeText(getApplication(),info.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failResponse(long retcode, String failMessage) {
                        Toast.makeText(getApplication(), failMessage,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 修改昵称
     * @param view
     */
    public void changeNickNameClick(View view){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("nickName","张三");
        httprequest.paramsPut("/customer/common/updateNickName", paramsMap
                , new CustomCallback<String>() {
                    @Override
                    public void successResponse(String responseInfo) {
                        //数据处理 --
                        Toast.makeText(getApplicationContext(),"修改昵称成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failResponse(long retcode, String failMessage) {
                        Toast.makeText(getApplicationContext(), failMessage,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 注销
     * @param view
     */
    public void destroyUserClick(View view){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("phone","13088889998");
        httprequest.paramsPost("/customer/cancellation/apply", paramsMap
                , new CustomCallback<String>() {
                    @Override
                    public void successResponse( String responseInfo) {
                        Toast.makeText(getApplicationContext(),"注销成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failResponse(long retcode, String failMessage) {
                        Toast.makeText(getApplicationContext(), failMessage,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 上传图片
     * @param view
     */
    public void uploadClick(View view){
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, 2020);

        /*try{
            String aaa = getAssets().list("")[0];
            HttpFileRequest.upload("http://124.207.182.189:8091/customer/authentication/uploadApplyPhoto"
                    , aaa , mUserId, mToken, new HttpFileRequest.Callback() {
                        @Override
                        public void success() {

                        }

                        @Override
                        public void fail(String message) {

                        }
                    });


        }catch (Exception e){
            e.printStackTrace();
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2020 && resultCode == Activity.RESULT_OK){
            Uri data1 = data.getData();
            String path = GetPathFromUri.getInstance().getPath(this,data1);
//            path = BitmapUtil.compressImage(getApplication(),path);
            HttpFileRequest.upload("http://124.207.182.189:8091/customer/authentication/uploadApplyPhoto"
                    , path , mUserId, mToken, "",new UploadCallback() {
                        @Override
                        public void success() {
                            Toast.makeText(MainActivity.this,"上传图片成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void fail(String message) {

                        }
                    });

        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
