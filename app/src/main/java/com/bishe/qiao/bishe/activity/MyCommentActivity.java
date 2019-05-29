package com.bishe.qiao.bishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.MyComment;
import com.bishe.qiao.bishe.myadapter.ItemMyCommentAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCommentActivity extends BaseActivity {
    public static String res;
    private List<MyComment> myComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        Intent intent = getIntent();
        res = intent.getStringExtra("res");
        initMyComments();
        RecyclerView recyclerView = findViewById(R.id.activity_my_comment_recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        ItemMyCommentAdapter adapter = new ItemMyCommentAdapter(myComments);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.my_comment_toolbar);
        //设置toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true); //设置返回键可用
            actionBar.setTitle("我的评论");
        }
    }

    private void initMyComments(){
        JSONArray jarr = JSONObject.parseObject(res).getJSONArray("comments");
        myComments = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(int i =0; i < jarr.size(); i++){
            MyComment myComment = new MyComment();
            JSONObject jobj = jarr.getJSONObject(i);
            Date date = new Date(jobj.getLong("commentTime"));
            myComment.setStars(Integer.valueOf(jobj.getString("stars")));
            myComment.setAuthor(jobj.getString("author"));
            myComment.setBookName(jobj.getString("bookName"));
            myComment.setBookUrl(jobj.getString("profileUrl"));
            myComment.setCommentTime(simpleDateFormat.format(date));
            myComment.setContent(jobj.getString("content"));
            myComment.setPublish(jobj.getString("publishingHouse"));
            myComments.add(myComment);
        }
    }
}
