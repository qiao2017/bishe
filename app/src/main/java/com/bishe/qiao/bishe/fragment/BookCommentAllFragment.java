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

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.BookComment;
import com.bishe.qiao.bishe.myadapter.ItemEbookCommentAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookCommentAllFragment extends Fragment{
    List<BookComment> bookComments;
//    private List<ListBook> bookList = new ArrayList<>();
    //    private List<Book> bookList = new ArrayList<>();
    private String bookUrl = "http://book.img.ireader.com/group6/M00/07/A8/CmQUN1usd9-ECydsAAAAAP3oY4M872146629.jpg?v=94TE-RrC&t=CmQUN1usePY.";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_book_comment_all, container, false);
        String msg = getArguments().getString("message");
//        initBooks();
/*        RecyclerView recyclerView = view.findViewById(R.id.fragment_main_home_recycler_view);
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));*/
        RecyclerView recyclerView = view.findViewById(R.id.fragment_book_comment_all_recycler_view);
        ItemEbookCommentAdapter adapter = new ItemEbookCommentAdapter(getBookComment());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

/*
    private void initBooks(){
        bookList.clear();
        for(int i = 0; i<50; i++){
            ListBook book = new ListBook();
            book.setAuthor("张三");
            book.setBookScore("3.5");
            book.setBookHeadUrl("http://bookbk.img.ireader.com/group6/M00/BB/CD/CmQUNlhGaf6ENNp0AAAAAMjGF3s094589847.jpg?v=RxWOQZ7V");
            book.setBookName("斗破苍穹");
            book.setContent("CCCCCCCC简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介");
            book.setPublish("PPPPPPPPPPPPP");
            bookList.add(book);
        }
    }*/

    public static final BookCommentAllFragment newInstance(String text) {
        BookCommentAllFragment fragment = new BookCommentAllFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", text);
        fragment.setArguments(bundle);
        return fragment ;
    }

    private List<BookComment> getBookComment(){
        List<BookComment> bookComments = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            BookComment bookComment = new BookComment();
            bookComment.setUserHead("http://q.qlogo.cn/qqapp/100467046/A2016D2246C0ECB6F26A540276A9E3A8/40");
            bookComment.setUserName("李四"+i);
            bookComment.setScore("2.7");
            bookComment.setContent("hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello");
            bookComments.add(bookComment);
        }
        return  bookComments;
    }

}