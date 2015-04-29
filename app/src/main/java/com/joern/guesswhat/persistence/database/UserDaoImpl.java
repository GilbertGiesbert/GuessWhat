package com.joern.guesswhat.persistence.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    public boolean createUser(String name, String email, int passwordHash) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.USERS.COL_NAME, name);
        values.put(DB.USERS.COL_EMAIL, email);
        values.put(DB.USERS.COL_PASSWORD_HASH, passwordHash);

        return -1 != db.insert(DB.USERS.TABLE_NAME, null, values);
    }

    @Override
    public User readUser(String name) {

        User userToReturn = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB.USERS.TABLE_NAME + " WHERE "
                + DB.USERS.COL_NAME + " = '" + name + "' COLLATE NOCASE";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            int id = c.getInt(c.getColumnIndex(DB.USERS.COL_ID));
            String stableAlias = c.getString(c.getColumnIndex(DB.USERS.COL_STABLE_ALIAS));
            // name was read with COLLATE NOCASE
            // so set name to original db value
            name = c.getString(c.getColumnIndex(DB.USERS.COL_NAME));
            String email = c.getString(c.getColumnIndex(DB.USERS.COL_EMAIL));
            int passwordHash = c.getInt(c.getColumnIndex(DB.USERS.COL_PASSWORD_HASH));
            userToReturn = new User(id, stableAlias, name, email, passwordHash);

        }else{
            Log.d(LOG_TAG, "no user for name="+name);
        }

        c.close();

        return userToReturn;
    }

    public User getUserByMail(String email){
        User userToReturn = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DB.USERS.TABLE_NAME + " WHERE "
                + DB.USERS.COL_EMAIL + " = '" + email + "' COLLATE NOCASE";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            int id = c.getInt(c.getColumnIndex(DB.USERS.COL_ID));
            String stableAlias = c.getString(c.getColumnIndex(DB.USERS.COL_STABLE_ALIAS));
            String name = c.getString(c.getColumnIndex(DB.USERS.COL_NAME));
            // email was read with COLLATE NOCASE
            // so set email to original db value
            email = c.getString(c.getColumnIndex(DB.USERS.COL_EMAIL));
            int passwordHash = c.getInt(c.getColumnIndex(DB.USERS.COL_PASSWORD_HASH));
            userToReturn = new User(id, stableAlias, name, email, passwordHash);

        }else{
            Log.d(LOG_TAG, "no user for email="+email);
        }

        c.close();

        return userToReturn;
    }

    @Override
    public boolean updateUser(User user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.USERS.COL_STABLE_ALIAS, user.getStableAlias());
        values.put(DB.USERS.COL_NAME, user.getName());
        values.put(DB.USERS.COL_EMAIL, user.getEmail());
        values.put(DB.USERS.COL_PASSWORD_HASH, user.getPasswordHash());

        return 0 < db.update(DB.USERS.TABLE_NAME, values, DB.USERS.COL_ID + " = ?", new String[]{""+user.getId()});
    }

    @Override
    public boolean deleteUser(User user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return 0 < db.delete(DB.USERS.TABLE_NAME, DB.USERS.COL_ID + " = ?", new String[]{""+user.getId()} );
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
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_ID+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_STABLE_ALIAS+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_NAME+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_EMAIL+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_PASSWORD_HASH+
                " FROM " + DB.USERS.TABLE_NAME + " INNER JOIN " + DB.FRIENDSHIPS.TABLE_NAME +
                " ON " + DB.USERS.COL_EMAIL + " = " + DB.FRIENDSHIPS.COL_EMAIL_REQUESTER +
                " WHERE  (" + DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR + " = '" + user.getEmail() + "'" +
                " AND " + DB.FRIENDSHIPS.COL_STATE + " = " + FriendshipState.ACTIVE.getValue() + ")";

        String selectQueryRequestedFriends =
                "SELECT "+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_ID+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_STABLE_ALIAS+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_NAME+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_EMAIL+","+
                DB.USERS.TABLE_NAME+"."+DB.USERS.COL_PASSWORD_HASH+
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

                    int id = c.getInt(c.getColumnIndex(DB.USERS.COL_ID));
                    String stableAlias = c.getString(c.getColumnIndex(DB.USERS.COL_STABLE_ALIAS));
                    String name = c.getString(c.getColumnIndex(DB.USERS.COL_NAME));
                    int passwordHash = c.getInt(c.getColumnIndex(DB.USERS.COL_PASSWORD_HASH));
                    userList.add(new User(id, stableAlias, name, email, passwordHash));
                }
            } while (c.moveToNext());
        }
        return userList;
    }
}