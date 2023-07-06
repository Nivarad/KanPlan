package com.example.kanplan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kanplan.Interfaces.RecyclerViewInterface;
import com.example.kanplan.Models.Comment;
import com.example.kanplan.R;
import com.example.kanplan.Utils.MySP;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{

    private Context context;
    private ArrayList<Comment> comments;





    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
        this.comments.remove(0);

    }



    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false),comments);



    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentHolder holder, int position) {
        holder.commentText.setText(comments.get(position).getCommentText());
        holder.commentDate.setText(comments.get(position).getDate());
        if(comments.get(position).getCommentWriterName().equals(MySP.getInstance().getName()))
            holder.commentWriterName.setText("You");
        else
            holder.commentWriterName.setText(comments.get(position).getCommentWriterName());

    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {

        public MaterialTextView commentWriterName;
        public MaterialTextView commentText;

        public MaterialTextView commentDate;





        public CommentHolder(@NonNull View itemView, final ArrayList<Comment> comments) {
            super(itemView);

            findViewsHolder();

        }

        public void findViewsHolder(){
            commentWriterName = itemView.findViewById(R.id.commentWriter);
            commentText = itemView.findViewById(R.id.commentText);
            commentDate = itemView.findViewById(R.id.commentDate);
        }
    }
}
