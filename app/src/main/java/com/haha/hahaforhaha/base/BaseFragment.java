package com.haha.hahaforhaha.base;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * A simple subclass.
 */
public abstract class BaseFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    protected Context context;
    Unbinder unbinder;
    /***设置布局名称R.layout.xxxxx*/
    protected abstract int getContentViewLayoutID();
    /*** 初始化界面 @savedInstanceState*/
    protected abstract void initView(Bundle savedInstanceState);
    /*** 初始化数据*/
    protected abstract void initData();
    /*** 绑定事件*/
    protected abstract void initEvent();
    /*** 初始化绑定listner*/
    protected abstract void initBindListener();

    /***获取传递参数*/
    public abstract void initParms(Bundle parms);

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {if (getContentViewLayoutID() != 0) {
        return inflater.inflate(getContentViewLayoutID(), container, false);
    }
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initBindListener();
        initView(savedInstanceState);
        initData();
        initEvent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
    }

    @Override
    public Context getContext() {
        if (context == null) {
            context = getActivity();
        }
        return context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }


    /*** toasty*/
    public void showInfo(String showFlag,String strMsg){
        showFlag = showFlag.toLowerCase();
        switch (showFlag) {
            case ""    :
                Toasty.info(context,strMsg).show();break;
            case "info":Toasty.info(context,strMsg).show();break;
            case "success":Toasty.success(context,strMsg).show();break;
            case "warning":Toasty.warning(context,strMsg).show();break;
            case "error":Toasty.error(context,strMsg).show();break;
            default:Toasty.info(context,strMsg).show();break;
        }
    }


    public void hideSoftInputMethod(EditText ed) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

}
