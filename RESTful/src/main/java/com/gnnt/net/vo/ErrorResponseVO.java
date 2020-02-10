package com.gnnt.net.vo;

/********************************************************************************
 *ErrorResponseVO 2020/2/8
 *<p>
 *错误信息类，这个类不会对使用
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public class ErrorResponseVO {

    private String code;

    private String msg;

    private String args;


    public long getCode() {
        try {
            return Long.parseLong(code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public String getMsg() {
        if(msg == null){
            return "";
        }
        return msg;
    }

    public String getArgs() {
        return args;
    }
}
