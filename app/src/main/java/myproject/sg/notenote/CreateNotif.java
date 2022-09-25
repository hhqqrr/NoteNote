package myproject.sg.notenote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.Instant;
import java.util.UUID;

public class CreateNotif extends AppCompatActivity {

    EditText titleInput, messageInput;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notif);

        DBAdapter db = new DBAdapter(this);

        titleInput = findViewById(R.id.titleInput);
        messageInput = findViewById(R.id.messageInput);
        createBtn = findViewById(R.id.createBtn);

        //create new notification
        createBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(!titleInput.getText().toString().matches("") || !messageInput.getText().toString().matches("")){//check if input is null
                    Notif notif = new Notif(UUID.randomUUID().toString(), Instant.now().toString(),titleInput.getText().toString(), messageInput.getText().toString(), "0");
                    db.addNotif(notif);
                    finish();
                }
            }
        });
    }
}