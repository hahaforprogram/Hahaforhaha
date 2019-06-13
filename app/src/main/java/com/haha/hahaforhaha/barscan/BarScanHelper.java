package com.haha.hahaforhaha.barscan;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.haha.hahaforhaha.MyApplication;
import com.haha.hahaforhaha.contraint.AppConfig;
import com.haha.hahaforhaha.contraint.AppCookie;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class BarScanHelper {
    private static final String TAG = "BarScanHelper";
    private static BarScanHelper instance;

    /***单例 BarScanHelper  ***/
    public static BarScanHelper getInstance(){
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null) {
                    instance = new BarScanHelper();
                }
            }
        }
        return instance;
    }

    @SuppressLint("CheckResult")
    public Observable<Boolean> barScan(final String strBarcode) {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(final Observer<? super Boolean> observer) {
                String strBarInfo[] = strBarcode.split("#");
                Log.d(TAG, "barScanProcess: begin.....");
                //判断扫描的是否为空
                if (TextUtils.isEmpty(strBarcode.trim())) {
                    observer.onError(new Throwable("扫描二维码Null异常,请检查后再试！"));
                    Log.d(TAG, "subscribeActual: 1----------");
                    observer.onComplete();
                    Log.d(TAG, "subscribeActual: 1**********");
                    return;
                }

                //如果有设置当期扫描标识
                if (!TextUtils.isEmpty(AppCookie.barscantype.getCurrentBarscantype()) && !strBarInfo[0].equals(AppCookie.barscantype.getCurrentBarscantype())) {
                    //扫描的二维码类型不与系统设定的相符 则提示 报错
                    if ("LOGIN".equals(AppCookie.barscantype.getCurrentBarscantype()) && strBarInfo[0].equals("NURSE")) {
                        /**第一次登陆LOGIN只验证当前app数据库是否存在此用户*/
                        Boolean mboolean = true;// (Boolean) LocalDbDao.appUserLogin(strBarInfo[1], AppCookie.userinfo.getWardcode());
                        if (mboolean) {
                            observer.onNext(true);
                        } else {
                            observer.onError(new Throwable("此用户不存在或未被授权！\n" + AppCookie.barscantype.getCurrentBarscantype()));
                        }
                    } else {
                        observer.onError(new Throwable("二维码类型错误,请重新扫描\n" + AppCookie.barscantype.getCurrentBarscantype()));
                    }
                    Log.d(TAG, "subscribeActual: 2----------");
                    observer.onComplete();
                    Log.d(TAG, "subscribeActual: 2**********");
                    return;
                }

                /**根据二维码类型处理数据 //保存wifi链接信息 */
                if (strBarInfo[0].equals(AppConfig.barScanType.registType)) {
                    AppCookie.wifi.setip(strBarInfo[1]);
                    AppCookie.wifi.setssid(strBarInfo[2]);
                    AppCookie.wifi.setpassword(strBarInfo[3]);
                    observer.onNext(true);
                } else if (strBarInfo[0].equals("NURSE")) {
                    /**验证数据库中的用户是否存在*/
                    observer.onNext(true);
                }

                Log.d(TAG, "subscribeActual: 3----------");
                observer.onComplete();
                Log.d(TAG, "subscribeActual: 3**********");
                return;


            }
        };
    }





}
