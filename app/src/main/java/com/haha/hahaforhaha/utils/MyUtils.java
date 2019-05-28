package com.haha.hahaforhaha.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Vibrator;

import androidx.annotation.RequiresPermission;

import com.haha.hahaforhaha.MyApplication;

import java.net.NetworkInterface;
import java.net.SocketException;

import static android.Manifest.permission.VIBRATE;

public class MyUtils {
    /***手机震动**/
    @RequiresPermission(VIBRATE)
    public static void errVIBRATE(long longSec) {
        Vibrator vibrator = (Vibrator) MyApplication.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(longSec * 1000);
        }
    }

    /***获取当前app 版本号*/
    public static String getAppVersion() {
        try {
            PackageManager manager = MyApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*** 字符 转 xml <?>value </?> */
    public static String getHisXmlPara(String strFormat,String strVal ) {
        StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(strFormat);
        sb.append(">");
        sb.append(strVal);
        sb.append("</");
        sb.append(strFormat);
        sb.append(">");
        return sb.toString();
    }

    /***获取mac地址*/
    public static String GetMacAddress(){
        String strDeviceMac="";
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            strDeviceMac = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return strDeviceMac;
    }



}
