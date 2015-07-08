package com.joern.guesswhat.persistence.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.joern.guesswhat.constants.DB;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public class UserDaoImpl implements UserDao{

    private static final String LOG_TAG = UserDaoImpl.class.getName();

    private DatabaseHelper dbHelper;

    public UserDaoImpl(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public boolean createUser(String name, String email, int passwordHash) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.TABLE_USERS.COL_NAME, name);
        values.put(DB.TABLE_USERS.COL_EMAIL, email);
        values.put(DB.TABLE_USERS.COL_PASSWORD_HASH, passwordHash);

        return -1 != db.insert(DB.TABLE_USERS.NAME, null, values);
    }

    @Override
    public User readUser(int id) {

        User user = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String whereClause = DB.TABLE_USERS.COL_ID + " = ?";
        String[] whereArgs = new String[]{""+id};

        Cursor c = db.query(DB.TABLE_USERS.NAME, null, whereClause, whereArgs, null, null, null);

        if(c.moveToFirst()){

            String name = c.getString(c.getColumnIndex(DB.TABLE_USERS.COL_NAME));
            String email = c.getString(c.getColumnIndex(DB.TABLE_USERS.COL_EMAIL));
            user = new User(id, name, email);

        }else{
            Log.d(LOG_TAG, "no user for id="+id);
        }

        c.close();
        return user;
    }

    @Override
    public User readUser(String name) {

        User user = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String whereClause = DB.TABLE_USERS.COL_NAME + " = ?";
        String[] whereArgs = new String[]{name};

        Cursor c = db.query(DB.TABLE_USERS.NAME, null, whereClause, whereArgs, null, null, null);

        if(c.moveToFirst()){

            int id = c.getInt(c.getColumnIndex(DB.TABLE_USERS.COL_ID));

            // name was read with 'COLLATE NOCASE' (meaning it was read case-insensitive)
            // so set name to original db value (when user entered wrong case)
            name = c.getString(c.getColumnIndex(DB.TABLE_USERS.COL_NAME));

            String email = c.getString(c.getColumnIndex(DB.TABLE_USERS.COL_EMAIL));
            user = new User(id, name, email);

        }else{
            Log.d(LOG_TAG, "no user for name="+name);
        }

        c.close();
        return user;
    }

    @Override
    public boolean updateUser(User user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.TABLE_USERS.COL_NAME, user.getName());
        values.put(DB.TABLE_USERS.COL_EMAIL, user.getEmail());

        return 0 < db.update(DB.TABLE_USERS.NAME, values, DB.TABLE_USERS.COL_ID + " = ?", new String[]{""+user.getId()});
    }

    @Override
    public boolean deleteUser(User user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return 0 < db.delete(DB.TABLE_USERS.NAME, DB.TABLE_USERS.COL_ID + " = ?", new String[]{"" + user.getId()});
    }

    @Override
    public boolean checkPswd(User user, int passwordHash) {

        boolean result = false;

        if(user != null){

            String[] columns = new String[]{DB.TABLE_USERS.COL_PASSWORD_HASH};
            String whereClause = DB.TABLE_USERS.COL_ID + " = ?";
            String[] whereArgs = new String[]{""+user.getId()};

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.query(DB.TABLE_USERS.NAME, columns, whereClause, whereArgs, null, null, null);

            if(c.moveToFirst()){

                int savedHash = c.getInt(c.getColumnIndex(DB.TABLE_USERS.COL_PASSWORD_HASH));
                result = savedHash == passwordHash;
            }
            c.close();
        }
        return result;
    }

    @Override
    public boolean updatePswd(User user, int oldPswdHash, int newPswdHash) {

        boolean oldPswdOk = checkPswd(user, oldPswdHash);
        if(oldPswdOk){

            // TODO

        }

        return false;
    }


    @Override
    public List<Friendship> getFriendships(User user, FriendshipState state){

        List<Friendship> friendships = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String whereClause = DB.TABLE_FRIENDSHIPS.COL_USER_ID + " = ? AND "+DB.TABLE_FRIENDSHIPS.COL_STATE+" = ?";
        String[] whereArgs = new String[]{""+user.getId(),""+state.getValue()};

        Cursor c = db.query(DB.TABLE_FRIENDSHIPS.NAME, null, whereClause, whereArgs, null, null, null);

        if(c.moveToFirst()){

            do{
                int id = c.getInt(c.getColumnIndex(DB.TABLE_FRIENDSHIPS.COL_ID));
                int friendId = c.getInt(c.getColumnIndex(DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID));

                User friend = readUser(friendId);
                friendships.add(new Friendship(id, user, friend, state));

            } while (c.moveToNext());
        }

        c.close();

        return friendships;
    }
}