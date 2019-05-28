package com.haha.hahaforhaha.hisdb;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.haha.hahaforhaha.MyApplication;
import com.haha.hahaforhaha.contraint.AppCookie;
import com.haha.hahaforhaha.contraint.AppSql;
import com.haha.hahaforhaha.localdb.LocalDbHelper;
import com.haha.hahaforhaha.localdb.bean.UserInfoBean;
import com.haha.hahaforhaha.utils.EventBusMessage;
import com.haha.hahaforhaha.utils.EventBusUtils;
import com.haha.hahaforhaha.utils.FileUtils;
import com.haha.hahaforhaha.utils.MyUtils;
import com.haha.hahaforhaha.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;


/**
 * @author hahame  @date 2018/12/24
 */
public class SyncDataHelper {
    private static final String TAG = "FAIROL-SyncDataHelper";
    /**
     * his连接数据库信息
     */
    private volatile static SyncDataHelper instance;
    private static SQLiteStatement localstmt = null;
    private static SQLiteDatabase localdb = null;
    private int intAppUserRowcount = 0;
    private int intPatInfoRowcount = 0;
    private int intPatOrderRowcount = 0;

    /**
     * 初始化单例* @param
     */
    public static synchronized SyncDataHelper getInstance() {
        if (instance == null) {
            synchronized (SyncDataHelper.class) {
                if (instance == null) {
                    instance = new SyncDataHelper();
                }
            }
        }
        return instance;
    }


