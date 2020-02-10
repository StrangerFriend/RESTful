package com.gnnt.net0208;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gnnt.net.util.HttpRequest;
import com.gnnt.net.callbacks.Callback;

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

        /*httprequest.paramsPost("customer/login/loginByPwd", params
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
                });*/

        httprequest.paramsPost("customer/login/loginByPwd", params
                , new Callback<Object>() {

                    @Override
                    public void successResponse(Object responseInfo) {
                        Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failResponse(long retcode, String failMessage) {

                    }
                });

    }

    /**
     * 获取用户信息
     * @param view
     */
    public void getUserInfoClick(View view){
        httprequest.get("/customer/common/info",null
                , new CustomCallback<String>() {
                    @Override
                    public void successResponse(String info) {
                        Toast.makeText(getApplication(),info,Toast.LENGTH_SHORT).show();
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

}
