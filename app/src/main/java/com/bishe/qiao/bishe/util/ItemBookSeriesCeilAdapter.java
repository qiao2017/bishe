package com.bishe.qiao.bishe.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.activity.BookDetailActivity;
import com.bishe.qiao.bishe.model.BookSeriesCeil;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bishe.qiao.bishe.util.MyApplication.getContext;

public class ItemBookSeriesCeilAdapter extends RecyclerView.Adapter<ItemBookSeriesCeilAdapter.ViewHolder>{
    private Context mContext;
    List<BookSeriesCeil> bookSeriesCeils;
    private ProgressDialog progressDialog;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            closeProgressDialog();
            Bundle data = msg.getData();
            String res = data.getString("res", null);
            switch (msg.what) {
                case 100:
                    Toast.makeText(getContext(), "网络错误！请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
                case 123://请求成功
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra("res", res);
                    mContext.startActivity(intent);
                    break;
                default:
                    Toast.makeText(getContext(), "出现未知错误！请检查后重试", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book_series_ceil, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BookSeriesCeil bookSeriesCeil = bookSeriesCeils.get(i);
        Glide.with(MyApplication.getContext()).load(bookSeriesCeil.getBookUrl()).into(viewHolder.bookImg);
        viewHolder.bookName.setText(bookSeriesCeil.getBookName());
        try{
            viewHolder.ratingBarScore.setRating(Float.parseFloat(bookSeriesCeil.getScore()));
        }catch (Exception e){
            viewHolder.ratingBarScore.setRating(Float.parseFloat("1"));
        }
        viewHolder.score.setText(bookSeriesCeil.getScore());
        viewHolder.bookImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(), bookSeriesCeil.getBookId(), Toast.LENGTH_SHORT).show();


                showProgressDialog();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient.Builder()
                                .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("bookId", String.valueOf(bookSeriesCeil.getBookId()))
                                .add("token", Status.token)
                                .build();
                        Request request = new Request.Builder()
                                .url(Util.baseUrl + "bookDetail")
                                .post(requestBody)
                                .build();
                        Response response;
                        String responseDate;
                        Message msg = new Message();
                        try {
                            response = client.newCall(request).execute();
                            responseDate = response.body().string();
                            msg.what = 123;
                            Bundle data = new Bundle();
                            data.putString("res",responseDate);
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }catch (Exception e){
                            msg.what = 100;
                            handler.sendMessage(msg);
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookSeriesCeils.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bookImg;
        TextView bookName;
        AppCompatRatingBar ratingBarScore;
        TextView score;
        public ViewHolder(View view){
            super(view);
            bookImg = view.findViewById(R.id.iv_book_img);
            bookName = view.findViewById(R.id.tv_title);
            ratingBarScore = view.findViewById(R.id.ratingBar_hots);
            score = view.findViewById(R.id.tv_hots_num);
        }
    }
    public ItemBookSeriesCeilAdapter(List<BookSeriesCeil> bookComments){
        this.bookSeriesCeils = bookComments;
    }

    protected void showProgressDialog() {
        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(getContext());
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("正在加载···");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    protected void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }
}