    private String getDbString(ResultSet rs, String strColumnName) {
        try {
            String strValue = rs.getString(strColumnName);
            if (StringUtils.isStringEmpty(strValue)) {
                return "";
            } else {
                return new String(strValue.getBytes("iso-8859-1"), "gbk");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }


    /**
     * 获取用户信息
     */
    public Observable<Boolean> getUserInfo() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                try {
                    intAppUserRowcount = 0;
                    /**打开本地数据库*/
                    EventBusUtils.postSticky(new EventBusMessage<>("syncgetUserInfo", "正在同步用户信息，请稍等... ..."));
                    LocalDbHelper localDbHelper = LocalDbHelper.getInstance(MyApplication.getInstance());
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localdb.execSQL(AppSql.sqlDelAppuser);
                    localdb.setTransactionSuccessful();
                    localdb.endTransaction();
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localstmt = localdb.compileStatement(AppSql.sqlInsertAppuser);
                    Log.d(TAG, "getUsersInfo: " + AppCookie.userinfo.getWardcode());
                    ResultSet hisResultSet = HisdbHelper.getInstance().executePLSQL("pck_hcis.getUsersInfo(?,?)", AppCookie.userinfo.getWardcode());
                    while (hisResultSet.next()) {
                        localstmt.bindString(1, getDbString(hisResultSet, UserInfoBean.COLUMN_NAME_USERCODE));// new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_USERCODE).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(2, getDbString(hisResultSet, UserInfoBean.COLUMN_NAME_USERID));//new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_USERID).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(3, getDbString(hisResultSet, UserInfoBean.COLUMN_NAME_USERNAME));//new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_USERNAME).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(4, getDbString(hisResultSet, UserInfoBean.COLUMN_NAME_WARDCODE));//new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_DEPTCODE).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(5, getDbString(hisResultSet, UserInfoBean.COLUMN_NAME_WARDNAME));//new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_DEPTCODE).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(6, getDbString(hisResultSet, UserInfoBean.COLUMN_NAME_DEPTCODE));//new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_DEPTCODE).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(7, getDbString(hisResultSet, UserInfoBean.COLUMN_NAME_DEPTNAME));//new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_DEPTNAME).getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(8, getDbString(hisResultSet, UserInfoBean.COLUMN_NAME_USERROLE));//new String(hisResultSet.getString(UserInfoBean.COLUMN_NAME_USERROLE).getBytes("iso-8859-1"), "gbk"));
                        localstmt.execute();
                        localstmt.clearBindings();
                        intAppUserRowcount++;
                    }
                    Log.d(TAG, "subscribe:getUserInfo " + intAppUserRowcount);
                    localdb.setTransactionSuccessful();
                    hisResultSet.close();
                    hisResultSet = null;
                    observer.onNext(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:SQLException\n" + ex.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:Exception\n" + ex.getMessage()));
                } finally {
                    localdb.endTransaction();
                    observer.onComplete();
                    EventBusUtils.postSticky(new EventBusMessage<>("syncgetUserInfo", "同步用户信息完成"));
                }
            }
        };
    }

    /**
     * 获取病人基本信息     */
    public Observable<Boolean> getPatsInfo() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                try {
                    intPatInfoRowcount = 0;
                    /**获取病人信息写入本地表*/
                    /**打开本地数据库*/
                    EventBusUtils.postSticky(new EventBusMessage<>("syncgetPatsInfo", "正在同步病人信息，请稍等... ..."));
                    intPatInfoRowcount = 0;
                    LocalDbHelper localDbHelper = LocalDbHelper.getInstance(MyApplication.getInstance());
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localdb.execSQL(AppSql.sqlDeletePatInfo);
                    localdb.setTransactionSuccessful();
                    localdb.endTransaction();
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localstmt = localdb.compileStatement(AppSql.sqlInsertPatInfo);
                    Log.d(TAG, "getPatsInfo: " + AppCookie.userinfo.getWardcode());
                    ResultSet hisResultSet = HisdbHelper.getInstance().executePLSQL("pck_hcis.getPatsInfo(?,?)", AppCookie.userinfo.getWardcode());
                    while (hisResultSet.next()) {
                        localstmt.clearBindings();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                        localstmt.bindString(1, getDbString(hisResultSet, "inp_no"));// new String(hisResultSet.getString("inp_no").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(2, getDbString(hisResultSet, "patient_id"));// new String(hisResultSet.getString("patient_id").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindLong(3, hisResultSet.getInt("visit_id"));
                        localstmt.bindString(4, getDbString(hisResultSet, "name"));// new String(hisResultSet.getString("name").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(5, getDbString(hisResultSet, "sex"));// new String(hisResultSet.getString("sex").getBytes("iso-8859-1"), "gbk"));

                        if (!StringUtils.isObjectNull(hisResultSet.getString("nation"))) {
                            localstmt.bindString(6, getDbString(hisResultSet, "nation"));//new String(hisResultSet.getString("nation").getBytes("iso-8859-1"), "gbk"));
                        }

                        if (!StringUtils.isObjectNull(hisResultSet.getTimestamp("birth_date"))) {
                            localstmt.bindString(7, sdf.format(hisResultSet.getTimestamp("birth_date")));
                        }


                        if (!StringUtils.isObjectNull(hisResultSet.getString("charge_type"))) {
                            localstmt.bindString(8, getDbString(hisResultSet, "charge_type"));// new String(hisResultSet.getString("charge_type").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("identity"))) {
                            localstmt.bindString(9, getDbString(hisResultSet, "identity"));// new String(hisResultSet.getString("identity").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("diagnosis"))) {
                            localstmt.bindString(10, getDbString(hisResultSet, "diagnosis"));// new String(hisResultSet.getString("diagnosis").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getTimestamp("admission_date_time"))) {
                            localstmt.bindString(11, sdf.format(hisResultSet.getTimestamp("admission_date_time")));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getTimestamp("operating_date"))) {
                            localstmt.bindString(12, sdf.format(hisResultSet.getTimestamp("operating_date")));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("bed_approved_type"))) {
                            localstmt.bindString(13, getDbString(hisResultSet, "bed_approved_type"));//hisResultSet.getString("bed_approved_type"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("room_no"))) {
                            localstmt.bindString(14, getDbString(hisResultSet, "room_no"));//hisResultSet.getString("room_no"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("bed_no"))) {
                            localstmt.bindString(15, getDbString(hisResultSet, "bed_no"));//hisResultSet.getString("bed_no"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("body_weight"))) {
                            localstmt.bindString(16, getDbString(hisResultSet, "body_weight"));//hisResultSet.getString("body_weight"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("body_height"))) {
                            localstmt.bindString(17, getDbString(hisResultSet, "body_height"));//hisResultSet.getString("body_height"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("blood_type"))) {
                            localstmt.bindString(18, getDbString(hisResultSet, "blood_type"));//hisResultSet.getString("blood_type"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("dept_code"))) {
                            localstmt.bindString(19, getDbString(hisResultSet, "dept_code"));//new String(hisResultSet.getString("dept_code").getBytes("iso-8859-1"), "gbk"));
                        }
                        localstmt.bindString(20, getDbString(hisResultSet, "dept_name"));//new String(hisResultSet.getString("dept_name").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(21, getDbString(hisResultSet, "ward_code"));//new String(hisResultSet.getString("ward_code").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(22, getDbString(hisResultSet, "ward_name"));//new String(hisResultSet.getString("ward_name").getBytes("iso-8859-1"), "gbk"));
                        if (!StringUtils.isObjectNull(hisResultSet.getString("patient_class"))) {
                            localstmt.bindString(23, getDbString(hisResultSet, "patient_class"));//new String(hisResultSet.getString("patient_class").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("patient_cond"))) {
                            localstmt.bindString(24, getDbString(hisResultSet, "patient_cond"));//new String(hisResultSet.getString("patient_cond").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("doctor_name"))) {
                            localstmt.bindString(25, getDbString(hisResultSet, "doctor_name"));//new String(hisResultSet.getString("doctor_name").getBytes("iso-8859-1"), "gbk"));
                        }
                        //头像处理
                        byte[] bytes = hisResultSet.getBytes("patient_image");
                        if (bytes != null) {
                            String filename = AppCookie.appsetting.getdirPatImg() + hisResultSet.getString("inp_no");
                            Boolean ibFile = FileUtils.saveFile(bytes, AppCookie.appsetting.getdirPatImg(), hisResultSet.getString("inp_no"), ".bmp");
                            if (ibFile) {
                                localstmt.bindString(26, filename + ".bmp");
                            }
                        }
                        bytes = null;

                        /**过敏药物*/
                        if (!StringUtils.isObjectNull(hisResultSet.getString("alergy_drugs"))) {
                            localstmt.bindString(27, getDbString(hisResultSet, "alergy_drugs"));//new String(hisResultSet.getString("alergy_drugs").getBytes("iso-8859-1"), "gbk"));
                        }
                        /**预出院日期*/
                        if (!StringUtils.isObjectNull(hisResultSet.getString("predischarge_date"))) {
                            localstmt.bindString(28, getDbString(hisResultSet, "predischarge_date"));//new String(hisResultSet.getString("predischarge_date").getBytes("iso-8859-1"), "gbk"));
                        }


                        if (!StringUtils.isObjectNull(hisResultSet.getString("mailing_address"))) {
                            localstmt.bindString(29, getDbString(hisResultSet, "mailing_address"));//new String(hisResultSet.getString("mailing_address").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("next_of_kin"))) {
                            localstmt.bindString(30, getDbString(hisResultSet, "next_of_kin"));//new String(hisResultSet.getString("next_of_kin").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("next_of_kin_phone"))) {
                            localstmt.bindString(31, getDbString(hisResultSet, "next_of_kin_phone"));//new String(hisResultSet.getString("next_of_kin_phone").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("phone_number_home"))) {
                            localstmt.bindString(32, getDbString(hisResultSet, "phone_number_home"));//new String(hisResultSet.getString("phone_number_home").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("phone_number_business"))) {
                            localstmt.bindString(33, getDbString(hisResultSet, "phone_number_business"));//new String(hisResultSet.getString("phone_number_business").getBytes("iso-8859-1"), "gbk"));
                        }
                        localstmt.bindDouble(34, hisResultSet.getDouble("prepayments"));
                        localstmt.bindDouble(35, hisResultSet.getDouble("total_costs"));
                        localstmt.bindLong(36, hisResultSet.getInt("settled_flag"));

                        localstmt.execute();
                        intPatInfoRowcount++;
                    }
                    Log.d(TAG, "subscribeActual: getPatsInfo" + AppCookie.userinfo.getWardcode() + intPatInfoRowcount);
                    localdb.setTransactionSuccessful();
                    hisResultSet.close();
                    hisResultSet = null;
                    observer.onNext(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:SQLException " + ex.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:Exception " + ex.getMessage()));
                } finally {
                    EventBusUtils.postSticky(new EventBusMessage<>("syncgetPatsInfo", "同步病人信息完成"));
                    localdb.endTransaction();
                    observer.onComplete();
                }
            }
        };
    }

    /**
     * 获取病人医嘱
     */
    public Observable<Boolean> getPatsOrder() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                try {
                    intPatOrderRowcount = 0;
                    EventBusUtils.postSticky(new EventBusMessage<>("syncgetPatsOrder", "正在同步病人医嘱信息，请稍等... ..."));
                    /**获取病人信息写入本地表  打开本地数据库*/
                    intPatInfoRowcount = 0;
                    LocalDbHelper localDbHelper = LocalDbHelper.getInstance(MyApplication.getInstance());
                    /**打开本地数据库*/
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localdb.execSQL(AppSql.sqlDeletePatOrder);
                    localdb.setTransactionSuccessful();
                    localdb.endTransaction();
                    localdb = localDbHelper.getWritableDatabase();
                    localdb.beginTransaction();
                    localstmt = localdb.compileStatement(AppSql.sqlInsertPatOrder);
                    Log.d(TAG, "subscribeActual: "+ MyUtils.getHisXmlPara(AppCookie.userinfo.getWardcode(), "WARDCODE"));
                    ResultSet hisResultSet = HisdbHelper.getInstance().executePLSQL("pck_hcis.getPatOrders(?,?)", MyUtils.getHisXmlPara("WARDCODE",AppCookie.userinfo.getWardcode()));
                    while (hisResultSet.next()) {
                        localstmt.clearBindings();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                        localstmt.bindString(1, getDbString(hisResultSet, "index_id"));// new String(hisResultSet.getString("index_id").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindString(2, getDbString(hisResultSet, "patient_id"));//  new String(hisResultSet.getString("patient_id").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindLong(3, hisResultSet.getInt("visit_id"));
                        localstmt.bindString(4, getDbString(hisResultSet, "ordering_dept"));//new String(hisResultSet.getString("ordering_dept").getBytes("iso-8859-1"), "gbk"));
                        localstmt.bindLong(5, hisResultSet.getInt("order_no"));
                        localstmt.bindLong(6, hisResultSet.getInt("repeat_indicator"));
                        if (!StringUtils.isObjectNull(hisResultSet.getTimestamp("start_date_time"))) {
                            localstmt.bindString(7, sdf.format(hisResultSet.getTimestamp("start_date_time")));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getTimestamp("stop_date_time"))) {
                            localstmt.bindString(8, sdf.format(hisResultSet.getTimestamp("stop_date_time")));
                        }
                        if (!StringUtils.isObjectNull(getDbString(hisResultSet, "ordering_dept"))) {
                            ;//hisResultSet.getString("freq_detail")
                            localstmt.bindString(9, getDbString(hisResultSet, "ordering_dept"));//new String(hisResultSet.getString("freq_detail").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("default_schedule"))) {
                            localstmt.bindString(10, new String(hisResultSet.getString("default_schedule").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("new_default_schedule"))) {
                            localstmt.bindString(11, new String(hisResultSet.getString("new_default_schedule").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getTimestamp("plan_exec_datetime"))) {
                            localstmt.bindString(12, sdf.format(hisResultSet.getTimestamp("plan_exec_datetime")));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("lab_exam_class"))) {
                            localstmt.bindString(13, new String(hisResultSet.getString("lab_exam_class").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getInt("IS_EMER"))) {
                            localstmt.bindLong(14, hisResultSet.getInt("IS_EMER"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("ajust_memo"))) {
                            localstmt.bindString(15, new String(hisResultSet.getString("ajust_memo").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("order_class"))) {
                            localstmt.bindString(16, new String(hisResultSet.getString("order_class").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("order_text"))) {
                            localstmt.bindString(17, new String(hisResultSet.getString("order_text").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("administration"))) {
                            localstmt.bindString(18, new String(hisResultSet.getString("administration").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("frequency"))) {
                            localstmt.bindString(19, new String(hisResultSet.getString("frequency").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("freq_counter"))) {
                            localstmt.bindString(20, new String(hisResultSet.getString("freq_counter").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getInt("freq_interval"))) {
                            localstmt.bindLong(21, hisResultSet.getInt("freq_interval"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("freq_interval_unit"))) {
                            localstmt.bindString(22, new String(hisResultSet.getString("freq_interval_unit").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getInt("order_status"))) {
                            localstmt.bindLong(23, hisResultSet.getInt("order_status"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("doctor"))) {
                            localstmt.bindString(24, new String(hisResultSet.getString("doctor").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("nurse"))) {
                            localstmt.bindString(25, new String(hisResultSet.getString("nurse").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("stop_info"))) {
                            localstmt.bindString(26, new String(hisResultSet.getString("stop_info").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("relative_no"))) {
                            localstmt.bindString(27, new String(hisResultSet.getString("relative_no").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("order_type"))) {
                            localstmt.bindString(28, new String(hisResultSet.getString("order_type").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getTimestamp("exec_datetime"))) {
                            localstmt.bindString(29, sdf.format(hisResultSet.getTimestamp("exec_datetime")));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getString("exec_operator"))) {
                            localstmt.bindString(30, new String(hisResultSet.getString("exec_operator").getBytes("iso-8859-1"), "gbk"));
                        }
                        if (!StringUtils.isObjectNull(hisResultSet.getInt("sync_status"))) {
                            localstmt.bindLong(31, hisResultSet.getInt("sync_status"));
                        }
                        localstmt.execute();
                        intPatOrderRowcount++;
                    }
                    Log.d(TAG, "subscribeActual: getPatsOrder" + AppCookie.userinfo.getWardcode() + intPatOrderRowcount);
                    localdb.setTransactionSuccessful();
                    hisResultSet.close();
                    observer.onNext(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:SQLException\n" + ex.getMessage()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    observer.onError(new Throwable("同步数据出错\n错误信息:Exception\n" + ex.getMessage()));
                } finally {
                    EventBusUtils.postSticky(new EventBusMessage<>("syncgetPatsOrder", "同步病人医嘱完成"));
                    localdb.endTransaction();
                    observer.onComplete();
                }
            }
        };
    }

    @SuppressLint("CheckResult")
    public Observable<Boolean> SyncData() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                Observable<Boolean> userinfo = SyncDataHelper.getInstance().getUserInfo();
                Observable<Boolean> patsinfo = SyncDataHelper.getInstance().getPatsInfo();
                Observable<Boolean> patsorder = SyncDataHelper.getInstance().getPatsOrder();


                Observable.zip(userinfo, patsinfo, patsorder, (Function3<Boolean, Boolean, Boolean, Object>) (aBoolean, aBoolean2, aBoolean3) -> aBoolean && aBoolean2 && aBoolean3)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                            Log.d(TAG, "subscribeActual: " + o.toString());
                            if (o instanceof Boolean) {
                                if ((Boolean) o == true) {
                                    observer.onNext(true);
                                    observer.onComplete();
                                } else {
                                    observer.onNext(false);
                                    observer.onComplete();
                                }
                            }
                        }, throwable -> {
                            observer.onError(new Throwable(throwable.getMessage()));
                            observer.onComplete();
                        });
            }
        };
    }


}
