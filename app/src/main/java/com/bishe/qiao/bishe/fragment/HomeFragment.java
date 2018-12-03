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
import com.bishe.qiao.bishe.model.ListBook;
import com.bishe.qiao.bishe.myadapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<ListBook> bookList = new ArrayList<>();
//    private List<Book> bookList = new ArrayList<>();
    private String bookUrl = "http://book.img.ireader.com/group6/M00/07/A8/CmQUN1usd9-ECydsAAAAAP3oY4M872146629.jpg?v=94TE-RrC&t=CmQUN1usePY.";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_main_home, container, false);
        initBooks();
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
    }

    public static final HomeFragment newInstance(String text) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", text);
        fragment.setArguments(bundle);
        return fragment ;
    }

}
