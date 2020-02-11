package com.gnnt.net0208;

import com.gnnt.net.callbacks.Callback;
import com.gnnt.net.vo.ErrorResponseVO;

import java.util.Map;

/**
 * 自定义callback
 * 通讯库demo 定制callback
 * @param <T>
 */
public abstract class CustomCallback<T> extends Callback<T> {

    @Override
    public Map<String, String> headerMapParams() {
        Map<String, String> map = super.headerMapParams();
        map.put("UserID", MainActivity.mUserId);
        map.put("Token", MainActivity.mToken);
        return map;
    }

    @Override
    public abstract void successResponse(T responseInfo) ;

    @Override
    public abstract void failResponse(long retcode, String failMessage) ;


}
