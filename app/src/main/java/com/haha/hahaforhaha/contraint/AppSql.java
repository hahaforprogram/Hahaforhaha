package com.haha.hahaforhaha.contraint;

import com.haha.hahaforhaha.localdb.bean.PatInfoBean;
import com.haha.hahaforhaha.localdb.bean.PatOrderBean;
import com.haha.hahaforhaha.localdb.bean.PatVitalSignRecBean;
import com.haha.hahaforhaha.localdb.bean.UserInfoBean;

public class AppSql {
    /***sqlite 数据库版本*/
    public static final int localDbVersion = 1;

    /***sqlite本地数据表名*/
    public static final String TABLE_APPUSER = "app_users";
    public static final String TABLE_PATSINFO = "pat_info";
    public static final String TABLE_PATSORDERS = "pat_order";
    public static final String TABLE_PATSVITALSIGNREC = "pat_vitalsignrec";

    public static final String Sql_appusers = "CREATE TABLE " + TABLE_APPUSER + "("
            + UserInfoBean.COLUMN_NAME_USERCODE + " text ,"
            + UserInfoBean.COLUMN_NAME_USERID + " text ,"
            + UserInfoBean.COLUMN_NAME_USERNAME + " text ,"
            + UserInfoBean.COLUMN_NAME_WARDCODE + " text ,"
            + UserInfoBean.COLUMN_NAME_WARDNAME + " text ,"
            + UserInfoBean.COLUMN_NAME_DEPTCODE + " text ,"
            + UserInfoBean.COLUMN_NAME_DEPTNAME + " text ,"
            + UserInfoBean.COLUMN_NAME_USERROLE + " text ,"
            + "PRIMARY KEY(" + UserInfoBean.COLUMN_NAME_USERCODE + "," + UserInfoBean.COLUMN_NAME_WARDCODE + "));";

    public static final String Sql_patsinfo = "CREATE TABLE " + TABLE_PATSINFO + "("
            + PatInfoBean.COLUMN_NAME_INP_NO + " text,"
            + PatInfoBean.COLUMN_NAME_PATIENT_ID + " text,"
            + PatInfoBean.COLUMN_NAME_VISIT_ID + " integer,"
            + PatInfoBean.COLUMN_NAME_NAME + " text,"
            + PatInfoBean.COLUMN_NAME_SEX + " text,"
            + PatInfoBean.COLUMN_NAME_NATION + " text,"
            + PatInfoBean.COLUMN_NAME_BIRTH_DATE + " text,"
            + PatInfoBean.COLUMN_NAME_CHARGE_TYPE + " text,"
            + PatInfoBean.COLUMN_NAME_IDENTITY + " text,"
            + PatInfoBean.COLUMN_NAME_DIAGNOSIS + " text ,"
            + PatInfoBean.COLUMN_NAME_ADMISSION_DATE_TIME + " text,"
            + PatInfoBean.COLUMN_NAME_OPERATING_DATE + " text,"
            + PatInfoBean.COLUMN_NAME_BED_APPROVED_TYPE + " text,"
            + PatInfoBean.COLUMN_NAME_ROOM_NO + " text,"
            + PatInfoBean.COLUMN_NAME_BED_NO + " text,"
            + PatInfoBean.COLUMN_NAME_BODY_WEIGHT + " text,"
            + PatInfoBean.COLUMN_NAME_BODY_HEIGHT + " text,"
            + PatInfoBean.COLUMN_BLOOD_TYPE + " text,"
            + PatInfoBean.COLUMN_NAME_DEPT_CODE + " text,"
            + PatInfoBean.COLUMN_NAME_DEPT_NAME + " text,"
            + PatInfoBean.COLUMN_NAME_WARD_CODE + " text,"
            + PatInfoBean.COLUMN_NAME_WARD_NAME + " text,"
            + PatInfoBean.COLUMN_NAME_PATIENT_CLASS + " text,"
            + PatInfoBean.COLUMN_NAME_PATIENT_COND + " text,"
            + PatInfoBean.COLUMN_NAME_DOCTOR_NAME + " text,"
            + PatInfoBean.COLUMN_NAME_PATIENT_IMAGE + " text,"
            + PatInfoBean.COLUMN_NAME_ALERGY_DRUGS + " text,"
            + PatInfoBean.COLUMN_NAME_PREDISCHARGE_DATE + " text,"
            + PatInfoBean.COLUMN_NAME_MAILING_ADDRESS + " text,"
            + PatInfoBean.COLUMN_NAME_NEXT_OF_KIN + " text,"
            + PatInfoBean.COLUMN_NAME_NEXT_OF_KIN_PHONE + " text,"
            + PatInfoBean.COLUMN_NAME_PHONE_NUMBER_HOME + " text,"
            + PatInfoBean.COLUMN_NAME_PHONE_NUMBER_BUSINESS + " text,"
            + PatInfoBean.COLUMN_NAME_PREPAYMENTS + " text,"
            + PatInfoBean.COLUMN_NAME_TOTAL_COSTS + " text,"
            + PatInfoBean.COLUMN_NAME_SETTLED_FLAG + " integer,"
            + "PRIMARY KEY(" + PatInfoBean.COLUMN_NAME_PATIENT_ID + "," + PatInfoBean.COLUMN_NAME_VISIT_ID + "));";

