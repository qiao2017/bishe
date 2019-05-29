package com.bishe.qiao.bishe.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.bishe.qiao.bishe.util.Status;
import com.bishe.qiao.bishe.util.Util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFragment extends Fragment {
    public ProgressDialog progressDialog;
    private TextView userName;
    private TextView password;
    private Button button;
    String userNameTxt;
    String passwordTxt;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = getActivity().findViewById(R.id.register_fragment_button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showProgressDialog();
                login();
            }
        });
        userName = getActivity().findViewById(R.id.fragment_register_user_name);
        password = getActivity().findViewById(R.id.fragment_register_password);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            closeProgressDialog();
            Bundle data = msg.getData();
            String res = data.getString("res", null);
            switch(msg.what){
                case 100:
                    Toast.makeText(getContext(), "网络错误！请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
                case 0://登陆成功
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("res", res);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                case 1:
                    JSONObject jobj = JSON.parseObject(res);
                    Toast.makeText(getContext(), jobj.getString("error") + "！请检查后重试", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    private void login(){
        userNameTxt = userName.getText().toString().trim();
        passwordTxt = password.getText().toString().trim();
        if(StringUtils.isEmpty(userNameTxt) || StringUtils.isEmpty(passwordTxt)){
            Toast.makeText(getContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            closeProgressDialog();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("userName", userNameTxt)
                        .add("password", passwordTxt)
                        .build();
                Request request = new Request.Builder()
                        .url(Util.baseUrl + "register")
                        .post(requestBody)
                        .build();
                Response response;
                String responseDate;
                try {
                    response = client.newCall(request).execute();
                    responseDate = response.body().string();
                    JSONObject res = JSON.parseObject(responseDate);
                    String status = res.getString("status");
                    Message msg = new Message();
                    if("0".equals(status)){
                        //注册成功
                        msg.what = 0;
                        String token = res.getString("token");
                        Status.token = token;
                    }else{
                        msg.what = 1;
                    }
                    Bundle data = new Bundle();
                    data.putString("res",responseDate);
                    msg.setData(data);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    Message msg = new Message();
                    msg.what = 100;
                    handler.sendMessage(msg);
                }

            }
        }).start();
    }
    protected void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("正在加载···");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    protected void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}