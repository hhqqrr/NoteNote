package myproject.sg.notenote;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {
    @NonNull
    @Override
    public NotifAdapter.NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.NotifViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NotifViewHolder extends RecyclerView.ViewHolder {
        public NotifViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
