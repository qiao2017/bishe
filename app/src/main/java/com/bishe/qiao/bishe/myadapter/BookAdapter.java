package com.bishe.qiao.bishe.myadapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.activity.BookDetailActivity;
import com.bishe.qiao.bishe.model.ListBook;
import com.bishe.qiao.bishe.util.Status;
import com.bishe.qiao.bishe.util.Util;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bishe.qiao.bishe.util.MyApplication.getContext;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context mContext;
    private List<ListBook> bookList;
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
                case 0://请求成功
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra("res", res);
                    mContext.startActivity(intent);
                    break;
                default:
                    Toast.makeText(getContext(), "出现未知错误！请检查后重试", Toast.LENGTH_SHORT).show();
            }

        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView bookImage;
        TextView bookName;
        AppCompatRatingBar ratingBar;
        TextView score;
        TextView bookInfo;
        TextView bookDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            bookImage = itemView.findViewById(R.id.iv_book_img);
            bookName = itemView.findViewById(R.id.tv_book_title);
            ratingBar = itemView.findViewById(R.id.ratingBar_hots);
            score = itemView.findViewById(R.id.tv_hots_num);
            bookInfo = itemView.findViewById(R.id.tv_book_info);
            bookDescription = itemView.findViewById(R.id.tv_book_description);
        }

    }

    public BookAdapter(List<ListBook> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        ListBook book = bookList.get(i);
        viewHolder.bookName.setText(book.getBookName());
        Glide.with(mContext).load(book.getBookHeadUrl()).into(viewHolder.bookImage);
        viewHolder.ratingBar.setRating(Float.parseFloat(book.getBookScore()));
        viewHolder.score.setText(book.getBookScore());
        viewHolder.bookDescription.setText(book.getContent());
        viewHolder.bookInfo.setText(book.getAuthor());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        OkHttpClient client = new OkHttpClient.Builder()
                                .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("bookId", String.valueOf(bookList.get(position).getBookId()))
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
                            msg.what = 0;
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
        return bookList.size();
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
