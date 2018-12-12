package com.bishe.qiao.bishe.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.fragment.BookCommentAllFragment;

public class BookCommentAllActivity extends BaseActivity {
    LinearLayout linearLayoutLeft;
    LinearLayout linearLayoutAll;
    LinearLayout linearLayoutRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment_all);

        linearLayoutLeft = findViewById(R.id.fragment_book_comment_left);
        linearLayoutAll = findViewById(R.id.fragment_book_comment_all);
        linearLayoutRight = findViewById(R.id.fragment_book_comment_right);
        linearLayoutLeft.setVisibility(View.GONE);
        Log.e("MMMMMMMMM", "Hello");
        replaceFragment(BookCommentAllFragment.newInstance("Hello"));
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_book_comment_all_fragment, fragment);
        transaction.commit();
    }
}
