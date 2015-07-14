package com.joern.guesswhat.storage.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joern.guesswhat.constants.DB;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public class FriendshipDaoImpl implements FriendshipDao {

    private static final String LOG_TAG = FriendshipDaoImpl.class.getName();

    private DatabaseHelper dbHelper;

    public FriendshipDaoImpl(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public boolean createFriendship(User user, User friend, FriendshipState state) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.TABLE_FRIENDSHIPS.COL_USER_ID, user.getId());
        values.put(DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID, friend.getId());
        values.put(DB.TABLE_FRIENDSHIPS.COL_STATE, state.getValue());

        return -1 != db.insert(DB.TABLE_FRIENDSHIPS.NAME, null, values);
    }

    @Override
    public Friendship readFriendship(User user, User friend) {

        String whereClause = DB.TABLE_FRIENDSHIPS.COL_USER_ID + " = ? AND "+ DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID +" = ?";
        String[] whereArgs = new String[]{""+user.getId(),""+friend.getId()};

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(DB.TABLE_FRIENDSHIPS.NAME, null, whereClause, whereArgs, null, null, null);

        Friendship fs = null;

        if(c.moveToFirst()){

            int id = c.getInt(c.getColumnIndex(DB.TABLE_FRIENDSHIPS.COL_ID));
            int state = c.getInt(c.getColumnIndex(DB.TABLE_FRIENDSHIPS.COL_STATE));

            fs = new Friendship(id, user, friend, FriendshipState.valueOf(state));
        }
        c.close();
        return fs;
    }

    @Override
    public boolean updateFriendship(Friendship friendship) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.TABLE_FRIENDSHIPS.COL_USER_ID, friendship.getUser().getId());
        values.put(DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID, friendship.getFriend().getId());
        values.put(DB.TABLE_FRIENDSHIPS.COL_STATE, friendship.getFriendshipState().getValue());

        return 0 < db.update(DB.TABLE_FRIENDSHIPS.NAME, values, DB.TABLE_FRIENDSHIPS.COL_ID + " = ?", new String[]{"" + friendship.getId()});
    }

    @Override
    public boolean deleteFriendship(Friendship friendship) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return 0 < db.delete(DB.TABLE_FRIENDSHIPS.NAME, DB.TABLE_FRIENDSHIPS.COL_ID + " = ?", new String[]{"" + friendship.getId()});
    }

    @Override
    public List<Friendship> getFriendships(User user, FriendshipState state){

        List<Friendship> friendships = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql =

            "SELECT u."+DB.TABLE_USERS.COL_ID + ", " +
            "u."+DB.TABLE_USERS.COL_NAME + ", " +
            "u."+DB.TABLE_USERS.COL_EMAIL + ", " +
            "f."+DB.TABLE_FRIENDSHIPS.COL_ID + " " +

            "FROM " + DB.TABLE_USERS.NAME + " u " +

            "JOIN (" +

            "SELECT * " +
            "FROM "+DB.TABLE_FRIENDSHIPS.NAME + " " +
            "WHERE "+DB.TABLE_FRIENDSHIPS.COL_USER_ID+" = ? " +
            "AND "+DB.TABLE_FRIENDSHIPS.COL_STATE+" = ?" +

            ") f " +

            "ON u."+DB.TABLE_USERS.COL_ID +" = f."+DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID;

        String[] selectionArgs = new String[]{""+user.getId(), ""+state.getValue()};

        Cursor c = db.rawQuery(sql, selectionArgs);

        if(c.moveToFirst()){

            do{

                // note: c.getColumnIndex(columnName) is not working if columnName contains dot(s)

                List columnNames = Arrays.asList(c.getColumnNames());

                int id = c.getInt(columnNames.indexOf("u." + DB.TABLE_USERS.COL_ID));
                String name = c.getString(columnNames.indexOf("u." + DB.TABLE_USERS.COL_NAME));
                String email = c.getString(columnNames.indexOf("u." + DB.TABLE_USERS.COL_EMAIL));

                int friendshipId = c.getInt(columnNames.indexOf("f." + DB.TABLE_FRIENDSHIPS.COL_ID));

                User friend = new User(id, name, email);
                friendships.add(new Friendship(friendshipId, user, friend, state));

            } while (c.moveToNext());
        }

        c.close();

        return friendships;
    }
}