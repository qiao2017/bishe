package com.bishe.qiao.bishe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.ListBook;
import com.bishe.qiao.bishe.myadapter.BookAdapter;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        RecyclerView recyclerView = findViewById(R.id.fragment_main_home_recycler_view);
        BookAdapter adapter = new BookAdapter(new ArrayList<ListBook>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }
}
