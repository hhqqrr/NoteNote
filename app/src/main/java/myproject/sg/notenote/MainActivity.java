package myproject.sg.notenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import myproject.sg.notenote.mainfragments.HistoryFragment;
import myproject.sg.notenote.mainfragments.HomeFragment;
import myproject.sg.notenote.mainfragments.NotesFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    NotesFragment notesFragment = new NotesFragment();
    HistoryFragment historyFragment = new HistoryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MainActivity.this.deleteDatabase("data.db");
        bottomNavigationView = findViewById(R.id.bottomNavView);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,homeFragment).commit();

        NotifDBAdapter db = new NotifDBAdapter(this);
        ArrayList<Notif> notifs = new ArrayList<>();
        notifs = db.getNotifList("0");//get actve notifs
        for (Notif n: notifs) {
            db.createPhoneNotif(n,MainActivity.this);//create notificaton on phone
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,homeFragment).commit();
                        return true;
                    case R.id.notes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,notesFragment).commit();
                        return true;
                    case R.id.history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,historyFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}