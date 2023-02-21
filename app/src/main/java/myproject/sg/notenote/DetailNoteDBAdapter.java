package myproject.sg.notenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailNoteDBAdapter extends SQLiteOpenHelper {

    public DetailNoteDBAdapter(Context c){
        super(c, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE NOTIF (UID TEXT PRIMARY KEY, INSTANT TEXT, TITLE TEXT, MESSAGE TEXT, STATUS TEXT, BUILDERID INT)");
        sqLiteDatabase.execSQL("CREATE TABLE DETAILNOTE (UID TEXT PRIMARY KEY, INSTANT TEXT, TITLE TEXT, MESSAGE TEXT, STATUS TEXT, BUILDERID INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NOTIF");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DETAILNOTE");
        onCreate(sqLiteDatabase);
    }

    //function to create a builder id for the detail note channel
    public int createIdDetailNote(){//get max of the builderid column and add one
        String query = "SELECT MAX(BUILDERID) FROM DETAILNOTE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0) + 1;
        cursor.close();
        return count;
    }

    //function to add new detail note to db
    public void addDetailNote(DetailNote detailNote){
        ContentValues values = new ContentValues();
        values.put("UID",detailNote.getUid());
        values.put("INSTANT",detailNote.getInstant());
        values.put("TITLE",detailNote.getTitle());
        values.put("MESSAGE",detailNote.getMessage());
        values.put("STATUS",detailNote.getStatus());
        values.put("BUILDERID",detailNote.getBuilderId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("DETAILNOTE",null, values);
        db.close();
    }

    //function to get the list of the detail note using status
    public ArrayList<DetailNote> getDetailNoteList(String status){// status 0 means active notification
        String query = "SELECT * FROM DETAILNOTE WHERE STATUS = " + "\"" + status + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null); //cursor iteration starts from -1

        final ArrayList<DetailNote> detailNotes = new ArrayList<>();//create notification list
        try {//add all to a list
            while(cursor.moveToNext()) {
                DetailNote detailNote = new DetailNote();
                detailNote.setUid(cursor.getString(0));
                detailNote.setInstant(cursor.getString(1));
                detailNote.setTitle(cursor.getString(2));
                detailNote.setMessage(cursor.getString(3));
                detailNote.setStatus(cursor.getString(4));
                detailNote.setBuilderId(cursor.getInt(5));
                detailNotes.add(detailNote);
            }
        } finally {
            cursor.close();
        }
        return detailNotes;
    }

    public boolean editDetailNote(DetailNote detailNote, String newTitle, String newMessage, Context c){
        SQLiteDatabase db = this.getWritableDatabase();
        if(newTitle.equals(detailNote.getTitle()) && newMessage.equals(detailNote.getMessage())){
            Toast.makeText(c,"Please edit at least one field", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!newTitle.equals(detailNote.getTitle())){//if new title is not the same as previous
            db.execSQL("UPDATE DETAILNOTE SET TITLE = " + "\"" + newTitle + "\"" + " WHERE UID = " + "\"" + detailNote.getUid() + "\"");
        }
        if(!newMessage.equals(detailNote.getMessage())){//if new message is not the same as previous
            db.execSQL("UPDATE DETAILNOTE SET MESSAGE = " + "\"" + newMessage + "\"" + " WHERE UID = " + "\"" + detailNote.getUid() + "\"");
        }
        return true;
    }

    //function to delete detailnote
    public void deleteDetailNote(DetailNote detailNote, Context c){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM DETAILNOTE WHERE UID = " + "\"" + detailNote.getUid() + "\"");
    }

    //function to 'complete' detailnote
    public void completeDetailNote(DetailNote detailNote, Context c){// status 1 means notification is completed
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE DETAILNOTE SET STATUS = " + "\"1\"" + "WHERE UID = " + "\"" + detailNote.getUid() + "\"");
    }

    //function to restore previously completed detail note obj
    public void restoreDetailNote(DetailNote detailNote, Context c){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE DETAILNOTE SET STATUS = " + "\"0\"" + "WHERE UID = " + "\"" + detailNote.getUid() + "\"");
    }
}
