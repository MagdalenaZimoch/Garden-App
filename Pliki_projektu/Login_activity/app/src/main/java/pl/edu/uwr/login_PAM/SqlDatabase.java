package pl.edu.uwr.login_PAM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlDatabase  extends SQLiteOpenHelper {

    // user table
    public static final String createUsers = "CREATE TABLE IF NOT EXISTS users (id_user INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(255), password varchar(10))";
    // seeds table
    public static final String createSeeds = "";
    // sown seeds table
    public static final String createSownSeeds = "";
    // principles table
    public static final String createPrinciples = "";
    // reminder table
    public static final String createReminder = "";
    // observation table
    public static final String createObservation = "";

    public SqlDatabase(@Nullable Context context) {
        super(context,"Garden.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createUsers);
        // sqLiteDatabase.execSQL(createSeeds);
        // sqLiteDatabase.execSQL(createSownSeeds);
        // sqLiteDatabase.execSQL(createPrinciples);
        // sqLiteDatabase.execSQL(createReminder);
        // sqLiteDatabase.execSQL(createObservation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqLiteDatabase);
    }
}
