package com.joern.guesswhat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joern.guesswhat.constants.DB;
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
    public boolean createUser(String name, String email, String password) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.USERS.COL_NAME, name);
        values.put(DB.USERS.COL_EMAIL, email);
        values.put(DB.USERS.COL_PASSWORD, password);

        return -1 != db.insert(DB.USERS.TABLE_NAME, null, values);
    }

    @Override
    public User readUser(String email) {

        User userToReturn = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB.USERS.TABLE_NAME + " WHERE "
                + DB.USERS.COL_EMAIL + " = '" + email + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            String name = c.getString(c.getColumnIndex(DB.USERS.COL_NAME));
            String password = c.getString(c.getColumnIndex(DB.USERS.COL_PASSWORD));
            userToReturn = new User(name, email, password);
        }

        c.close();

        return userToReturn;
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.USERS.COL_NAME, newUser.getName());
        values.put(DB.USERS.COL_EMAIL, newUser.getEmail());

        return 0 < db.update(DB.USERS.TABLE_NAME, values, DB.USERS.COL_EMAIL + " = ?", new String[]{oldUser.getEmail()});
    }

    @Override
    public boolean deleteUser(User user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return 0 < db.delete(DB.USERS.TABLE_NAME, DB.USERS.COL_EMAIL + " = ?", new String[]{user.getEmail()} );
    }

    public List<User> getAllUsers() {

        List<User> userList = null;

        String selectQuery = "SELECT * FROM " + DB.USERS.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            userList = new ArrayList<>();

            do {
                String name = c.getString(c.getColumnIndex(DB.USERS.COL_NAME));
                String email = c.getString(c.getColumnIndex(DB.USERS.COL_EMAIL));
                String password = c.getString(c.getColumnIndex(DB.USERS.COL_PASSWORD));
                userList.add(new User(name, email, password));

            } while (c.moveToNext());
        }

        c.close();

        return userList;
    }

    public List<User> getFriendships(User user){

//        SELECT employees.employee_id, employees.last_name, positions.title
//        FROM employees
//        INNER JOIN positions
//        ON employees.position_id = positions.position_id;

//        select * from users inner join friendships on users.email = friendships.eMailRequester where (friendships.eMailAcceptor = 'b0@' and friendships.state = 3);
//        select * from users inner join friendships on users.email = friendships.emailAcceptor where (friendships.eMailRequester = 'b0@' and friendships.state = 3);

        List<User> userList = new ArrayList<>();

        String selectQueryAcceptedFriends =
                "SELECT "+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_NAME+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_EMAIL+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_PASSWORD+
                " FROM " + DB.USERS.TABLE_NAME + " INNER JOIN " + DB.FRIENDSHIPS.TABLE_NAME +
                " ON " + DB.USERS.COL_EMAIL + " = " + DB.FRIENDSHIPS.COL_EMAIL_REQUESTER +
                " WHERE  (" + DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR + " = '" + user.getEmail() + "'" +
                " AND " + DB.FRIENDSHIPS.COL_STATE + " = " + FriendshipState.ACTIVE.getValue() + ")";

        String selectQueryRequestedFriends =
                "SELECT "+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_NAME+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_EMAIL+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_PASSWORD+
                " FROM " + DB.USERS.TABLE_NAME + " INNER JOIN " + DB.FRIENDSHIPS.TABLE_NAME +
                " ON " + DB.USERS.COL_EMAIL + " = " + DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR +
                " WHERE  (" + DB.FRIENDSHIPS.COL_EMAIL_REQUESTER + " = '" + user.getEmail() + "'" +
                " AND " + DB.FRIENDSHIPS.COL_STATE + " = " + FriendshipState.ACTIVE.getValue() + ")";


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(selectQueryAcceptedFriends, null);
        userList.addAll(getFriendships(user, c));

        c = db.rawQuery(selectQueryRequestedFriends, null);
        userList.addAll(getFriendships(user, c));

        c.close();
        return userList;
    }

    public List<User> getFriendships(User user, Cursor c){

        List<User> userList = new ArrayList<>();

        if (c.moveToFirst()) {

            userList = new ArrayList<>();

            do {
                String email = c.getString(c.getColumnIndex(DB.USERS.COL_EMAIL));
                if(email != null && !email.equals(user.getEmail())){

                    String name = c.getString(c.getColumnIndex(DB.USERS.COL_NAME));
                    String password = c.getString(c.getColumnIndex(DB.USERS.COL_PASSWORD));
                    userList.add(new User(name, email, password));
                }
            } while (c.moveToNext());
        }
        return userList;
    }
}