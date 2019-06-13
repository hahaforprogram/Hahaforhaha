package com.haha.hahaforhaha.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.haha.hahaforhaha.MyApplication;
import com.haha.hahaforhaha.contraint.AppCookie;
import com.haha.hahaforhaha.db.dao.TblusersDAO;
import com.haha.hahaforhaha.db.entries.Tbl_users;

@Database(entities = {Tbl_users.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final Object sLock = new Object();


    public static AppDatabase getInstance(){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(MyApplication.getInstance(),
                            AppDatabase.class,
                            AppCookie.appsetting.getdatabaseName())
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //version update 1 -  2
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    //version update 2 - 3
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };



    //用户
    public abstract TblusersDAO tblUsersDAO();
}
