package com.haha.hahaforhaha.utils;

import android.util.Base64;

public class Base64Utils {

//    String str = "Hello!";
//    //base64编码
//    //String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
//    String strBase64 = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
//    //base64解码
//    String str2 = new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT));


    /*** base64 加码*/
    public static String base64Encode(String str) {
//        byte[] strByte = android.util.Base64.decode(str, android.util.Base64.DEFAULT);
        return android.util.Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
    }


    /*** base64 解码*/
    public static String base64Decode(String str) {
//        byte[] strByte = android.util.Base64.encode(str.getBytes(), android.util.Base64.DEFAULT);
        return new String(Base64.decode(str.getBytes(), Base64.DEFAULT));
    }

}
