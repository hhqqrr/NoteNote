package myproject.sg.notenote;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class NotifDBAdapter extends SQLiteOpenHelper {
    public NotifDBAdapter(Context c) {
        super(c, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE NOTIF (UID TEXT PRIMARY KEY, INSTANT TEXT, TITLE TEXT, MESSAGE TEXT, STATUS TEXT, BUILDERID INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NOTIF");
        onCreate(sqLiteDatabase);
    }

    public void addNotif(Notif notif){
        ContentValues values = new ContentValues();
        values.put("UID",notif.getUid());
        values.put("INSTANT",notif.getInstant());
        values.put("TITLE",notif.getTitle());
        values.put("MESSAGE",notif.getMessage());
        values.put("STATUS",notif.getStatus());
        values.put("BUILDERID",notif.getBuilderId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("NOTIF",null, values);
        db.close();
    }

    public Notif getNotifById(String uid){
        SQLiteDatabase db = this.getReadableDatabase();
        Notif notif = new Notif();
        Cursor cursor =  db.rawQuery("SELECT * FROM NOTIF WHERE UID = " + "\"" + uid + "\"", null);
        try {//add all to a list
            while(cursor.moveToNext()) {
                notif.setUid(cursor.getString(0));
                notif.setInstant(cursor.getString(1));
                notif.setTitle(cursor.getString(2));
                notif.setMessage(cursor.getString(3));
                notif.setStatus(cursor.getString(4));
                notif.setBuilderId(cursor.getInt(5));
            }
        } finally {
            cursor.close();
        }
        return notif;
    }

    //function to edit notification
    public boolean editNotif(Notif notif, String newTitle, String newMessage, Context c){
        SQLiteDatabase db = this.getWritableDatabase();
        if(newTitle.equals(notif.getTitle()) && newMessage.equals(notif.getMessage())){
            Toast.makeText(c,"Please edit at least one field", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!newTitle.equals(notif.getTitle())){//if new title is not the same as previous
            db.execSQL("UPDATE NOTIF SET TITLE = " + "\"" + newTitle + "\"" + " WHERE UID = " + "\"" + notif.getUid() + "\"");
        }
        if(!newMessage.equals(notif.getMessage())){//if new message is not the same as previous
            db.execSQL("UPDATE NOTIF SET MESSAGE = " + "\"" + newMessage + "\"" + " WHERE UID = " + "\"" + notif.getUid() + "\"");
        }

        //delete n recreate notification
        NotificationManager notificationManager = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notif.getBuilderId());
        createPhoneNotif(getNotifById(notif.getUid()), c);
        return true;
    }

    //function to delete notification
    public void deleteNotif(Notif notif, Context c){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM NOTIF WHERE UID = " + "\"" + notif.getUid() + "\"");
        NotificationManager notificationManager = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notif.getBuilderId());
    }

    //function to 'complete' notification
    public void completeNotif(Notif notif, Context c){// status 1 means notification is completed
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE NOTIF SET STATUS = " + "\"1\"" + "WHERE UID = " + "\"" + notif.getUid() + "\"");
        NotificationManager notificationManager = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notif.getBuilderId());
    }

    //function to restore previously completed notif obj
    public void restoreNotif(Notif notif, Context c){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE NOTIF SET STATUS = " + "\"0\"" + "WHERE UID = " + "\"" + notif.getUid() + "\"");
        createPhoneNotif(getNotifById(notif.getUid()), c);
    }

    //function to get the list of the notif using status
    public ArrayList<Notif> getNotifList(String status){// status 0 means active notification
        String query = "SELECT * FROM NOTIF WHERE STATUS = " + "\"" + status + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null); //cursor iteration starts from -1

        final ArrayList<Notif> notifList = new ArrayList<>();//create notification list
        try {//add all to a list
            while(cursor.moveToNext()) {
                Notif notif = new Notif();
                notif.setUid(cursor.getString(0));
                notif.setInstant(cursor.getString(1));
                notif.setTitle(cursor.getString(2));
                notif.setMessage(cursor.getString(3));
                notif.setStatus(cursor.getString(4));
                notif.setBuilderId(cursor.getInt(5));
                notifList.add(notif);
            }
        } finally {
            cursor.close();
        }
        return notifList;
    }

    //function to create a builder id for the notification channel
    public int createIdNotif(){//get max of the builderid column and add one
        String query = "SELECT MAX(BUILDERID) FROM NOTIF";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0) + 1;
        cursor.close();
        return count;
    }

    //function to create phone notification for a notif obj
    public void createPhoneNotif(Notif notif, Context c){
        Intent i = new Intent(c,MainActivity.class);
        PendingIntent intent = PendingIntent.getActivity(c, 1,
                i, PendingIntent.FLAG_MUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(notif.getUid(),"cb", NotificationManager.IMPORTANCE_LOW);
            channel.setSound(null,null);
            channel.enableVibration(false);
            NotificationManager manager = c.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, notif.getUid());
        builder.setContentTitle(notif.getTitle());
        builder.setContentText(notif.getMessage());
        builder.setSmallIcon(R.drawable.ic_baseline_done_24);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setSound(null);
        builder.setVibrate(new long[]{ 0 });
        builder.setContentIntent(intent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(c);
        managerCompat.notify(notif.getBuilderId(),builder.build());
    }
}
