package com.gnnt.net0208;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gnnt.net.Callback;
import com.gnnt.net.HttpCommunicate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private HttpCommunicate httpCommunicate;

    private String mUserId = "";
    private String mToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpCommunicate.application = getApplication();
        httpCommunicate = new HttpCommunicate("http://124.207.182.189:8091");
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
        httpCommunicate.postParams("/customer/register/register", param, null
                , new Callback() {
                    @Override
                    public void response(int result, String failMessage, Object o) {
                        if(result ==1 ){
                            Toast.makeText(getApplication(),"注册成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplication(), failMessage,Toast.LENGTH_SHORT).show();
                        }
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
        httpCommunicate.postParams("customer/login/loginByPwd", params, HashMap.class
                , new Callback<HashMap>() {
                    @Override
                    public void response(int result, String failMessage, HashMap infoMap) {
                        if(result == 1){
                            Toast.makeText(getApplication(),"登录成功 token = "+infoMap.get("token"),Toast.LENGTH_SHORT).show();
//                            mUserId = new BigDecimal("" +infoMap.get("userId"))
                            Double doubleUserId = (Double) infoMap.get("userId");
                            mUserId = "" + doubleUserId.intValue();
                            mToken = (String) infoMap.get("token");
                            httpCommunicate.setUserIdAndToken(mUserId,mToken);
                        }else {
                            Toast.makeText(getApplication(), failMessage,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 获取用户信息
     * @param view
     */
    public void getUserInfoClick(View view){
        httpCommunicate.getParams("/customer/common/info",null , String.class
                , new Callback<String>() {
                    @Override
                    public void response(int result, String failMessage, String info) {
                        if(result ==1 ){
                            Toast.makeText(getApplication(),info,Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplication(), failMessage,Toast.LENGTH_SHORT).show();
                        }
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
        httpCommunicate.putParams("/customer/common/updateNickName", paramsMap, String.class
                , new Callback<String>() {
                    @Override
                    protected void response(int result, String failMessage, String responseInfo) {
                        if(result == 1){
                            Toast.makeText(getApplicationContext(),"修改昵称成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), failMessage,Toast.LENGTH_SHORT).show();
                        }
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
        httpCommunicate.postParams("/customer/cancellation/apply", paramsMap, String.class
                , new Callback<String>() {
                    @Override
                    protected void response(int result, String failMessage, String responseInfo) {
                        if(result == 1){
                            Toast.makeText(getApplicationContext(),"注销成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), failMessage,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
