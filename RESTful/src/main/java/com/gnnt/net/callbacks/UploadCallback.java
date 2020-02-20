package com.gnnt.net.callbacks;


/********************************************************************************
 *Callback 2020/2/8
 *<p>
 *类说明:图片上传回调<br/>
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public interface UploadCallback {

    void success();
    void fail(String message);

}
