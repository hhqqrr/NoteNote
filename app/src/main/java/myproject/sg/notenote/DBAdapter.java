package myproject.sg.notenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter extends SQLiteOpenHelper {
    public DBAdapter(Context c) {
        super(c, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE NOTIF (UID TEXT PRIMARY KEY, INSTANT TEXT, TITLE TEXT, MESSAGE TEXT, STATUS TEXT)");
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
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("NOTIF",null, values);
        db.close();
    }

    public void deleteNotif(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM NOTIF WHERE UID = " + "\"" + uid + "\"");
    }

    public void completeNotif(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE NOTIF SET STATUS = " + "\"1\"" + "WHERE UID = " + "\"" + uid + "\"");
    }

    public ArrayList<Notif> getNotifList(String status){
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
                notifList.add(notif);
            }
        } finally {
            cursor.close();
        }
        return notifList;
    }
}
