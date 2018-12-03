package com.bishe.qiao.bishe.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.util.SharedPreferencesUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginFragment extends Fragment {
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
    }
    private void showResponseData(final String response){

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.login_register_fragment, fragment);
        transaction.commit();
    }
    private void login(final String userName, final String password){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://120.79.59.91:8080")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            Log.d("MMMMMMMMMMM", "出现异常");
        }
        String responseDate = null;
        try {
            responseDate = response.body().string();
        } catch (IOException e) {
            Log.d("MMMMM", "IOException");
        }
        Log.d("MMMMM", responseDate);
        JSONObject jobj = JSON.parseObject(responseDate);
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("token", jobj.getString("token"));
        editor.apply();
        new SharedPreferencesUtil(getActivity(), "data").putValues(new SharedPreferencesUtil.ContentValue("token", "123"));
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