    public static final String Sql_patsorders = "create table if not exists " + TABLE_PATSORDERS + "("
            + PatOrderBean.COLUMN_NAME_INDEX_ID + " text ,"
            + PatOrderBean.COLUMN_NAME_PATIENT_ID + " text ,"
            + PatOrderBean.COLUMN_NAME_VISIT_ID + " Integer ,"
            + PatOrderBean.COLUMN_NAME_ORDER_NO + " Integer ,"
            + PatOrderBean.COLUMN_NAME_ORDERING_DEPT + " text ,"
            + PatOrderBean.COLUMN_NAME_REPEAT_INDICATOR + " Integer ,"
            + PatOrderBean.COLUMN_NAME_START_DATE_TIME + " text ,"
            + PatOrderBean.COLUMN_NAME_STOP_DATE_TIME + " text ,"
            + PatOrderBean.COLUMN_NAME_FREQ_DETAIL + " text ,"
            + PatOrderBean.COLUMN_NAME_DEFAULT_SCHEDULE + " text ,"
            + PatOrderBean.COLUMN_NAME_NEW_DEFAULT_SCHEDULE + " text ,"
            + PatOrderBean.COLUMN_NAME_PLAN_EXEC_DATETIME + " text ,"
            + PatOrderBean.COLUMN_NAME_LAB_EXAM_CLASS + " text ,"
            + PatOrderBean.COLUMN_NAME_IS_EMER + " Integer ,"
            + PatOrderBean.COLUMN_NAME_AJUST_MEMO + " text ,"
            + PatOrderBean.COLUMN_NAME_ORDER_CLASS + " text ,"
            + PatOrderBean.COLUMN_NAME_ORDER_TEXT + " text ,"
            + PatOrderBean.COLUMN_NAME_ADMINISTRATION + " text ,"
            + PatOrderBean.COLUMN_NAME_FREQUENCY + " text ,"
            + PatOrderBean.COLUMN_NAME_FREQ_COUNTER + " text ,"
            + PatOrderBean.COLUMN_NAME_FREQ_INTERVAL + " integer ,"
            + PatOrderBean.COLUMN_NAME_FREQ_INTERVAL_UNIT + " text ,"
            + PatOrderBean.COLUMN_NAME_ORDER_STATUS + " integer ,"
            + PatOrderBean.COLUMN_NAME_DOCTOR + " text ,"
            + PatOrderBean.COLUMN_NAME_NURSE + " text ,"
            + PatOrderBean.COLUMN_NAME_STOP_INFO + " text ,"
            + PatOrderBean.COLUMN_NAME_RELATIVE_NO + " text ,"
            + PatOrderBean.COLUMN_NAME_ORDER_TYPE + " text ,"
            + PatOrderBean.COLUMN_NAME_EXEC_DATETIME + " text ,"
            + PatOrderBean.COLUMN_NAME_EXEC_OPERATOR + " text ,"
            + PatOrderBean.COLUMN_NAME_EXEC_STATUS + " Integer ,"
            + PatOrderBean.COLUMN_NAME_FIX_EXEC_DATETIME + " text ,"
            + PatOrderBean.COLUMN_NAME_FIX_EXEC_OPERATOR + " text ,"
            + PatOrderBean.COLUMN_NAME_SYNC_STATUS + " Integer ,"
            + PatOrderBean.COLUMN_NAME_SYNC_DATETIME + " text ,"
            + PatOrderBean.COLUMN_NAME_SYNC_MACHINE + " text ,"
            + PatOrderBean.COLUMN_NAME_FLAG + " Integer , "
            + "PRIMARY KEY (" + PatOrderBean.COLUMN_NAME_INDEX_ID + "));";

