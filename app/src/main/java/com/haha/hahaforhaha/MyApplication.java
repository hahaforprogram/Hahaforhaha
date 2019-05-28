package com.haha.hahaforhaha;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haha.hahaforhaha.contraint.AppConfig;
import com.haha.hahaforhaha.contraint.AppCookie;
import com.haha.hahaforhaha.utils.FileUtils;
import com.haha.hahaforhaha.utils.MyUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mmkv.MMKV;

public class MyApplication extends Application {
    private static final String TAG = "Myapplication";
    private static final Boolean isArouteDebug = true;
    private static MyApplication instance;
    private static MMKV mmkv = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initSp(instance);
        initApp();
        initLogger();
        initArouter();
    }

    private boolean isDebug() {
        return isArouteDebug;
    }

    private void initArouter(){
        if (isDebug()) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(instance);
    }

    private void initSp(Context context){
        MMKV.initialize(context);
        mmkv = MMKV.defaultMMKV();
    }

    /***单例 applicaion ***/
    public static MyApplication getInstance(){
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null) {
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }

    /***初始化logger日志**/
    private void initLogger(){
        Logger.addLogAdapter(new AndroidLogAdapter());
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .tag("HFKLog")
                .build();

        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
    }

    /***初始化SmartRefreshLayout**/
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.toolbar_backgroud, android.R.color.white);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });

//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
//            //指定为经典Footer，默认是 BallPulseFooter
//            return new ClassicsFooter(context).setDrawableSize(20);
//        });
    }

    /***初始化app 参数**/

    /*****/
    public static MMKV geSp(){
        if (mmkv == null) mmkv = MMKV.defaultMMKV();
        return mmkv;
    }

    public static void initApp(){
        String strPath;
        /**创建相关目录 设置数据库路径 */
        strPath = instance.getPackageName() + "/" + AppConfig.appSetting.dirDatabase;
        AppCookie.appsetting.setdatabaseName(FileUtils.createDirectory(strPath)+AppConfig.appSetting.databaseName);
        /**设置病人头像存储路径*/
        AppCookie.appsetting.setdirPatImg(FileUtils.createDirectory(instance.getPackageName() + "/" + AppConfig.appSetting.dirPatImg));
        /**设置app 日志 路径*/
        AppCookie.appsetting.setdirSoresImg(FileUtils.createDirectory(instance.getPackageName() + "/" + AppConfig.appSetting.dirSoresImg));
        /**设置其他图片存储路径*/
        AppCookie.appsetting.setdirApplog(FileUtils.createDirectory(instance.getPackageName() + "/" + AppConfig.appSetting.dirApplog));
        /**设置设备mac*/
        AppCookie.appsetting.setDeviceId(MyUtils.GetMacAddress());
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
