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
import com.bishe.qiao.bishe.model.BookComment;
import com.bishe.qiao.bishe.myadapter.ItemEbookCommentAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookCommentAllFragment extends Fragment{
    List<BookComment> bookComments;
    public static String res;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_book_comment_all, container, false);
        res = getArguments().getString("message");

        RecyclerView recyclerView = view.findViewById(R.id.fragment_book_comment_all_recycler_view);
        initBookComments();
        ItemEbookCommentAdapter adapter = new ItemEbookCommentAdapter(bookComments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static final BookCommentAllFragment newInstance(String text) {
        BookCommentAllFragment fragment = new BookCommentAllFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", text);
        fragment.setArguments(bundle);
        return fragment ;
    }

    private void initBookComments(){
        JSONArray jarr = JSONObject.parseObject(res).getJSONObject("comments").getJSONArray("comments");
        bookComments = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < jarr.size(); i++){
            JSONObject jobj = jarr.getJSONObject(i);
            BookComment bookComment = new BookComment();
            bookComment.setUserHead(jobj.getString("headUrl"));
            bookComment.setUserName(jobj.getString("nickName"));
            bookComment.setScore(jobj.getString("stars"));
            bookComment.setContent(jobj.getString("content"));
            Long time = jobj.getLong("commentTime");
            Date date;
            if(time == null){
                date = new Date();
            }else{
                date = new Date(time);
            }
            bookComment.setTime(df.format(date));
            bookComments.add(bookComment);
        }
    }

}