package com.haha.hahaforhaha.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.haha.hahaforhaha.db.entries.Tbl_users;

import java.util.List;

@Dao
public interface TblusersDAO {
    @Transaction

    @Query("select * From Tbl_users")
    List<Tbl_users> getUsersAll();

    @Query("select count(1) From Tbl_users where usercode=:usercode")
    long countUsersByusercode(String usercode);

    @Query("select * From Tbl_users where usercode=:usercode")
    List<Tbl_users> getUsersByusercode (String usercode);

    @Query("select * From Tbl_users where userid=:userid")
    List<Tbl_users> getUsersByuserid (String userid);

    @Query("select * From Tbl_users where userdeptcode=:userdeptcode")
    List<Tbl_users> getUsersByuserdeptcode(String userdeptcode);

    @Query("select * From Tbl_users where userwardcode=:userwardcode")
    List<Tbl_users> getUsersByuseruserwardcode(String userwardcode);

    @Query("delete from Tbl_users")
    int delUsersAll();

    @Delete
    int delUsers(Tbl_users tblUsers);

    @Query("delete from Tbl_users where usercode=:usercode")
    int delUsersByusercode(String usercode);

    @Query("delete from Tbl_users where userid=:userid")
    void delUsersByuserid(String userid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUsers(Tbl_users tblUsers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertUsers(Tbl_users... tblAppusers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertUsersList(List<Tbl_users> tblUsersList);

}
