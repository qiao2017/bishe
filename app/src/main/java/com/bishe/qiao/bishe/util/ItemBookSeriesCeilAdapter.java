package com.bishe.qiao.bishe.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.BookSeriesCeil;
import com.bumptech.glide.Glide;

import java.util.List;

public class ItemBookSeriesCeilAdapter extends RecyclerView.Adapter<ItemBookSeriesCeilAdapter.ViewHolder>{
    List<BookSeriesCeil> bookSeriesCeils;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book_series_ceil, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BookSeriesCeil bookSeriesCeil = bookSeriesCeils.get(i);
        Glide.with(MyApplication.getContext()).load(bookSeriesCeil.getBookUrl()).into(viewHolder.bookImg);
        viewHolder.bookName.setText(bookSeriesCeil.getBookName());
        try{
            viewHolder.ratingBarScore.setRating(Float.parseFloat(bookSeriesCeil.getScore()));
        }catch (Exception e){
            viewHolder.ratingBarScore.setRating(Float.parseFloat("1"));
        }
        viewHolder.score.setText(bookSeriesCeil.getScore());
        viewHolder.bookImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(), bookSeriesCeil.getBookId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookSeriesCeils.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bookImg;
        TextView bookName;
        AppCompatRatingBar ratingBarScore;
        TextView score;
        public ViewHolder(View view){
            super(view);
            bookImg = view.findViewById(R.id.iv_book_img);
            bookName = view.findViewById(R.id.tv_title);
            ratingBarScore = view.findViewById(R.id.ratingBar_hots);
            score = view.findViewById(R.id.tv_hots_num);
        }
    }
    public ItemBookSeriesCeilAdapter(List<BookSeriesCeil> bookComments){
        this.bookSeriesCeils = bookComments;
        Log.d("MMMMMMMMMMMMM", bookSeriesCeils.toString());
    }
}
