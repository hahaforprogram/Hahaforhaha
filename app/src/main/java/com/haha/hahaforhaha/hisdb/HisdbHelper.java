package com.haha.hahaforhaha.hisdb;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.haha.hahaforhaha.contraint.AppConfig;
import com.haha.hahaforhaha.contraint.AppCookie;
import com.haha.hahaforhaha.utils.StringUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.reactivex.Observable;
import oracle.jdbc.OracleTypes;


/**
 * @author hahame  @date 2019/1/3
 */
public class HisdbHelper {
    private static final String TAG = "FAIROL-HisdbHelper";
    private static HisdbHelper instance;
    private static Connection hisConnection = null;
    private static ResultSet hisResultSet = null;
    private static SQLiteStatement localstmt = null;
    private static SQLiteDatabase localdb = null;

    /**
     * 初始化单例* @param
     */
    public static synchronized HisdbHelper getInstance() {
        if (instance == null) {
            synchronized (HisdbHelper.class) {
                if (instance == null) {
                    instance = new HisdbHelper();
                }
            }
        }
        return instance;
    }


    private String getDbString(ResultSet rs , String strColumnName) {
        try{
            String strValue = rs.getString(strColumnName);
            if (StringUtils.isStringEmpty(strValue)) {
                return "";
            }else {
                return new String(strValue.getBytes("iso-8859-1"), "gbk");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

    /**连接数据库*/

    /**
     * 通用执行oracle存储过程 返回 resultset
     */
    public static ResultSet executePLSQL(String strPLSQL, String strParameters) {
        /**his oracle 执行存储过错 */
        CallableStatement hisPlSql;
        try {
            if (hisConnection == null || hisConnection.isClosed()) {
                Class.forName(AppConfig.hisDatabase.DriverClass);
                hisConnection = DriverManager.getConnection(AppConfig.hisDatabase.ConnectInfo, AppConfig.hisDatabase.ConnectUser, AppConfig.hisDatabase.ConnectPass);
            }
            strPLSQL = "{ call " + strPLSQL + " }";
            hisPlSql = hisConnection.prepareCall(strPLSQL);
            hisPlSql.setString(1, strParameters);
            hisPlSql.registerOutParameter(2, OracleTypes.CURSOR);
            hisPlSql.execute();
            ResultSet rs = (ResultSet) hisPlSql.getObject(2);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 获取设备信息
     */
    public Observable<Boolean> getAppRegisterInfo() {
        return Observable.create(e -> {
            String strWardcode = null;
            String strWardname = null;
            Boolean isHasRows = false;
            Log.d(TAG, "getAppRegisterInfo: " + AppCookie.appsetting.getDeviceId());
            try {
                hisResultSet = executePLSQL("pck_hcis.gethcisdeviceinfo(?,?)", AppCookie.appsetting.getDeviceId());
                while (hisResultSet.next()) {
                    int intDeviceStatus = hisResultSet.getInt("device_status");
                    strWardcode = getDbString(hisResultSet,"dept_code"); //new String(hisResultSet.getString("dept_code").getBytes("iso-8859-1"), "gbk");
                    strWardname = getDbString(hisResultSet,"dept_name"); //new String(hisResultSet.getString("dept_name").getBytes("iso-8859-1"), "gbk");
                    isHasRows = true;
                    if (intDeviceStatus == -1) {
                        e.onError(new Throwable("此设备已注销，请联系相关人员进行重新注册！"));
                    }
                }
                if (!isHasRows) {
                    e.onError(new Throwable("此设备尚未注册！"));
                } else {
                    Log.d(TAG, "getAppRegisterInf1111: "+strWardcode );
                    AppCookie.userinfo.setWardcode(strWardcode);
                    AppCookie.userinfo.setWardname(strWardname);
                    e.onNext(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                e.onError(new Throwable("获取设备注册信息错误，连接数据库失败:SQLException\n" + ex.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
                e.onError(new Throwable("获取设备注册信息错误，连接数据库失败:Exception\n" + ex.getMessage()));
            } finally {
                if (hisResultSet != null) {
                    hisResultSet.close();
                }
                if (hisConnection != null) {
                    hisConnection.close();
                }
                e.onComplete();
            }
            e.onNext(false);
        });
    }


    public void close() {
        try {
            // 应该明确地关闭所有的数据库资源
            if (null != hisResultSet) {
                hisResultSet.close();
            }

            if (null != localstmt) {
                localstmt.close();
            }

            if (null != hisConnection) {
                hisConnection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

