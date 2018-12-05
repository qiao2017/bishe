package com.bishe.qiao.bishe.myadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishe.qiao.bishe.R;
import com.bishe.qiao.bishe.model.BookComment;
import com.bishe.qiao.bishe.util.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ItemEbookCommentAdapter extends RecyclerView.Adapter<ItemEbookCommentAdapter.ViewHolder>{
    List<BookComment> bookComments;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ebook_comment, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BookComment comment = bookComments.get(i);
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(MyApplication.getContext()).load(comment.getUserHead()).apply(options).into(viewHolder.userImg);
        viewHolder.userName.setText(comment.getUserName());
        try{
            viewHolder.ratingBarScore.setRating(Float.parseFloat(comment.getScore()));
        }catch (Exception e){
            viewHolder.ratingBarScore.setRating(Float.parseFloat("1"));
        }
        viewHolder.content.setText(comment.getContent());
        viewHolder.time.setText(comment.getTime());
    }

    @Override
    public int getItemCount() {
        return bookComments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userImg;
        TextView userName;
        AppCompatRatingBar ratingBarScore;
        TextView content;
        TextView time;
        public ViewHolder(View view){
            super(view);
            userImg = view.findViewById(R.id.iv_avatar);
            userName = view.findViewById(R.id.tv_user_name);
            ratingBarScore = view.findViewById(R.id.ratingBar_hots);
            content = view.findViewById(R.id.tv_comment_content);
            time = view.findViewById(R.id.tv_update_time);
        }
    }
    public ItemEbookCommentAdapter(List<BookComment> bookComments){
        this.bookComments = bookComments;
    }
}
