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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import myproject.sg.notenote.DetailNote;
import myproject.sg.notenote.DetailNoteAdapter;
import myproject.sg.notenote.DetailNoteDBAdapter;
import myproject.sg.notenote.NotifDBAdapter;
import myproject.sg.notenote.Notif;
import myproject.sg.notenote.NotifAdapter;
import myproject.sg.notenote.R;


public class HistoryFragment extends Fragment {

    ArrayList<Notif> notifList;
    ArrayList<DetailNote> detailNoteList;
    RecyclerView historyRecycler;
    NotifAdapter historyAdapter;
    DetailNoteAdapter detailNoteAdapter;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    NotifDBAdapter notifDB;
    DetailNoteDBAdapter detailNoteDB;

    TextView historyTitle;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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

        historyTitle = view.findViewById(R.id.historyTitle);
        Switch historySwitch = view.findViewById(R.id.historySwitch);

        historyRecycler = view.findViewById(R.id.historyRecycler);

        //for quick notes
        notifDB = new NotifDBAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);//set layout, 1 item per row

        //for detail notes
        detailNoteDB = new DetailNoteDBAdapter(getContext());
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //if quick has nothing, and detail has something
        if ((detailNoteDB.getDetailNoteList("1").size() > 0) && notifDB.getNotifList("1").size() == 0){
            historySwitch.setChecked(true);
            showDetailNotes();
        }
        else{
            historySwitch.setChecked(false);
            showQuickNotes();
        }
        historySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){//if true, i.e. Checked = Detail Notes
                    showDetailNotes();
                }
                else{//if false, i.e. Not Checked = Quick Notes
                    showQuickNotes();
                }
            }
        });
    }

    public void showQuickNotes(){
        historyTitle.setText("Quick Notes History");
        notifList = new ArrayList<>();
        notifList = notifDB.getNotifList("1");//status 1 indicates completed (aka history)
        Collections.sort(notifList, notifComparator);
        historyAdapter = new NotifAdapter(notifList,1);//0 for active notif obj, 1 for history
        historyRecycler.setLayoutManager(linearLayoutManager);
        historyRecycler.setItemAnimator(new DefaultItemAnimator());
        historyRecycler.setAdapter(historyAdapter);//set adapter
    }

    public void showDetailNotes(){
        historyTitle.setText("Detail Notes History");
        detailNoteList = new ArrayList<>();
        detailNoteList = detailNoteDB.getDetailNoteList("1");
        Collections.sort(detailNoteList, detailNoteComparator);
        detailNoteAdapter = new DetailNoteAdapter(detailNoteList, 1);
        historyRecycler.setLayoutManager(staggeredGridLayoutManager);
        historyRecycler.setItemAnimator(new DefaultItemAnimator());
        historyRecycler.setAdapter(detailNoteAdapter);
    }

    public Comparator<Notif> notifComparator = new Comparator<Notif>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Notif p1, Notif p2) {
            int l1 = Instant.parse(p2.getInstant()).compareTo(Instant.parse(p1.getInstant()));
            return l1;
        }
    };

    public Comparator<DetailNote> detailNoteComparator = new Comparator<DetailNote>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(DetailNote p1, DetailNote p2) {
            int l1 = Instant.parse(p2.getInstant()).compareTo(Instant.parse(p1.getInstant()));
            return l1;
        }
    };
}