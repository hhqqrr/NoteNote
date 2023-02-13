package myproject.sg.notenote.mainfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import myproject.sg.notenote.CreateNote;
import myproject.sg.notenote.DetailNote;
import myproject.sg.notenote.DetailNoteAdapter;
import myproject.sg.notenote.DetailNoteDBAdapter;
import myproject.sg.notenote.R;

public class NotesFragment extends Fragment {

    FloatingActionButton detailCreate;
    ArrayList<DetailNote> detailNoteList;
    RecyclerView detailNoteRecycler;
    DetailNoteAdapter detailNoteAdapter;
    GridLayoutManager gridLayoutManager;
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
}