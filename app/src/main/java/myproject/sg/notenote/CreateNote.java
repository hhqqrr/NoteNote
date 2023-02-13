package myproject.sg.notenote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
import java.util.UUID;

public class CreateNote extends AppCompatActivity {

    EditText titleInput, messageInput;
    Button createBtn;
    TextView titleActivity,titleCount,messageCount;
    String mode,note;
    Notif notifToEdit;
    DetailNote noteToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Intent i = getIntent();
        if(i!=null){//if intent is not null
            mode = i.getStringExtra("mode");//get the mode
            note = i.getStringExtra("note");//get note type;; 'quick' or 'detail'
            if (mode.equals("edit") && note.equals("quick")){//get notif obj if mode is edit and is quick note
                notifToEdit = (Notif) i.getSerializableExtra("notif");
            }
            else if(mode.equals("edit") && note.equals("detail")){
                noteToEdit = (DetailNote) i.getSerializableExtra("note");
            }
            else{
                notifToEdit = null;
                noteToEdit = null;
            }
        }
        else{
            mode = "create";//set mode to create by default
        }

        NotifDBAdapter db = new NotifDBAdapter(this);

        titleInput = findViewById(R.id.titleInput);
        messageInput = findViewById(R.id.messageInput);
        createBtn = findViewById(R.id.createBtn);
        titleActivity = findViewById(R.id.titleActivity);
        titleCount = findViewById(R.id.titleCount);
        messageCount = findViewById(R.id.messageCount);
        titleCount.setText("0/20");

        titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                titleCount.setText(editable.toString().length() + "/20");
            }
        });

        if (note.equals("quick")){
            messageCount.setText("0/100");
        }
        else{
            messageCount.setText("0/10000");
        }

        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer i =100;
                if (note.equals("detail")) {i = 10000;}
                messageCount.setText(editable.toString().length() + "/" + i.toString());
            }
        });

        if(mode.equals("create") && note.equals("quick")){//create new NOTIFICATION mode
            titleActivity.setText("Create Notification");
            createBtn.setText("Create");

            //create new notification
            createBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    if((titleInput.getText().toString().matches("") == false) && (messageInput.getText().toString().matches("") == false)){//check if input is null
                        Notif notif = new Notif(UUID.randomUUID().toString(), Instant.now().toString(),titleInput.getText().toString().trim(), messageInput.getText().toString().trim(), "0",db.createIdNotif());
                        db.addNotif(notif);//db function to insert obj to sqlite
                        db.createPhoneNotif(notif, CreateNote.this);//create notificaton on phone
                        finish();
                    }
                    else{//validation error
                        Toast.makeText(CreateNote.this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if (mode.equals("edit") && note.equals("quick")){//edit previous NOTIFICATION mode
            titleActivity.setText("Edit Notification");
            createBtn.setText("Save");
            titleInput.setText(notifToEdit.getTitle());
            messageInput.setText(notifToEdit.getMessage());
            createBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if((titleInput.getText().toString().matches("") == false) && (messageInput.getText().toString().matches("") == false)){
                        Boolean executed = db.editNotif(notifToEdit, titleInput.getText().toString().trim(), messageInput.getText().toString().trim(), CreateNote.this);
                        if(executed){//function returns true if the query is executed
                            finish();
                        }//else do nothing
                    }
                    else{//validation error
                        Toast.makeText(CreateNote.this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else if (mode.equals("create") && note.equals("detail")){
            titleActivity.setText("Create Detail Note");
            createBtn.setText("Create");
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(10000);
            messageInput.setFilters(filters);
            messageInput.setHint("max: 10,000 char");


        }
        else if (mode.equals("edit") && note.equals("detail")){

        }


    }//end of oncreate
}