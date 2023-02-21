package myproject.sg.notenote;

import android.content.Intent;
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

    ArrayList<DetailNote> detailNoteList;
    int viewType;//view type, 0 for active notif obj, 1 for history
    DetailNoteDBAdapter db;

    public DetailNoteAdapter(ArrayList<DetailNote> _detialNoteList, int _viewType){
        this.detailNoteList = _detialNoteList;
        this.viewType = _viewType;
    }

    @NonNull
    @Override
    public DetailNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_note_row,parent,false);
        return new DetailNoteAdapter.DetailNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailNoteViewHolder holder, int position) {
        db = new DetailNoteDBAdapter(holder.itemView.getContext());
        DetailNote detailNote = detailNoteList.get(position);
        holder.detailTitle.setText(detailNote.getTitle());
        holder.detailMessage.setText(detailNote.getMessage());
        holder.detailOptions.setVisibility(View.GONE);

        if (viewType == 0){//active detail notes section
            holder.itemView.setOnClickListener(new View.OnClickListener() {//make show the options to complete or delete
                @Override
                public void onClick(View view) {
                    if(holder.detailOptions.getVisibility() == View.GONE){
                        holder.detailOptions.setVisibility(View.VISIBLE);
                    }
                    else{
                        holder.detailOptions.setVisibility(View.GONE);
                    }
                }
            });

            holder.detailEdit.setOnClickListener(new View.OnClickListener() {//edit the detail note
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(holder.itemView.getContext(), CreateNote.class);
                    i.putExtra("mode","edit");
                    i.putExtra("detailNote", detailNote);
                    i.putExtra("note","detail");
                    holder.itemView.getContext().startActivity(i);
                }
            });

            holder.detailComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.completeDetailNote(detailNote, view.getContext());
                    detailNoteList.remove(holder.getAdapterPosition());
                    DetailNoteAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }
        else{//viewtype = 1, which is the view used for history
            holder.detailComplete.setVisibility(View.GONE);
            holder.detailEdit.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.ic_baseline_restart_alt_24));
            holder.detailEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.restoreDetailNote(detailNote,holder.itemView.getContext());
                    detailNoteList.remove(holder.getAdapterPosition());
                    DetailNoteAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.detailOptions.getVisibility() == View.GONE){
                        holder.detailOptions.setVisibility(View.VISIBLE);
                    }
                    else{
                        holder.detailOptions.setVisibility(View.GONE);
                    }
                }
            });
        }

        holder.detailDelete.setOnClickListener(new View.OnClickListener() {//delete the note
            @Override
            public void onClick(View view) {
                db.deleteDetailNote(detailNote, view.getContext());
                detailNoteList.remove(holder.getAdapterPosition());
                DetailNoteAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailNoteList.size();
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
