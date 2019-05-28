package com.haha.hahaforhaha.base;

import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haha.hahaforhaha.R;
import com.haha.hahaforhaha.barscan.BarScanReciever;
import com.haha.hahaforhaha.utils.ActivityUtils;
import com.haha.hahaforhaha.utils.EventBusMessage;
import com.haha.hahaforhaha.utils.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public abstract class BaseActivity extends AppCompatActivity implements BarScanReciever.BarScanListener {
    public final String TAG = this.getClass().getSimpleName();
    private static final String lachesisBarScancode = "lachesis_barcode_value_notice_broadcast";
    private BarScanReciever mBarScanReciever = null;

    /***设置布局名称R.layout.xxxxx*/
    protected abstract int getContentViewLayoutID();
    /***设置是否全屏*/
    protected abstract Boolean getIsFullScreen();
    /***设置是否需要标题*/
    protected abstract Boolean getIsShowTitle();
    /***获取传递参数 只能接收数据，不能直接装载数据，因为此方法在initView之前*/
    public abstract void initParms(Bundle parms);
    /*** 初始化界面*/
    protected abstract void initView(Bundle savedInstanceState);
    /*** 初始化绑定listner*/
    protected abstract void initBindListener();
    /*** 初始化数据*/
    protected abstract void initData();
    /*** 绑定事件*/
    protected abstract void initEvent();
    /***是否扫描二维码广播*/
    protected abstract Boolean isBarScanListener();

    /*** 窗口全屏*/
    private void setFullScreen(Boolean fullScreen) {
        if (fullScreen) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            } else {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
            }

        }
    }
    /*** 窗口标题*/
    private void setShowTitle(Boolean showTitle) {
        if (!showTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }
    protected int setStatusBarColor() {
        return R.color.colorPrimary;
    }

    /***判断当前设备版本号是否为4.4以上，如果是，则通过调用setTranslucentStatus让状态栏变透明*/
    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }

    /***沉浸状态栏（4.4以上系统有效） 设置状态栏颜色*/
    protected void setTranslucentStatus(Boolean bool) {
        Window window = getWindow();
        if (bool) {
            /** 设置状态栏全透明*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        /** 沉浸式状态栏*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**5.0以上使用原生方法*/
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /**4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式*/
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }

    /*** toasty*/
    public void showInfo(String showFlag,String strMsg){
        showFlag = showFlag.toLowerCase();
        switch (showFlag) {
            case ""    :Toasty.info(this,strMsg).show();break;
            case "info":Toasty.info(this,strMsg).show();break;
            case "success":Toasty.success(this,strMsg).show();break;
            case "warning":Toasty.warning(this,strMsg).show();break;
            case "error":Toasty.error(this,strMsg).show();break;
            default:Toasty.info(this,strMsg).show();break;
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initBindListener();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowTitle(getIsShowTitle());
        setTranslucentStatus();
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            ARouter.getInstance().inject(this);
            ActivityUtils.getInstance().addActivity(this);
            setFullScreen(getIsFullScreen());
            Bundle bundle = getIntent().getExtras();
            initParms(bundle);
            initView(savedInstanceState);
        }
        if (!EventBusUtils.isRegister(this)){
            EventBusUtils.register(this);
        }
        initEvent();
        initData();
    }

    public void hideSoftInputMethod(EditText ed) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        String methodName = null;
        int currentVersion = Build.VERSION.SDK_INT;
        if (currentVersion >= 16) {          // 4.2
            methodName = "setShowSoftInputOnFocus";  //
        } else if (currentVersion >= 14) {            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            //最低级最不济的方式，这个方式会把光标给屏蔽
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*** 注册 广播 扫描*/
    private void registerBarScanListener() {
        if (isBarScanListener()) {
            if (mBarScanReciever == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(lachesisBarScancode);
                mBarScanReciever = new BarScanReciever();
                registerReceiver(mBarScanReciever, intentFilter);
                mBarScanReciever.SetOnbarScanListener(this);
                Log.d(TAG, "registerBarScanBroadcast: start");
            }
        }
    }

    /*** 销毁广播扫描*/
    private void unregisterBarScanListener() {
        if (mBarScanReciever != null ) {
            try {
                unregisterReceiver(mBarScanReciever);
                mBarScanReciever = null;
                Log.d(TAG, "unregisterBarScanBroadcast: stop");
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    @Override
    public void onBarScanEvent(String strBarCode) {
        Log.d(TAG, "onBarScanEvent: " + strBarCode);
    }

    /*** 接收到分发的事件@param event 事件*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventBusMessage event) {
    }

    /*** 接受到分发的粘性事件@param event 粘性事件*/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventBusMessage event) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBarScanListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterBarScanListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁 EVENTBUS
        if (EventBusUtils.isRegister(this)) {
            EventBusUtils.unregister(this);
        }
        ActivityUtils.getInstance().removeActivity(this);
    }





}
