package myproject.sg.notenote.mainfragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import myproject.sg.notenote.DBAdapter;
import myproject.sg.notenote.Notif;
import myproject.sg.notenote.NotifAdapter;
import myproject.sg.notenote.R;


public class HistoryFragment extends Fragment {

    ArrayList<Notif> notifList;
    RecyclerView historyRecycler;
    NotifAdapter historyAdapter;
    LinearLayoutManager linearLayoutManager;
    DBAdapter db;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        historyRecycler = view.findViewById(R.id.historyRecycler);
        notifList = new ArrayList<>();

        db = new DBAdapter(getContext());

        notifList = db.getNotifList("1");//status 1 indicates completed (aka history)
        Collections.sort(notifList, notifComparator);

        historyAdapter = new NotifAdapter(notifList,1);//0 for active notif obj, 1 for history

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);//set layout, 1 item per row
        historyRecycler.setLayoutManager(linearLayoutManager);
        historyRecycler.setItemAnimator(new DefaultItemAnimator());
        historyRecycler.setAdapter(historyAdapter);//set adapter
    }

    public Comparator<Notif> notifComparator = new Comparator<Notif>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Notif p1, Notif p2) {
            int l1 = Instant.parse(p2.getInstant()).compareTo(Instant.parse(p1.getInstant()));
            return l1;
        }
    };
}