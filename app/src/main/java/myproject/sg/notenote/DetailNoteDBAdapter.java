package myproject.sg.notenote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DetailNoteDBAdapter extends SQLiteOpenHelper {

    public DetailNoteDBAdapter(Context c){
        super(c, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE DETAILNOTE (UID TEXT PRIMARY KEY, INSTANT TEXT, TITLE TEXT, MESSAGE TEXT, STATUS TEXT, BUILDERID INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DETAILNOTE");
        onCreate(sqLiteDatabase);
    }
}