    public static final String sql_create_patsorders_index = "CREATE INDEX IF NOT EXISTS IND_1_PATORDERS ON " + TABLE_PATSORDERS +
            "(" + PatOrderBean.COLUMN_NAME_PATIENT_ID + "," + PatOrderBean.COLUMN_NAME_VISIT_ID + "," + PatOrderBean.COLUMN_NAME_ORDER_NO + "," +
            PatOrderBean.COLUMN_NAME_PLAN_EXEC_DATETIME + "," + PatOrderBean.COLUMN_NAME_SYNC_STATUS + ")";

    public static final String Sql_patvitalsignrec = "create table if not exists " + TABLE_PATSVITALSIGNREC + "("
            + PatVitalSignRecBean.COLUMN_NAME_PATIENT_ID + " text ,"
            + PatVitalSignRecBean.COLUMN_NAME_VISIT_ID + " Integer ,"
            + PatVitalSignRecBean.COLUMN_NAME_RECORDING_DATE + " text ,"
            + PatVitalSignRecBean.COLUMN_NAME_TIME_POINT + " text ,"
            + PatVitalSignRecBean.COLUMN_NAME_VITAL_SIGN + " text ,"
            + PatVitalSignRecBean.COLUMN_NAME_VITAL_SIGN_VALUES + " text ,"
            + PatVitalSignRecBean.COLUMN_NAME_UNITS + " text ,"
            + PatVitalSignRecBean.COLUMN_NAME_ENTER_DATE_TIME + " text, "
            + PatVitalSignRecBean.COLUMN_NAME_OPERATOR + " text ,"
            + PatVitalSignRecBean.COLUMN_NAME_SYNC_MACHINE + " text ,"
            + PatVitalSignRecBean.COLUMN_NAME_SYNC_FLAG + " integer ,"
            + PatVitalSignRecBean.COLUMN_NAME_SYNC_DATETIME + " text , "
            + "PRIMARY KEY (" + PatVitalSignRecBean.COLUMN_NAME_PATIENT_ID + "," + PatVitalSignRecBean.COLUMN_NAME_VISIT_ID + "," + PatVitalSignRecBean.COLUMN_NAME_RECORDING_DATE + "," + PatVitalSignRecBean.COLUMN_NAME_TIME_POINT + "," + PatVitalSignRecBean.COLUMN_NAME_VITAL_SIGN + "));";


    /*** app_users删除清空*/
    public static String sqlDelAppuser = "delete from app_users ";
    /*** 插入app_users*/
    public static String sqlInsertAppuser = "replace into app_users(usercode,userid,username,userwardcode,userwardname,userdeptcode,userdeptname,userrole) values (?,?,?,?,?,?,?,?) ";
    /*** 查询用户信息*/
    public static String sqlQueryAppuser = "select * from app_users where userid=? and userwardcode=? ";
    /***清空病人基本信息表*/
    public static String sqlDeletePatInfo = "delete from pat_info";
    /**
     * 插入病人基本信息表
     **/
    public static String sqlInsertPatInfo = "replace into pat_info(" +
            "inp_no,patient_id,visit_id,name,sex,nation,birth_date,charge_type,identity,diagnosis,admission_date_time," +
            "operating_date,bed_approved_type,room_no,bed_no,body_weight,body_height,blood_type,dept_code,dept_name," +
            "ward_code,ward_name,patient_class,patient_cond,doctor_name,patient_image,alergy_drugs,predischarge_date," +
            "mailing_address,next_of_kin,next_of_kin_phone,phone_number_home,phone_number_business," +
            "prepayments,total_costs,settled_flag ) " +
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String sqlDeletePatOrder = "delete from pat_order";

    public static String sqlInsertPatOrder = "replace into pat_order("
            + "index_id,"
            + "patient_id,"
            + "visit_id,"
            + "ordering_dept,"
            + "order_no,"
            + "repeat_indicator,"
            + "start_date_time,"
            + "stop_date_time,"
            + "freq_detail,"
            + "default_schedule,"
            + "new_default_schedule,"
            + "plan_exec_datetime,"
            + "lab_exam_class,"
            + "is_emer,"
            + "ajust_memo,"
            + "order_class,"
            + "order_text,"
            + "administration,"
            + "frequency,"
            + "freq_counter,"
            + "freq_interval,"
            + "freq_interval_unit,"
            + "order_status,"
            + "doctor,"
            + "nurse,"
            + "stop_info,"
            + "relative_no,"
            + "order_type,"
            + "exec_datetime,"
            + "exec_operator,"
            + "exec_status,"
            + "sync_status)"
            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 医嘱ORDERS
     */
    public static String sqlselectPatsOrders = "select po.* from pat_info pi " +
            "inner join pat_order po on (pi.patient_id=po.patient_id and pi.visit_id=po.visit_id ) " +
            "where pi.patient_id=? and pi.visit_id=?";


}
