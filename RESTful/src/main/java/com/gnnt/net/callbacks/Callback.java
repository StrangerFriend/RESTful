package com.gnnt.net.callbacks;

import com.gnnt.net.vo.ErrorResponseVO;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/********************************************************************************
 *Callback 2020/2/8
 *<p>
 *类说明:获取请求数据的回调抽象类<br/>
 * +++++ 不建议直接使用该类 项目中应该根据需求使用一个重写的子类 +++
 * 该类是网路库扩展的核心
 * 项目使用时需根据具体的需求对该类进行重写
 * T 服务端返回的数据的映射对象，内部自动转换，view调用处不用再手动转换
 *<br/>
 *</p>
 *Copyright2019 by GNNT Company. All Rights Reserved.
 *@author:jiacl
 *********************************************************************************/
public abstract class Callback <T> {

    /**
     * 泛型对象
     */
    private Class<T> clz;

    public Callback() {
        //获取泛型对象
        ParameterizedType pt = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.clz = (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * 添加header 参数
     * @return
     */
    public Map<String,String> headerMapParams(){
        return new HashMap();
    }

    /**
     * 请求前处理 --这个针对每次请求的
     */
    public void beforeRequest(){}

    /**
     * 获得请求后失败的统一处理，比如失效退出--针对每次请求的
     */
    public void afterResponseError(ErrorResponseVO errorResponseVO){}

    /**
     * 成功数据获取回调
     * @param responseInfo 返回包信息 ，有可能为null
     */
    public abstract void successResponse( T responseInfo);

    /**
     * 失败数据获取
     * @param retcode 返回码
     * @param failMessage 失败信息
     */
    public abstract void failResponse(long retcode, String failMessage);

    /**
     * 返回class泛型对象
     * @return
     */
    public final Class<T> getClz() {
        return clz;
    }
}
