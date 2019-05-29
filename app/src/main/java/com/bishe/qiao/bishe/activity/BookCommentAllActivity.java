package com.bishe.qiao.bishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.fragment.BookCommentAllFragment;

public class BookCommentAllActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment_all);
        Intent intent = getIntent();
        String res = intent.getStringExtra("res");
        replaceFragment(BookCommentAllFragment.newInstance(res));
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_book_comment_all_fragment, fragment);
        transaction.commit();
    }
}
