package com.haha.hahaforhaha.localdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.haha.hahaforhaha.contraint.AppCookie;
import com.haha.hahaforhaha.contraint.AppSql;

public class LocalDbHelper extends SQLiteOpenHelper {
    private volatile static LocalDbHelper instance;

    private LocalDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private LocalDbHelper(Context context) {
        this(context, AppCookie.appsetting.getdatabaseName() , null, AppSql.localDbVersion);
    }

    /**
     * 双重验证的单例方法，通过getDBHelper得到helper对象来得到数据库，保证helper类是单例的
     */
    public static LocalDbHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalDbHelper.class) {
                if (instance == null) {
                    instance = new LocalDbHelper(context);
                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /**创建用户信息表*/
        db.execSQL(AppSql.Sql_appusers);
        /***创建病人信息表*/
        db.execSQL(AppSql.Sql_patsinfo);
        /***创建病人医嘱信息表*/
        db.execSQL(AppSql.Sql_patsorders);
        /***创建病人体征信息表*/
        db.execSQL(AppSql.Sql_patvitalsignrec);
        /**创建医嘱索引*/
        db.execSQL(AppSql.sql_create_patsorders_index);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
