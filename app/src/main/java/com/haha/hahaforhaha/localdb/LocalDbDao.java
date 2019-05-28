package com.haha.hahaforhaha.localdb;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.haha.hahaforhaha.MyApplication;
import com.haha.hahaforhaha.contraint.AppCookie;
import com.haha.hahaforhaha.contraint.AppSql;

public class LocalDbDao {

    private static LocalDbDao instance;
    private static final String TAG = "FAIROL-LocalDbDao";

    public static LocalDbDao getInstance() {
        if (instance == null) {
            synchronized (LocalDbDao.class) {
                if (instance == null) {
                    instance = new LocalDbDao();
                }
            }
        }
        return instance;
    }

    /*** 查询用户信息  用于用户登录判断 */
    public static Boolean appUserLogin(final String strUserCode, final String struserwardcode) {
        SQLiteDatabase db = LocalDbHelper.getInstance(MyApplication.getInstance()).getReadableDatabase();
        Log.d(TAG, "appUserLogin: "+ struserwardcode);
        try {
            Cursor rs = db.rawQuery(AppSql.sqlQueryAppuser, new String[]{strUserCode, struserwardcode});
            if (rs.moveToFirst()) {
                AppCookie.userinfo.setUserCode(rs.getString(0));
                AppCookie.userinfo.setUserid(rs.getString(1));
                AppCookie.userinfo.setUsername(rs.getString(2));
                Log.d(TAG, "queryUserLoginInfo: " + rs.getString(2));
                rs.close();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
