package com.gnnt.net;

/********************************************************************************
 *Callback 2020/2/8
 *<p>
 *类说明:获取请求数据的回调抽象类<br/>
 * 在请求时 userid  token也在这里传入
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public abstract class Callback <T> {

//    public String userID = "";
//    public String token = "";

//    public Callback(String userID, String token) {
//        this.userID = userID;
//        this.token = token;
//    }

    public Callback() {}

    /**
     * 数据获取回调
     * @param result 标识成功1  失败  网络异常
     * @param failMessage 失败message
     * @param responseInfo 返回包信息 ，有可能为null
     */
    protected abstract void response(int result, String failMessage, T responseInfo);

}
