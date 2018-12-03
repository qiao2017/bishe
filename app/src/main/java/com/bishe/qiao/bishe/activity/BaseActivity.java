package com.bishe.qiao.bishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //查看是否登录
/*        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);*/
        Log.d("BaseActivity", getClass().getSimpleName());
    }
}
