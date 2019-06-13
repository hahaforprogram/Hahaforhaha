package com.haha.hahaforhaha.db.entries;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_users")
public class Tbl_users {
    @PrimaryKey
    @NonNull
    private String usercode;
    private String userid;
    private String username;
    private String userdeptcode;
    private String userwardcode;
    private String userdeptname;
    private String userwardname;
    private String userrole;


    @Override
    public String toString() {
        return "Tbl_users{" +
                "usercode='" + usercode + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", userdeptcode='" + userdeptcode + '\'' +
                ", userwardcode='" + userwardcode + '\'' +
                ", userdeptname='" + userdeptname + '\'' +
                ", userwardname='" + userwardname + '\'' +
                ", userrole='" + userrole + '\'' +
                '}';
    }

    @NonNull
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(@NonNull String usercode) {
        this.usercode = usercode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserdeptcode() {
        return userdeptcode;
    }

    public void setUserdeptcode(String userdeptcode) {
        this.userdeptcode = userdeptcode;
    }

    public String getUserwardcode() {
        return userwardcode;
    }

    public void setUserwardcode(String userwardcode) {
        this.userwardcode = userwardcode;
    }

    public String getUserdeptname() {
        return userdeptname;
    }

    public void setUserdeptname(String userdeptname) {
        this.userdeptname = userdeptname;
    }

    public String getUserwardname() {
        return userwardname;
    }

    public void setUserwardname(String userwardname) {
        this.userwardname = userwardname;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }
}
