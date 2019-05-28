package com.haha.hahaforhaha.contraint;


public class AppConfig {
    /***WIFI连接信息*/
    public static class wifiInfo{
         static final String ip = "IP";
         static final String ssid = "SSID";
         static final String password = "PASSWORD";
    }

    /***His Database信息*/
    public static class hisDatabase {
        public static String DriverClass = "oracle.jdbc.driver.OracleDriver";
        public static String ConnectInfo = "jdbc:oracle:thin:@192.168.202.1:1521:dbserver1";
        public static String ConnectUser = "comminterface";
        public static String ConnectPass = "cp6vm";
    }

    /***系统设置*/
    public static class appSetting {
        /*** 数据库存储目录*/
        public static final String dirDatabase = "Database";
        /*** 病人头像存储目录*/
        public static final String dirPatImg = "PatImg";
        /*** 其他图片存储目录*/
        public static final String dirSoresImg = "SoresImg";
        /*** 系统日志*/
        public static final String dirApplog = "AppLog";
        /*** app database 信息 */
        public static final String databaseName = "Hcis.db";

        public static final String deviceid = "DeviceId";
    }

    /***扫描类型*/
    public static class barScanType{
        public static final String currentBarscantype="currentBarscantype";
        public static final String registType="APPCONFIG";
        public static final String loginType="NURSE";
        public static final String loginType1="LOGIN";
        public static final String bedType="BED";
        public static final String roomType="ROOM";
    }

    /***用户登录后信息*/
    public static class userLoginInfo {
        public static final String usercode = "USERCODE";
        public static final String userid = "USERID";
        public static final String username = "USERNAME";
        public static final String wardcode = "WARDCODE";
        public static final String wardname = "WARDNAME";
        public static final String deptcode = "DEPTCODE";
        public static final String deptname = "DEPTNAME";
    }

}
