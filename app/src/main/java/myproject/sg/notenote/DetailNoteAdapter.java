package myproject.sg.notenote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailNoteAdapter extends RecyclerView.Adapter<DetailNoteAdapter.DetailNoteViewHolder> {

    @NonNull
    @Override
    public DetailNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_note_row,parent,false);
        return new DetailNoteAdapter.DetailNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailNoteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DetailNoteViewHolder extends RecyclerView.ViewHolder{

        TextView detailTitle, detailMessage;
        ConstraintLayout detailOptions;
        ImageView detailEdit, detailDelete, detailComplete;
        public DetailNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            detailTitle = itemView.findViewById(R.id.detailTitle);
            detailMessage = itemView.findViewById(R.id.detailMessage);
            detailOptions = itemView.findViewById(R.id.detailOptions);
            detailEdit = itemView.findViewById(R.id.detailEdit);
            detailDelete = itemView.findViewById(R.id.detailDelete);
            detailComplete = itemView.findViewById(R.id.detailComplete);
        }
    }
}
