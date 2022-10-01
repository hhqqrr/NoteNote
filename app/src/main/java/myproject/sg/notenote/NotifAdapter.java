package myproject.sg.notenote;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {

    ArrayList<Notif> notifList;
    DBAdapter db;

    public NotifAdapter(ArrayList<Notif> _notifList){
        this.notifList = _notifList;
    }

    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_row,parent,false);
        return new NotifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        db = new DBAdapter(holder.itemView.getContext());
        Notif notif = notifList.get(position);
        holder.titleRow.setText(notif.getTitle());
        holder.messageRow.setText(notif.getMessage());
        holder.optionsRow.setVisibility(View.GONE);//default to hide the options

        //expand options
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.optionsRow.getVisibility() == View.GONE){
                    holder.optionsRow.setVisibility(View.VISIBLE);
                }
                else{
                    holder.optionsRow.setVisibility(View.GONE);
                }
            }
        });
        //delete row
        holder.deleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNotif(notif, view.getContext());
                notifList.remove(holder.getAdapterPosition());
                NotifAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
            }
        });

        //complete row
        holder.completeRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.completeNotif(notif, view.getContext());
                notifList.remove(holder.getAdapterPosition());
                NotifAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifList.size();
    }

    public class NotifViewHolder extends RecyclerView.ViewHolder {
        TextView titleRow, messageRow;
        ConstraintLayout optionsRow;
        ImageView editRow, deleteRow, completeRow;
        public NotifViewHolder(@NonNull View itemView) {
            super(itemView);
            titleRow = itemView.findViewById(R.id.titleRow);
            messageRow = itemView.findViewById(R.id.messageRow);
            optionsRow = itemView.findViewById(R.id.optionsRow);
            editRow = itemView.findViewById(R.id.editRow);
            deleteRow = itemView.findViewById(R.id.deleteRow);
            completeRow = itemView.findViewById(R.id.completeRow);
        }
    }
}
