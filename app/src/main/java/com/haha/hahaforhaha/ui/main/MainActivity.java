package com.haha.hahaforhaha.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haha.hahaforhaha.R;
import com.haha.hahaforhaha.base.BaseActivity;
import com.haha.hahaforhaha.contraint.AppCookie;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/main/main")
public class MainActivity extends BaseActivity {


    @BindView(R.id.main_toolbar) Toolbar mainToolbar;
    @BindView(R.id.main_viewpager) ViewPager mainViewpager;
    @BindView(R.id.main_navigation) BottomNavigationView mainNavigation;
    @BindView(R.id.container) LinearLayout container;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
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
        initToolBar(mainToolbar, false, AppCookie.userinfo.getDeptname(), AppCookie.userinfo.getUsername() + "[" + AppCookie.userinfo.getUserid() + "]");
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

    @Override
    protected Boolean isBarScanListener() {
        return false;
    }


    /*** 初始化 Toolbar*/
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title, String subtitle) {
        toolbar.setTitle(title);
        toolbar.setSubtitle(subtitle);
        toolbar.setNavigationIcon(null);
        toolbar.setLogo(R.drawable.ic_nurse);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.toolbar_title));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.toolbar_subtitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
