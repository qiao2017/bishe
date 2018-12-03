package com.bishe.qiao.bishe.myadapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.activity.BookDetailActivity;
import com.bishe.qiao.bishe.model.ListBook;
import com.bumptech.glide.Glide;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context mContext;
    private List<ListBook> bookList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView bookImage;
        TextView bookName;
        AppCompatRatingBar ratingBar;
        TextView score;
        TextView bookInfo;
        TextView bookDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            bookImage = itemView.findViewById(R.id.iv_book_img);
            bookName = itemView.findViewById(R.id.tv_book_title);
            ratingBar = itemView.findViewById(R.id.ratingBar_hots);
            score = itemView.findViewById(R.id.tv_hots_num);
            bookInfo = itemView.findViewById(R.id.tv_book_info);
            bookDescription = itemView.findViewById(R.id.tv_book_description);
        }

    }

    public BookAdapter(List<ListBook> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext == null){
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_book_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        ListBook book = bookList.get(i);
        viewHolder.bookName.setText(book.getBookName());
        Glide.with(mContext).load(book.getBookHeadUrl()).into(viewHolder.bookImage);
        viewHolder.ratingBar.setRating(Float.parseFloat(book.getBookScore()));
        viewHolder.bookDescription.setText(book.getContent());
        viewHolder.bookInfo.setText(book.getAuthor());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                intent.putExtra("bookName", String.valueOf(position));
                intent.putExtra("bookUrl", "http://book.img.ireader.com/group6/M00/07/A8/CmQUN1usd9-ECydsAAAAAP3oY4M872146629.jpg?v=94TE-RrC&t=CmQUN1usePY.");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
