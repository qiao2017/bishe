package com.bishe.qiao.bishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.BookComment;
import com.bishe.qiao.bishe.model.BookSeriesCeil;
import com.bishe.qiao.bishe.myadapter.ItemEbookCommentAdapter;
import com.bishe.qiao.bishe.util.BlurTransformation;
import com.bishe.qiao.bishe.util.ItemBookSeriesCeilAdapter;
import com.bishe.qiao.bishe.util.MyApplication;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class BookDetailActivity extends AppCompatActivity {
    ImageView bookImgBig;
    ImageView bookImgSmall;
    Toolbar toolbar;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    FloatingActionButton fab;
    TextView bookCommentLookMore;
    TextView addComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_detail);
        initEvents();
    }

    protected void initEvents() {
        bookImgBig = findViewById(R.id.iv_book_bg);
        bookImgSmall = findViewById(R.id.iv_book_img);
        addComment = findViewById(R.id.book_comment_add_comment);
        addComment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, CommentActivity.class);
                startActivity(intent);
            }
        });
        bookCommentLookMore = findViewById(R.id.book_comment_look_more);
        bookCommentLookMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, BookCommentAllActivity.class);
                startActivity(intent);
            }
        });
        String bookUrl = "http://bookbk.img.ireader.com/group6/M00/BB/CD/CmQUNlhGaf6ENNp0AAAAAMjGF3s094589847.jpg?v=RxWOQZ7V";
        Glide.with(this)
                .load(bookUrl)
                .apply(bitmapTransform(new BlurTransformation(this, 25, 3)))
                .into(bookImgBig);
        Glide.with(this)
                .load(bookUrl)
                .into(bookImgSmall);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("十面埋伏");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView1 = findViewById(R.id.book_detail_recycler_1);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(BookDetailActivity.this);
        mLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(mLayoutManager1);
        ItemEbookCommentAdapter adapter1 = new ItemEbookCommentAdapter(getBookComment());
        recyclerView1.setAdapter(adapter1);

        recyclerView2 = findViewById(R.id.book_detail_recycler_2);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(BookDetailActivity.this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(mLayoutManager2);
        ItemBookSeriesCeilAdapter adapter2 = new ItemBookSeriesCeilAdapter(getBookSeriesCeil());
        recyclerView2.setAdapter(adapter2);

        fab = findViewById(R.id.book_detail_fab_fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(), "已收藏", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private List<BookSeriesCeil> getBookSeriesCeil(){
        List<BookSeriesCeil> bookSeriesCeils = new ArrayList<>();
        for (int i = 0; i<10; i++){
            BookSeriesCeil bookSeriesCeil = new BookSeriesCeil();
            bookSeriesCeil.setBookId("123456" + i);
            bookSeriesCeil.setBookName("时间简史");
            bookSeriesCeil.setBookUrl("http://bookbk.img.ireader.com/group6/M00/37/4E/CmQUOFdX8gaEZJNWAAAAAF8hRhg431643616.jpg?v=A151oVg_&t=CmQUOFlSDF8.");
            bookSeriesCeil.setScore("3.1");
            bookSeriesCeils.add(bookSeriesCeil);
        }
        return bookSeriesCeils;
    }
    private List<BookComment> getBookComment(){
        List<BookComment> bookComments = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            BookComment bookComment = new BookComment();
            bookComment.setUserHead("http://q.qlogo.cn/qqapp/100467046/A2016D2246C0ECB6F26A540276A9E3A8/40");
            bookComment.setUserName("李四"+i);
            bookComment.setScore("2.7");
            bookComment.setContent(generateBookContent(String.valueOf(i)));
            bookComments.add(bookComment);
        }
        return  bookComments;
    }

    private String generateBookContent(String bookName){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< 500; i++){
            sb.append(bookName);
        }
        return sb == null ? "null" : sb.toString();
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
}
