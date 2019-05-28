package com.haha.hahaforhaha.contraint;

import com.haha.hahaforhaha.MyApplication;
import com.haha.hahaforhaha.utils.Base64Utils;

public class AppCookie {
    /*** 获取当前wifi设置信息*/
    public static class wifi {
        public static void setip(String strip) {
            MyApplication.geSp().putString(AppConfig.wifiInfo.ip, strip);
        }

        public static void setssid(String strssid) {
            MyApplication.geSp().putString(AppConfig.wifiInfo.ssid, strssid);
        }

        public static void setpassword(String strpassword) {
            MyApplication.geSp().putString(AppConfig.wifiInfo.password, strpassword);
        }

        public static String getip() {
            return Base64Utils.base64Decode(MyApplication.geSp().getString(AppConfig.wifiInfo.ip, ""));
        }

        public static String getssid() {
            return Base64Utils.base64Decode(MyApplication.geSp().getString(AppConfig.wifiInfo.ssid, ""));
        }

        public static String getpassword() {
            return Base64Utils.base64Decode(MyApplication.geSp().getString(AppConfig.wifiInfo.password, ""));
        }
    }

    /*** 获取当前hisdatabase设置信息*/
    public static class hisdatabase {
        public static String getDriverClass() {
            return AppConfig.hisDatabase.DriverClass;
        }
        public static String getConnectInfo() {
            return AppConfig.hisDatabase.ConnectInfo;
        }
        public static String getConnectUser() {
            return AppConfig.hisDatabase.ConnectUser;
        }
        public static String getConnectPass() {
            return AppConfig.hisDatabase.ConnectPass;
        }

    }

    /*** 获取当前appsetting设置信息*/
    public static class appsetting {
        public static void setdatabaseName(String databaseName){
            MyApplication.geSp().putString(AppConfig.appSetting.databaseName,databaseName);
        }

        public static void setdirDatabase(String dirDatabase){
            MyApplication.geSp().putString(AppConfig.appSetting.dirDatabase,dirDatabase);
        }

        public static void setdirApplog(String dirApplog){
            MyApplication.geSp().putString(AppConfig.appSetting.dirApplog,dirApplog);
        }

        public static void setdirPatImg(String dirPatImg){
            MyApplication.geSp().putString(AppConfig.appSetting.dirPatImg,dirPatImg);
        }
        public static void setdirSoresImg(String dirSoresImg){
            MyApplication.geSp().putString(AppConfig.appSetting.dirSoresImg,dirSoresImg);
        }

        public static String getdatabaseName(){
            return MyApplication.geSp().getString(AppConfig.appSetting.databaseName,"");
        }

        public static String getdirDatabase(){
            return MyApplication.geSp().getString(AppConfig.appSetting.dirDatabase,"");
        }

        public static String getdirApplog(){
            return MyApplication.geSp().getString(AppConfig.appSetting.dirApplog,"");
        }

        public static String getdirPatImg(){
            return MyApplication.geSp().getString(AppConfig.appSetting.dirPatImg,"");
        }
        public static String getdirSoresImg(){
            return MyApplication.geSp().getString(AppConfig.appSetting.dirSoresImg,"");
        }

        /*** 设备mac*/
        public static void setDeviceId(String strDeviceId) {
            MyApplication.geSp().putString(AppConfig.appSetting.deviceid, strDeviceId);
        }

        public static String getDeviceId() {
            return MyApplication.geSp().getString(AppConfig.appSetting.deviceid,"");
        }

    }

    /*** 获取当前扫描二维码 类型 强制 设置信息*/
    public static class barscantype{
        public static void setCurrentBarscantype(String strbartype){
            MyApplication.geSp().putString(AppConfig.barScanType.currentBarscantype,strbartype);
        }
        public static String getCurrentBarscantype(){
            return MyApplication.geSp().getString(AppConfig.barScanType.currentBarscantype,"");
        }
    }

    public static class userinfo{
        /*** 用户护理单元科室代码*/
        public static void setWardcode(String strWardcode) {
            MyApplication.geSp().putString(AppConfig.userLoginInfo.wardcode, strWardcode);
        }
        public static String getWardcode() {
            return  MyApplication.geSp().getString(AppConfig.userLoginInfo.wardcode,"");
        }

        /*** 用户护理单元科室名称*/
        public static void setWardname(String strWardname) {
            MyApplication.geSp().putString(AppConfig.userLoginInfo.wardname, strWardname);
        }
        public static String getWardname() {
            return MyApplication.geSp().getString(AppConfig.userLoginInfo.wardname,"");
        }

        /*** 用户科室代码*/
        public static void setDeptcode(String strDeptcode) {
            MyApplication.geSp().putString(AppConfig.userLoginInfo.deptcode, strDeptcode);
        }
        public static String getDeptcode() {
            return  MyApplication.geSp().getString(AppConfig.userLoginInfo.deptcode,"");
        }

        /*** 用户科室名称*/
        public static void setDeptname(String strDeptname) {
            MyApplication.geSp().putString(AppConfig.userLoginInfo.deptname, strDeptname);
        }
        public static String getDeptname() {
            return MyApplication.geSp().getString(AppConfig.userLoginInfo.deptname,"");
        }

        /*** 用户ID*/
        public static void setUserid(String strUserid) {
            MyApplication.geSp().putString(AppConfig.userLoginInfo.userid, strUserid);
        }
        public static String getUserid() {
            return MyApplication.geSp().getString(AppConfig.userLoginInfo.userid,"");
        }

        /*** 用户 usercode*/
        public static void setUserCode(String strUserCode) {
            MyApplication.geSp().putString(AppConfig.userLoginInfo.usercode, strUserCode);
        }
        public static String getUsercode() {
            return MyApplication.geSp().getString(AppConfig.userLoginInfo.usercode,"");
        }

        /*** 用户姓名*/
        public static void setUsername(String strUsername) {
            MyApplication.geSp().putString(AppConfig.userLoginInfo.username, strUsername);
        }
        public static String getUsername() {
            return MyApplication.geSp().getString(AppConfig.userLoginInfo.username,"");
        }
    }

}

