package com.bishe.qiao.bishe.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bishe.qiao.bishe.util.MyApplication;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //查看是否登录
/*        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);*/
        Log.d("BaseActivity", getClass().getSimpleName());
    }
    public void startActivityAfterLogin(Intent intent) {
        //未登录（这里用自己的登录逻辑去判断是否未登录）
        if (true) {
            ComponentName componentName = new ComponentName(MyApplication.getContext(), LoginRegisterActivity.class);
            intent.putExtra("className", intent.getComponent().getClassName());
            intent.setComponent(componentName);
            super.startActivity(intent);
        } else {
            super.startActivity(intent);
        }
    }


}
