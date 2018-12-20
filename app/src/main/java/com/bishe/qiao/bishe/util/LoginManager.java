package com.bishe.qiao.bishe.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginManager {
    private static String token = null;
    static{
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        sp.edit()
                .putString("username", null)
                .putString("password", "")
                .apply();
        token = sp.getString("token", null);
    }

    public static Boolean isLogin() {
        return token != null;
    }


    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        LoginManager.token = token;
    }
}
