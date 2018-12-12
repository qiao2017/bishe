package com.bishe.qiao.bishe.myadapter;

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
import com.bishe.qiao.bishe.model.MyComment;
import com.bishe.qiao.bishe.util.MyApplication;
import com.bumptech.glide.Glide;

import java.util.List;

public class ItemMyCommentAdapter extends RecyclerView.Adapter<ItemMyCommentAdapter.ViewHolder>{
    private List<MyComment> mMyComments;

    public ItemMyCommentAdapter(List<MyComment> mMyComments) {
        this.mMyComments = mMyComments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_my_comment, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MyComment myComment = mMyComments.get(i);
        viewHolder.itemMyCommentRateBar.setRating(myComment.getStars());
        viewHolder.itemMyCommentComment.setText(myComment.getContent());
        Glide.with(MyApplication.getContext())
                .load(myComment.getBookUrl()).into(viewHolder.itemMyCommentBookImg);
        viewHolder.itemMyCommentBookName.setText(myComment.getBookName());
        viewHolder.itemMyCommentBookInfo.setText(" " + myComment.getAuthor() + " | " + myComment.getPublish() + " ");
        viewHolder.itemMyCommentCommentTime.setText(myComment.getCommentTime());
    }

    @Override
    public int getItemCount() {
        return mMyComments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        AppCompatRatingBar itemMyCommentRateBar;
        TextView itemMyCommentComment;
        ImageView itemMyCommentBookImg;
        TextView itemMyCommentBookName;
        TextView itemMyCommentBookInfo;
        TextView itemMyCommentCommentTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            itemMyCommentRateBar = itemView.findViewById(R.id.item_my_comment_rate_bar);
            itemMyCommentComment = itemView.findViewById(R.id.item_my_comment_comment);
            itemMyCommentBookImg = itemView.findViewById(R.id.item_my_comment_book_img);
            itemMyCommentBookName = itemView.findViewById(R.id.item_my_comment_book_name);
            itemMyCommentBookInfo = itemView.findViewById(R.id.item_my_comment_book_info);
            itemMyCommentCommentTime = itemView.findViewById(R.id.item_my_comment_comment_time);
        }
    }
}
