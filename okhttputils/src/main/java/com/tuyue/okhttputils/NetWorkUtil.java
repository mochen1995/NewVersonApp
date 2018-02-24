package com.tuyue.okhttputils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 项目名：SellApp
 * 包名：com.tuyue.sellapp.utils
 * 创建者：mmcc
 * 创建时间：2017/4/5 11:10
 * 描述：判断网络状态的工具类
 */


public class NetWorkUtil {

    public enum NetStatus{
        MOBILE,WIFI,NONE
    }

    private Context context;

    public NetWorkUtil(Context context) {
        this.context = context;
    }

    /**
     * 网络是否连接
     *
     * @return
     */
    public boolean isNetConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    public NetStatus getNetStatus() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        int type = networkInfo.getType();
        if (type==ConnectivityManager.TYPE_MOBILE)
            return NetStatus.MOBILE;
        else if (type==ConnectivityManager.TYPE_WIFI)
        {
            return NetStatus.WIFI;
        }
        return NetStatus.NONE;
    }




}
