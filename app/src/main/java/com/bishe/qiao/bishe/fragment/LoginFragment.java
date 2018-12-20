package com.bishe.qiao.bishe.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.activity.MainActivity;
import com.bishe.qiao.bishe.util.MyApplication;
import com.bishe.qiao.bishe.util.SharedPreferencesUtil;
import com.bishe.qiao.bishe.util.Status;
import com.bishe.qiao.bishe.util.Util;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginFragment extends Fragment {
    private TextView userName;
    private TextView password;
    private Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = getActivity().findViewById(R.id.fragment_login_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RegisterFragment fragment = new RegisterFragment();
//                FrameLayout frameLayout = getActivity().findViewById(R.id.login_register_fragment);
                replaceFragment(fragment);
/*
                Toast.makeText(getActivity(), "登录", Toast.LENGTH_SHORT).show();
                *//*Intent intent = new Intent(getActivity(), LoginRegisterActivity.class);
                startActivity(intent);*//*
*//*                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                }else{
                    Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
                }*//*
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                login("111", "111");
                            }
                        }
                ).start();*/
            }
        });

        userName = getActivity().findViewById(R.id.fragment_login_user_name);
        password = getActivity().findViewById(R.id.fragment_login_password);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.login_register_fragment, fragment);
        transaction.commit();
    }
    private void login(){
        String userNameTxt = userName.getText().toString().trim();
        String passwordTxt = password.getText().toString().trim();
//        if(StringUtils.isEmpty("") || )
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("userName", userNameTxt)
                .add("password", passwordTxt)
                .build();
        Request request = new Request.Builder()
                .url(Util.baseUrl + "login")
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {

        }
        String responseDate = null;
        try {
            responseDate = response.body().string();
        } catch (IOException e) {
            Log.d("MMMMM", "IOException");
        }
        JSONObject res = JSON.parseObject(responseDate);
/*        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("token", jobj.getString("token"));
        editor.apply();*/
        String status = res.getString("status");
        if(Status.OK.equals(status)){
            new SharedPreferencesUtil(getActivity(), "data").putValues(new SharedPreferencesUtil.ContentValue("token", res.get("token")));
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            startActivity(intent);
        }else{
            //TODO
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity(), "权限申请成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "权限申请失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
