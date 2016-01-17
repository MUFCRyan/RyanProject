package com.miss.qianghongbao.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;

public class ServiceStateUtil {

    /**
     * 判断一个服务是否在运行
     * @param context 
     * @param serviceClass 服务的 class
     * @return
     */
    public static boolean isServiceRunning(Context context, Class<? extends Service> serviceClass) {
        // ActivityManager 相当于windows的任务管理器
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> runningServices = activityManager.getRunningServices(1000);
        for (RunningServiceInfo runningServiceInfo : runningServices) {
            ComponentName serviceName = runningServiceInfo.service;
            if(serviceName.getClassName().equals(serviceClass.getName())) {
                return true;
            }
        }
        return false;
    }
}
