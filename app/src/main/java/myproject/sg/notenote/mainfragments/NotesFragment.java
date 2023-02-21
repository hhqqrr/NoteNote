package myproject.sg.notenote.mainfragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import myproject.sg.notenote.CreateNote;
import myproject.sg.notenote.DetailNote;
import myproject.sg.notenote.DetailNoteAdapter;
import myproject.sg.notenote.DetailNoteDBAdapter;
import myproject.sg.notenote.NotifAdapter;
import myproject.sg.notenote.R;

public class NotesFragment extends Fragment {

    FloatingActionButton detailCreate;
    ArrayList<DetailNote> detailNoteList;
    RecyclerView detailNoteRecycler;
    DetailNoteAdapter detailNoteAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    DetailNoteDBAdapter detailNoteDB;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailCreate = view.findViewById(R.id.detailCreateBtn);
        detailNoteRecycler = view.findViewById(R.id.detailNoteRecycler);
        detailNoteList = new ArrayList<>();
        detailNoteDB = new DetailNoteDBAdapter(getContext());

        detailNoteList = detailNoteDB.getDetailNoteList("0");
        Collections.sort(detailNoteList, detailNoteComparator);

        detailNoteAdapter = new DetailNoteAdapter(detailNoteList, 0);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        detailNoteRecycler.setLayoutManager(staggeredGridLayoutManager);
        detailNoteRecycler.setItemAnimator(new DefaultItemAnimator());
        detailNoteRecycler.setAdapter(detailNoteAdapter);

        detailCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreateNote.class);
                i.putExtra("mode", "create");
                i.putExtra("note","detail");
                startActivity(i);
            }
        });
    }

    public Comparator<DetailNote> detailNoteComparator = new Comparator<DetailNote>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(DetailNote p1, DetailNote p2) {
            int l1 = Instant.parse(p2.getInstant()).compareTo(Instant.parse(p1.getInstant()));
            return l1;
        }
    };

    @Override
    public void onResume() {//so that changes will be reflected
        super.onResume();
        detailNoteList = detailNoteDB.getDetailNoteList("0");
        Collections.sort(detailNoteList, detailNoteComparator);
        detailNoteAdapter = new DetailNoteAdapter(detailNoteList, 0);//viewtpe 0 for active notif, 1 for history
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        detailNoteRecycler.setLayoutManager(staggeredGridLayoutManager);
        detailNoteRecycler.setItemAnimator(new DefaultItemAnimator());
        detailNoteRecycler.setAdapter(detailNoteAdapter);//set adapter
    }
}