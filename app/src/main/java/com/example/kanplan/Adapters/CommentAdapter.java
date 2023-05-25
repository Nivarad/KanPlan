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
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{

    private Context context;
    private ArrayList<Comment> comments;

    private final RecyclerViewInterface recyclerViewInterface;



    public CommentAdapter(Context context, ArrayList<Comment> comments, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.comments = comments;
        this.recyclerViewInterface = recyclerViewInterface;
    }



    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false),comments,recyclerViewInterface);



    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentHolder holder, int position) {
        holder.commentText.setText(comments.get(position).getCommentText());
        holder.commentTitle.setText(comments.get(position).getCommentTitle());
        holder.commentWriterName.setText(comments.get(position).getCommentWriterName());

    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {

        public MaterialTextView commentWriterName;
        public MaterialTextView commentTitle;
        public MaterialTextView commentText;





        public CommentHolder(@NonNull View itemView, final ArrayList<Comment> comments, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            findViewsHolder();

        }

        public void findViewsHolder(){
            commentWriterName = itemView.findViewById(R.id.commentWriter);
            commentTitle = itemView.findViewById(R.id.commentTitle);
            commentText = itemView.findViewById(R.id.commentText);
        }
    }
}
