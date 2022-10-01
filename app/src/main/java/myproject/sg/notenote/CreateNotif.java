package myproject.sg.notenote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.Instant;
import java.util.UUID;

import myproject.sg.notenote.mainfragments.HomeFragment;

public class CreateNotif extends AppCompatActivity {

    EditText titleInput, messageInput;
    Button createBtn;
    TextView titleActivity;
    String mode;
    Notif notifToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notif);

        Intent i = getIntent();
        if(i!=null){//if intent is not null
            mode = i.getStringExtra("mode");//get the mode
            if (mode.equals("edit")){//get notif obj if mode is edit
                notifToEdit = (Notif) i.getSerializableExtra("notif");
            }
            else{
                notifToEdit = null;
            }
        }
        else{
            mode = "create";//set mode to create by default
        }

        DBAdapter db = new DBAdapter(this);

        titleInput = findViewById(R.id.titleInput);
        messageInput = findViewById(R.id.messageInput);
        createBtn = findViewById(R.id.createBtn);
        titleActivity = findViewById(R.id.titleActivity);

        if(mode.equals("create")){//create new notification mode
            titleActivity.setText("Create Notification");
            createBtn.setText("Create");

            //create new notification
            createBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    if(!titleInput.getText().toString().matches("") || !messageInput.getText().toString().matches("")){//check if input is null
                        Notif notif = new Notif(UUID.randomUUID().toString(), Instant.now().toString(),titleInput.getText().toString(), messageInput.getText().toString(), "0",db.createIdNotif());
                        db.addNotif(notif);//db function to insert obj to sqlite
                        db.createPhoneNotif(notif,CreateNotif.this);//create notificaton on phone
                        finish();
                    }
                }
            });
        }
        else{//edit previous notification mode
            titleActivity.setText("Edit Notification");
            createBtn.setText("Save");
            createBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!titleInput.getText().toString().matches("") || !messageInput.getText().toString().matches("")){
                        db.editNotif(notifToEdit, titleInput.getText().toString(), messageInput.getText().toString());
                        finish();
                    }
                }
            });
        }


    }//end of oncreate
}