package com.gnnt.net.callbacks;

import com.google.gson.JsonObject;

import java.util.Map;

/********************************************************************************
 *CallbackJsonObject 2020/2/8
 *<p>
 *类说明:该类把服务端返回的数据转为map<br/>
 * 如果服务端返回的数据都使用JsonObject来取值，则可以使用该类，就是简单的固定了泛型为JsonObject
 * 建议子类根据实际重写
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public class CallbackJsonObject extends Callback<JsonObject>{


    @Override
    public void successResponse(JsonObject responseInfo) {

    }

    @Override
    public void failResponse(long retcode, String failMessage) {

    }
}
