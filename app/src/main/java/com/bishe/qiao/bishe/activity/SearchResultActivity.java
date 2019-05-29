package com.bishe.qiao.bishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.ListBook;
import com.bishe.qiao.bishe.myadapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    public static String res;
    private List<ListBook> bookList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        res = intent.getStringExtra("res");
        RecyclerView recyclerView = findViewById(R.id.search_result_recycler_view);
        initBooks();
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Toolbar toolbar = findViewById(R.id.search_result_toolbar);
        //设置toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true); //设置返回键可用
            actionBar.setTitle("搜索结果");
        }
    }

    private void initBooks(){
        bookList.clear();
        JSONArray jarr = JSONObject.parseObject(res).getJSONArray("searchResult");
        for(int i = 0; i<jarr.size(); i++){
            ListBook book = new ListBook();
            JSONObject jobjBook = JSONObject.parseObject(jarr.getString(i));
            book.setAuthor(jobjBook.getString("author"));
            String score = jobjBook.getString("avgScore");
            score = score == null ? "1.0" : score;
            book.setBookScore(score);
            book.setBookId(jobjBook.getInteger("bookId"));
            book.setPublish(jobjBook.getString("publishingHouse"));
            String profileUrl = jobjBook.getString("profileUrl");
            profileUrl = profileUrl == null ? "null" : profileUrl;
            book.setBookHeadUrl(profileUrl);
            book.setBookName(jobjBook.getString("bookName"));
            book.setContent(jobjBook.getString("content"));
            bookList.add(book);
        }
    }
}
