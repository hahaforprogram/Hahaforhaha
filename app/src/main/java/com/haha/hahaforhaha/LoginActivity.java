package com.haha.hahaforhaha;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haha.hahaforhaha.barscan.BarScanHelper;
import com.haha.hahaforhaha.base.BaseActivity;
import com.haha.hahaforhaha.contraint.AppConfig;
import com.haha.hahaforhaha.contraint.AppCookie;
import com.haha.hahaforhaha.db.AppDatabase;
import com.haha.hahaforhaha.db.entries.Tbl_users;
import com.haha.hahaforhaha.utils.EventBusMessage;
import com.haha.hahaforhaha.utils.MyUtils;
import com.haha.hahaforhaha.utils.NetworkUtils;
import com.haha.hahaforhaha.utils.SpannableStringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.pb_SyncStatus) ProgressBar pbSyncStatus;
    @BindView(R.id.tv_Appname) TextView tvAppname;
    @BindView(R.id.tv_Message) TextView tvMessage;
    @BindView(R.id.bt_retry) Button btRetry;
    @BindView(R.id.tvHosptialname) TextView tvHosptialname;
    @BindView(R.id.tvAppVersion) TextView tvAppVersion;

    private final int maxRetries = 3;
    private final int retryDelayMillis = 1000;

    private int retryCount;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected Boolean getIsFullScreen() {
        return true;
    }

    @Override
    protected Boolean getIsShowTitle() {
        return true;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        btRetry.setVisibility(View.GONE);
        tvAppVersion.setText(String.format("软件版本:%s", MyUtils.getAppVersion()));
        tvMessage.setText("");
        initWifiNetwork();
        btRetry.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean isBarScanListener() {
        return true;
    }

    @Override
    protected void initBindListener() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
    }

    private void test() {
        //initWifiNetwork();
        Tbl_users users = new Tbl_users();
        users.setUsercode("abc");
        users.setUserid("abc0--123123123");
        users.setUserdeptcode("abc");

        AppDatabase ap = AppDatabase.getInstance();

        Long q1 = ap.tblUsersDAO().countUsersByusercode("abc");


        users.setUsercode("xxx");
        ap.tblUsersDAO().insertUsers(users);

        if (q1 > 0) {
            long a = ap.tblUsersDAO().delUsersAll();
            Log.d(TAG, "delUsersAll: " + a);
            long aaa = ap.tblUsersDAO().insertUsers(users);
            Log.d(TAG, "insertUsers: " + aaa);
        } else {
            Tbl_users users1 = new Tbl_users();
            users1.setUsercode("abc");
            users1.setUserid("abc0--123123123");
            users1.setUserdeptcode("abc");
            ap.tblUsersDAO().insertUsers(users1);
        }

        List<Tbl_users> abc;
//        abc= ap.tblUsersDAO().getUsersAll();
//        Log.d(TAG, "onViewClicked: " + abc);
//
//        ap.tblUsersDAO().delUsersAll();


        abc = ap.tblUsersDAO().getUsersAll();
        Log.d(TAG, "onViewClicked: " + abc);
    }

    @OnClick(R.id.bt_retry)
    public void onViewClicked() {

    }

    @SuppressLint({"CheckResult"})
    private void initWifiNetwork() {
        retryCount = 0;
        btRetry.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(AppCookie.wifi.getip()) ||
                TextUtils.isEmpty(AppCookie.wifi.getssid()) ||
                TextUtils.isEmpty(AppCookie.wifi.getpassword())) {
            AppCookie.barscantype.setCurrentBarscantype(AppConfig.barScanType.registType);
            tvMessage.setText("请先扫描配置二维码");
            tvMessage.setTextColor(Color.RED);
            return;
        }
        tvMessage.setTextColor(Color.BLACK);
        NetworkUtils.getInstance(MyApplication.getInstance())
                .connect(AppCookie.wifi.getip(), AppCookie.wifi.getssid(), AppCookie.wifi.getpassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(observable -> observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (++retryCount <= maxRetries) {
                        runOnUiThread(() -> tvMessage.setText(SpannableStringUtils.getBuilder("正在第")
                                .append(String.valueOf(retryCount)).setForegroundColor(Color.RED).setBold().setProportion(2)
                                .append("次，尝试连接wifi,请稍等...").create()));
                        return Observable.timer(retryDelayMillis,
                                TimeUnit.MILLISECONDS);
                    } else {
                        return Observable.error(throwable);
                    }
                }))
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        tvMessage.setText("连接Wifi成功\n正在验证此设备的注册信息...");
                        btRetry.setVisibility(View.VISIBLE);
                        intAppRegisterInfo();
                    } else {
                        tvMessage.setText("连接wifi失败，请重试!");
                        btRetry.setVisibility(View.VISIBLE);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    tvMessage.setTextColor(getResources().getColor(R.color.red_normal));
                    tvMessage.setText(throwable.getMessage());
                    btRetry.setVisibility(View.VISIBLE);
                });
    }

    @SuppressLint("CheckResult")
    private void intAppRegisterInfo() {
        btRetry.setVisibility(View.VISIBLE);
//        HisdbHelper.getInstance().getAppRegisterInfo()
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//                .subscribe(aBoolean -> {
//                    if (aBoolean) {
//                        tvMessage.setText("验证注册信息成功.");
//                        syncData();
//                    } else {
//                        tvMessage.setText("验证注册信息失败.");
//                        btRetry.setVisibility(View.VISIBLE);
//                    }
//                }, throwable -> tvMessage.setText(throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    private void syncData() {
        btRetry.setVisibility(View.VISIBLE);
        tvMessage.setText("同步数据,请稍等... ...");
        setSyncStatus(true);
//        Observable<Boolean> syncData = new SyncDataHelper().SyncData();
//        syncData.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//                .subscribe(aBoolean -> {
//                    Log.d(TAG, "syncData: " + aBoolean);
//                    LoginActivity.this.setSyncStatus(false);
//                    AppCookie.barscantype.setCurrentBarscantype(AppConfig.barScanType.loginType1);
//                    tvMessage.setTextColor(Color.BLACK);
//                    tvMessage.setText(SpannableStringUtils.getBuilder(AppCookie.userinfo.getDeptname()).setForegroundColor(Color.BLUE).setProportion(-1)
//                            .append("\n请扫描二维码登陆")
//                            .create());
//                }, throwable -> {
//                   setSyncStatus(false);
//                    tvMessage.setText(throwable.getMessage());
//                    btRetry.setVisibility(View.VISIBLE);
//                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void onBarScanEvent(String strBarCode) {
        super.onBarScanEvent(strBarCode);
        BarScanHelper.getInstance().barScan(strBarCode)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(aBoolean -> {
                    btRetry.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onBarScanEvent: " + aBoolean);
                    if (aBoolean) {
                        if (strBarCode.contains(AppConfig.barScanType.registType)) {
                            tvMessage.setText("App设置成功\n正在进行验证及数据同步操作");
                            initWifiNetwork();
                        } else if (strBarCode.contains(AppConfig.barScanType.loginType)) {
                            tvMessage.setText("用户验证成功");
                            ARouter.getInstance().build("/main/main").navigation();
//                            gotoActivity(MainActivity.class);
//                            WelcomeActivity.this.finish();
                        }
                    }
                }, throwable -> {
                    tvMessage.setText(throwable.getMessage());
                    showInfo("ERROR", "扫描失败!\n" + throwable.getMessage());
                });
    }


    private void setSyncStatus(Boolean bl) {
        if (bl) {
            pbSyncStatus.setVisibility(View.VISIBLE);
        } else {
            pbSyncStatus.setVisibility(View.INVISIBLE);
        }
    }

    public void onReceiveStickyEvent(EventBusMessage event) {
        super.onReceiveStickyEvent(event);
//        showInfo("",event.getData().toString());
//        Log.d(TAG, "onReceiveStickyEvent: " + event.getData().toString());
        tvMessage.setText(event.getData().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
