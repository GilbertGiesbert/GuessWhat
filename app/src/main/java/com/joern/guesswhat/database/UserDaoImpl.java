package com.joern.guesswhat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public class UserDaoImpl implements UserDao{

    private static final String LOG_TAG = UserDaoImpl.class.getName();

    private static final String TABLE_USERS = "users";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    private DatabaseHelper dbHelper;

    public UserDaoImpl(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public boolean createUser(String name, String email, String password) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);

        return -1 != db.insert(TABLE_USERS, null, values);
    }

    @Override
    public User readUser(String email) {

        User userToReturn = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE "
                + COL_EMAIL + " = '" + email + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            String name = c.getString(c.getColumnIndex(COL_NAME));
            String password = c.getString(c.getColumnIndex(COL_PASSWORD));
            userToReturn = new User(name, email, password);
        }

        c.close();

        return userToReturn;
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, newUser.getName());
        values.put(COL_EMAIL, newUser.getEmail());

        return 0 < db.update(TABLE_USERS, values, COL_EMAIL + " = ?", new String[]{oldUser.getEmail()});
    }

    @Override
    public boolean deleteUser(User user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return 0 < db.delete(TABLE_USERS, COL_EMAIL + " = ?", new String[]{user.getEmail()} );
    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = null;

        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            userList = new ArrayList<>();

            do {
                String name = c.getString(c.getColumnIndex(COL_NAME));
                String email = c.getString(c.getColumnIndex(COL_EMAIL));
                String password = c.getString(c.getColumnIndex(COL_PASSWORD));
                userList.add(new User(name, email, password));

            } while (c.moveToNext());
        }

        c.close();

        return userList;
    }

    @Override
    public List<User> getFriends(User user) {

        //TODO
        return null;
    }
}