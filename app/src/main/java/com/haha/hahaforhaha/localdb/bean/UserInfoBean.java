package com.haha.hahaforhaha.localdb.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Haha on 2017/10/9.
 */

public class UserInfoBean implements Parcelable {
    public static final String COLUMN_NAME_USERCODE = "usercode";
    public static final String COLUMN_NAME_USERID = "userid";
    public static final String COLUMN_NAME_USERNAME = "username";
    public static final String COLUMN_NAME_WARDCODE = "userwardcode";
    public static final String COLUMN_NAME_WARDNAME = "userwardname";
    public static final String COLUMN_NAME_DEPTCODE = "userdeptcode";
    public static final String COLUMN_NAME_DEPTNAME = "userdeptname";
    public static final String COLUMN_NAME_USERROLE = "userrole";

    private static String usercode;
    private static String userid;
    private static String username;
    private static String userdeptcode;
    private static String userwardcode;
    private static String userdeptname;
    private static String userwardname;
    private static String userrole;

    public UserInfoBean() {
    }


    public static String getUsercode() {
        return usercode;
    }

    public static void setUsercode(String usercode) {
        UserInfoBean.usercode = usercode;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        UserInfoBean.userid = userid;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserInfoBean.username = username;
    }

    public static String getUserdeptcode() {
        return userdeptcode;
    }

    public static void setUserdeptcode(String userdeptcode) {
        UserInfoBean.userdeptcode = userdeptcode;
    }

    public static String getUserwardcode() {
        return userwardcode;
    }

    public static void setUserwardcode(String userwardcode) {
        UserInfoBean.userwardcode = userwardcode;
    }

    public static String getUserdeptname() {
        return userdeptname;
    }

    public static void setUserdeptname(String userdeptname) {
        UserInfoBean.userdeptname = userdeptname;
    }

    public static String getUserwardname() {
        return userwardname;
    }

    public static void setUserwardname(String userwardname) {
        UserInfoBean.userwardname = userwardname;
    }

    public static String getUserrole() {
        return userrole;
    }

    public static void setUserrole(String userrole) {
        UserInfoBean.userrole = userrole;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }


    protected UserInfoBean(Parcel in) {
    }

    public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
