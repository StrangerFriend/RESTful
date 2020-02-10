package com.gnnt.net.callbacks;

import java.util.Map;

/********************************************************************************
 *CallbackMap 2020/2/8
 *<p>
 *类说明:该类把服务端返回的数据转为map<br/>
 * 项目使用时需根据具体的需求对该类进行重写，一般不会直接使用该类
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public class CallbackMap extends Callback<Map<String,String>>{


    @Override
    public void successResponse(Map<String, String> responseInfo) {

    }

    @Override
    public void failResponse(long retcode, String failMessage) {

    }
}
