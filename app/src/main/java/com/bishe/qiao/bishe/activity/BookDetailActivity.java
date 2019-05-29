package com.bishe.qiao.bishe.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.BookComment;
import com.bishe.qiao.bishe.model.BookSeriesCeil;
import com.bishe.qiao.bishe.myadapter.ItemEbookCommentAdapter;
import com.bishe.qiao.bishe.util.BlurTransformation;
import com.bishe.qiao.bishe.util.ItemBookSeriesCeilAdapter;
import com.bishe.qiao.bishe.util.MyApplication;
import com.bishe.qiao.bishe.util.Status;
import com.bishe.qiao.bishe.util.Util;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class BookDetailActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private boolean isFavorite;
    private boolean hasMore;
    public static String res;
    ImageView bookImgBig;
    ImageView bookImgSmall;
    Toolbar toolbar;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RatingBar bookRatingBar;
    TextView scoreTextView;
    TextView scoreNumTextView;
    TextView bookInfoTextView;
    TextView contentTextView;
    TextView priceTextView;
    FloatingActionButton fab;
    TextView bookCommentLookMore;
    TextView addComment;
    int bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_detail);
        Intent intent = getIntent();
        res = intent.getStringExtra("res");
        initEvents();
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
                    Toast.makeText(BookDetailActivity.this, "网络错误！请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
                case 0://请求成功
                    Intent intent = new Intent(BookDetailActivity.this, BookCommentAllActivity.class);
                    intent.putExtra("res", res);
                    startActivity(intent);
                    break;
            }

        }
    };

    protected void initEvents() {
        bookImgBig = findViewById(R.id.iv_book_bg);
        bookImgSmall = findViewById(R.id.iv_book_img);
        addComment = findViewById(R.id.book_comment_add_comment);
        JSONObject responseDate = JSONObject.parseObject(res);
        isFavorite = Boolean.parseBoolean(responseDate.getString("isFavorite"));
        JSONObject bookInfo = responseDate.getJSONObject("bookInfo");

        bookRatingBar = findViewById(R.id.ratingBar_hots);
        String avgScore = bookInfo.getString("avgScore") == null ? "3.2" : bookInfo.getString("avgScore");
        bookRatingBar.setRating(Float.valueOf(avgScore));
        scoreTextView = findViewById(R.id.tv_hots_num);
        scoreTextView.setText(bookInfo.getString("avgScore"));
        scoreNumTextView = findViewById(R.id.tv_words_num);
        scoreNumTextView.setText(bookInfo.getString("scoreNumber") + " 人评分");
        bookInfoTextView = findViewById(R.id.tv_book_info);
        bookInfoTextView.setText(bookInfo.getString("author") + "/" + bookInfo.getString("publishingHouse"));
        contentTextView = findViewById(R.id.book_detail_brief);
        contentTextView.setText(bookInfo.getString("content"));
        priceTextView = findViewById(R.id.book_detail_price);
        priceTextView.setText(bookInfo.getString("price"));
        bookId = bookInfo.getInteger("bookId");
        addComment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, CommentActivity.class);
                intent.putExtra("bookId", String.valueOf(bookId));
                startActivity(intent);
            }
        });
        bookCommentLookMore = findViewById(R.id.book_comment_look_more);
        bookCommentLookMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showProgressDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient.Builder()
                                .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("token", Status.token)
                                .add("bookId", String.valueOf(bookId))
                                .build();
                        Request request = new Request.Builder()
                                .url(Util.baseUrl + "allComment")
                                .post(requestBody)
                                .build();
                        Response response;
                        String responseDate;
                        try {
                            response = client.newCall(request).execute();
                            responseDate = response.body().string();
                            Message msg = new Message();
                            msg.what = 0;

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
        });





        String bookUrl = bookInfo.getString("profileUrl");
        Glide.with(this)
                .load(bookUrl)
                .apply(bitmapTransform(new BlurTransformation(this, 25, 3)))
                .into(bookImgBig);
        Glide.with(this)
                .load(bookUrl)
                .into(bookImgSmall);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(bookInfo.getString("bookName"));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView1 = findViewById(R.id.book_detail_recycler_1);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(BookDetailActivity.this);
        mLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(mLayoutManager1);
        ItemEbookCommentAdapter adapter1 = new ItemEbookCommentAdapter(getBookComment(responseDate.getJSONObject("bookComments").getJSONArray("comments")));
        hasMore = Boolean.parseBoolean(responseDate.getJSONObject("bookComments").getString("hasMore"));
        recyclerView1.setAdapter(adapter1);

        recyclerView2 = findViewById(R.id.book_detail_recycler_2);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(BookDetailActivity.this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(mLayoutManager2);
        ItemBookSeriesCeilAdapter adapter2 = new ItemBookSeriesCeilAdapter(getBookSeriesCeil(responseDate.getJSONArray("recomment")));
        recyclerView2.setAdapter(adapter2);

        fab = findViewById(R.id.book_detail_fab_fab);
        if(isFavorite){
            fab.setImageResource(R.drawable.star);
        }else{
            fab.setImageResource(R.drawable.star_empty);
        }
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                String message = isFavorite ? "已收藏" : "已取消收藏";
                Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT).show();
                if (isFavorite){
                    fab.setImageResource(R.drawable.star);
                }else {
                    fab.setImageResource(R.drawable.star_empty);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient.Builder()
                                .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("token", Status.token)
                                .add("bookId", String.valueOf(bookId))
                                .build();
                        Request request = new Request.Builder()
                                .url(Util.baseUrl + "favorite")
                                .post(requestBody)
                                .build();
                        try {
                            client.newCall(request).execute();
                        } catch (IOException e) {
                        }
                    }
                }).start();
            }
        });
    }

    private List<BookSeriesCeil> getBookSeriesCeil(JSONArray recomment){
        List<BookSeriesCeil> bookSeriesCeils = new ArrayList<>();
        for (int i = 0; i<recomment.size(); i++){
            JSONObject jobj = recomment.getJSONObject(i);
            BookSeriesCeil bookSeriesCeil = new BookSeriesCeil();
            bookSeriesCeil.setBookId(String.valueOf(jobj.getInteger("bookId")));
            bookSeriesCeil.setBookName(jobj.getString("bookName"));
            bookSeriesCeil.setBookUrl(jobj.getString("profileUrl"));
            bookSeriesCeil.setScore(jobj.getString("avgScore"));
            bookSeriesCeils.add(bookSeriesCeil);
        }
        return bookSeriesCeils;
    }
    private List<BookComment> getBookComment(JSONArray comments){
        List<BookComment> bookComments = new ArrayList<>();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < comments.size(); i++){
            JSONObject jobj = comments.getJSONObject(i);
            BookComment bookComment = new BookComment();
            bookComment.setUserHead(jobj.getString("headUrl"));
            bookComment.setUserName(jobj.getString("nickName"));
            bookComment.setScore(jobj.getString("stars"));
            bookComment.setContent(jobj.getString("content"));
            Long time = jobj.getLong("commentTime");
            Date date;
            if(time == null){
                date = new Date();
            }else{
                date = new Date(time);
            }
            bookComment.setTime(df.format(date));
            bookComments.add(bookComment);
        }
        return  bookComments;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(BookDetailActivity.this);
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
