package com.bishe.qiao.bishe.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.MyComment;
import com.bishe.qiao.bishe.myadapter.ItemMyCommentAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCommentActivity extends BaseActivity {
    private List<MyComment> myComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        initMyComments();
        Log.d("MMMMMMMMMMM", "33333");
        RecyclerView recyclerView = findViewById(R.id.activity_my_comment_recycler_view);
        Log.d("MMMMMMMMMMM", "444444");
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        Log.d("MMMMMMMMMMM", "55555");
        recyclerView.setLayoutManager(manager);
        Log.d("MMMMMMMMMMM", "666666");
        ItemMyCommentAdapter adapter = new ItemMyCommentAdapter(myComments);
        Log.d("MMMMMMMMMMM", "777777777");
        recyclerView.setAdapter(adapter);
        Log.d("MMMMMMMMMMM", "9999999");
    }

    private void initMyComments(){
        Log.d("MMMMMMMMMMM", "111111");
        myComments = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        for(int i =0; i < 10; i++){
            MyComment myComment = new MyComment();
            myComment.setStars(3);
            myComment.setAuthor("张三");
            myComment.setBookName("斗破苍穹");
            myComment.setBookUrl("http://book.img.ireader.com/group6/M00/09/57/CmRaIVeXHBaEMHRQAAAAAB6XPRk644055113.jpg?v=eJoHXyw7&t=CmRaIVv3rXw.");
            myComment.setCommentTime(simpleDateFormat.format(date));
            myComment.setContent("评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论评论");
            myComments.add(myComment);
        }

        Log.d("MMMMMMMMMMM", "22222222");
    }
}
