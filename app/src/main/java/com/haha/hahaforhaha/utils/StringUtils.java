package com.haha.hahaforhaha.utils;

public class StringUtils {

    public static Boolean isStringEmpty(String strString){
        if (strString.equals("") || strString==null){
            return true;
        }else{
            return false;
        }
    }

    /***判断是否为null*/
    public static boolean isObjectNull(Object o) {
        return o == null;
    }

}
