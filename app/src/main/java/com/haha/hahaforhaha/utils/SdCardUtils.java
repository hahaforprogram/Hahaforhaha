package com.haha.hahaforhaha.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by hah on 2018/4/13.
 */

public class SdCardUtils {

    public SdCardUtils() {
    }

    public static String getState() {
        return Environment.getExternalStorageState();
    }

    /***SD卡是否可用@return 只有当SD卡已经安装并且准备好了才返回true*/
    public static boolean isAvailable() {
        return getState().equals(Environment.MEDIA_MOUNTED);
    }

    /*** 获取SD卡的根目录 @return null：不存在SD卡*/
    public static String getRootDirectory() {
        return isAvailable() ? Environment.getExternalStorageDirectory().toString() : "";
    }

    /*** 获取SD卡的根路径@return null：不存在SD卡*/
    public String getSDPath(){
        File sdDir = null;
        if(isAvailable())
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString().concat("/");
    }
}
