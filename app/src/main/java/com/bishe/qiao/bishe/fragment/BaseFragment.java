package com.bishe.qiao.bishe.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.bishe.qiao.bishe.util.MyApplication;

public class BaseFragment extends Fragment{
    public ProgressDialog progressDialog;
    protected void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(MyApplication.getContext());
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
