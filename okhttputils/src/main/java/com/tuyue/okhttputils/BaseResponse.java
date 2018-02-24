package com.tuyue.okhttputils;

/**
 * 项目名：Newapp
 * 包名：com.tuyue.rxjava2.net
 * 创建者：mmcc
 * 创建时间：2017/7/18 11:30
 * 描述：TODO
 */


public class BaseResponse<T>{
    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
