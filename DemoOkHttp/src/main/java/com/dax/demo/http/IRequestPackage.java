package com.dax.demo.http;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/17.
 */
public interface IRequestPackage {

    /**
     * GET方式
     */
    public final static int TYPE_GET = 1;

    /**
     * POST方式
     */
    public final static int TYPE_POST = 2;

    /**
     * 获取header设置信息
     *
     * @return
     */
    public HashMap<String, String> getRequestHeaders();

    /**
     * 获取GET方法参数
     *
     * @return
     */
    public String getGetRequestParams();

    /**
     * 获取Post请求Entity<br/>
     * 根据不用的参数，需要将数据转换成对应HttpEntity子类
     *
     * @return
     */
    public byte[] getPostRequestEntity();

    /**
     * 获取请求链接
     *
     * @return
     */
    public String getUrl();

    /**
     * 请求类型
     *
     * @return
     */
    public int getRequestType();

    /**
     * 获取请求参数设置，如超时时间
     *
     * @return
     */
    public HashMap<String, Object> getSettings();
}
