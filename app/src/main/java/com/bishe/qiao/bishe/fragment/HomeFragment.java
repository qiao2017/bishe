package com.bishe.qiao.bishe.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.ListBook;
import com.bishe.qiao.bishe.myadapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<ListBook> bookList = new ArrayList<>();
    public static String res;
//    private List<Book> bookList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_main_home, container, false);
        Bundle bundle = getArguments();
        res = bundle.getString("message");
        initBooks(res);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_main_home_recycler_view);
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initBooks(String res){
        bookList.clear();
        JSONArray jarr = JSONArray.parseArray(res);
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

    public static final HomeFragment newInstance(String text) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", text);
        fragment.setArguments(bundle);
        return fragment ;
    }

}
