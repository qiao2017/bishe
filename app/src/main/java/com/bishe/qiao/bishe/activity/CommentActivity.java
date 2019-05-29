package com.bishe.qiao.bishe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.util.Status;
import com.bishe.qiao.bishe.util.Util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentActivity extends BaseActivity {
    public static int bookId;
    public static final int UPDATE_TEXT_0 = 0;
    public static final int UPDATE_TEXT_1 = 1;
    public static final int UPDATE_TEXT_2 = 2;
    public static final int UPDATE_TEXT_3 = 3;
    public static final int UPDATE_TEXT_4 = 4;
    public static final int UPDATE_TEXT_5 = 5;
    RatingBar ratingBar;
    EditText editText;
    TextView textView;
    Button button;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TEXT_0:
                    textView.setText("非常差");
                    break;
                case UPDATE_TEXT_1:
                    textView.setText("差");
                    break;
                case UPDATE_TEXT_2:
                    textView.setText("较差");
                    break;
                case UPDATE_TEXT_3:
                    textView.setText("一般");
                    break;
                case UPDATE_TEXT_4:
                    textView.setText("好");
                    break;
                case UPDATE_TEXT_5:
                    textView.setText("非常好");
                    break;
                case 100:
                    closeProgressDialog();
                    finish();
                case 999:
                    closeProgressDialog();
                    Bundle data = msg.getData();
                    String res = data.getString("res", null);
                    Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        bookId = Integer.valueOf(intent.getStringExtra("bookId"));
        ratingBar = findViewById(R.id.comment_rating_bar);
        textView = findViewById(R.id.comment_rating_bar_text);
        editText  = findViewById(R.id.comment_edit_text);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, float rating, boolean fromUser) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int score = (int)ratingBar.getRating();
                        Message message = new Message();
                        message.what = score;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        button = findViewById(R.id.activity_add_comment_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String text = editText.getText().toString();
                        Log.e("NNNNNNNN", text);
                        try {
                            OkHttpClient client = new OkHttpClient.Builder()
                                    .readTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                    .writeTimeout(5 * 60 * 1000, TimeUnit.SECONDS)
                                    .connectTimeout(5 * 60 * 1000, TimeUnit.SECONDS).build();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("bookId", String.valueOf(bookId))
                                    .add("content", text)
                                    .add("stars", String.valueOf((int) ratingBar.getRating()))
                                    .add("token", Status.token)
                                    .build();
                            Request request = new Request.Builder()
                                    .url(Util.baseUrl + "addComment")
                                    .post(requestBody)
                                    .build();
                            Response response;
                            Message message = new Message();
                            Bundle data = new Bundle();
                            String responseDate;
                            try {
                                response = client.newCall(request).execute();
                                responseDate = response.body().string();
                                message.what = 999;
                                data.putString("res",responseDate);
                                message.setData(data);
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                message.what = 100;
                                handler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            Message message = new Message();
                            message.what = 100;
                            handler.sendMessage(message);}
                    }
                }).start();
            }
        });
    }

    protected void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("评论提交中···");
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
