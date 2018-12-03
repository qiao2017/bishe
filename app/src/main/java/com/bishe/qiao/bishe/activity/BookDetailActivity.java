package com.bishe.qiao.bishe.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.util.BlurTransformation;
import com.bumptech.glide.Glide;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class BookDetailActivity extends AppCompatActivity {
    ImageView bookImgBig;
    ImageView bookImgSmall;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_detail);
        initEvents();
/*        Intent intent = getIntent();
        String bookName = intent.getStringExtra("bookName");
        String bookUrl = intent.getStringExtra("bookUrl");
        Toolbar toolbar = findViewById(R.id.activity_book_detail_tool_bar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.activity_book_detail_coll_tool_bar);
        ImageView bookImg = findViewById(R.id.activity_book_detail_book_img);
        TextView bookContentTextView = findViewById(R.id.activity_book_detail_text_view_book);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(bookName);
        collapsingToolbarLayout.setTitleEnabled(false);
        Glide.with(this).load(bookUrl).into(bookImg);
        String bookContent = generateBookContent(bookName);
        bookContentTextView.setText(bookContent);*/
    }





    protected void initEvents() {
        Log.d("NNNNNNNNNNNN", "NNNNNNNNNNNNNNNNNNNN");
        bookImgBig = findViewById(R.id.iv_book_bg);
        bookImgSmall = findViewById(R.id.iv_book_img);
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
    }


    private String generateBookContent(String bookName){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< 5000; i++){
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
