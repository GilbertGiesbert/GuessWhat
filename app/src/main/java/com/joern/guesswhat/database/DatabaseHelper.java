package com.joern.guesswhat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;

/**
 * Created by joern on 13.04.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DatabaseHelper.class.getName();

    private static final String DB_NAME = "appDatabase";

    private static final int DB_VERSION = 1;

    private static boolean migrationDone = false;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context){

        if(!migrationDone){
            migrate(context);
            migrationDone = true;
        }
        return new DatabaseHelper(context);
    }

    private static void migrate(Context context){
        Log.d(LOG_TAG, "migrate()");

        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, 0, null);
        ContextHolder.setContext(context);
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:" + db.getPath(), "", "");
        flyway.setBaselineOnMigrate(true);
        flyway.migrate();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "onUpgrade()");
    }
}
