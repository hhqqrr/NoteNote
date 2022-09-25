package myproject.sg.notenote.mainfragments;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import myproject.sg.notenote.CreateNotif;
import myproject.sg.notenote.DBAdapter;
import myproject.sg.notenote.Notif;
import myproject.sg.notenote.NotifAdapter;
import myproject.sg.notenote.R;

public class HomeFragment extends Fragment {

    FloatingActionButton notifCreate;
    ArrayList<Notif> notifList;
    RecyclerView notifRecycler;
    NotifAdapter notifAdapter;
    LinearLayoutManager linearLayoutManager;
    DBAdapter db;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notifCreate = view.findViewById(R.id.notifCreateBtn);
        notifRecycler = view.findViewById(R.id.notifRecycler);
        notifList = new ArrayList<>();

        db = new DBAdapter(getContext());

        notifList = db.getNotifList("0");
        Collections.sort(notifList, notifComparator);

        notifAdapter = new NotifAdapter(notifList);

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);//set layout, 1 item per row
        notifRecycler.setLayoutManager(linearLayoutManager);
        notifRecycler.setItemAnimator(new DefaultItemAnimator());
        notifRecycler.setAdapter(notifAdapter);//set adapter

        notifCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreateNotif.class);
                startActivity(i);
            }
        });
    }

    public Comparator<Notif> notifComparator = new Comparator<Notif>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public int compare(Notif p1, Notif p2) {
            int l1 = Instant.parse(p2.getInstant()).compareTo(Instant.parse(p1.getInstant()));
            return l1;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        notifAdapter.notifyDataSetChanged();
    }
}