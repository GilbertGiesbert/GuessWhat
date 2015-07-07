package com.joern.guesswhat.persistence.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joern.guesswhat.constants.DB;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

import java.util.ArrayList;
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
    public boolean createFriendship(User user, User friend) {

        // TODO: check if fs was already requested by friend
        // if so then accept

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.TABLE_FRIENDSHIPS.COL_USER_ID, user.getId());
        values.put(DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID, friend.getId());
        values.put(DB.TABLE_FRIENDSHIPS.COL_STATE, FriendshipState.REQUEST_SENT.getValue());

        boolean createdFriendship = -1 != db.insert(DB.TABLE_FRIENDSHIPS.NAME, null, values);
        if(createdFriendship){

            // create friend's friendship

            values = new ContentValues();
            values.put(DB.TABLE_FRIENDSHIPS.COL_USER_ID, friend.getId());
            values.put(DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID, user.getId());
            values.put(DB.TABLE_FRIENDSHIPS.COL_STATE, FriendshipState.REQUEST_RECEIVED.getValue());

            db.insert(DB.TABLE_FRIENDSHIPS.NAME, null, values);

            createdFriendship = -1 != db.insert(DB.TABLE_FRIENDSHIPS.NAME, null, values);
        }
        return createdFriendship;
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

        boolean deletedFriendship = 0 < db.delete(DB.TABLE_FRIENDSHIPS.NAME, DB.TABLE_FRIENDSHIPS.COL_ID + " = ?", new String[]{""+friendship.getId()});
        if(deletedFriendship){
            // delete friend's friendship
            deletedFriendship = 0 < db.delete(DB.TABLE_FRIENDSHIPS.NAME, DB.TABLE_FRIENDSHIPS.COL_USER_ID + " = ? AND "+ DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID + " = ?", new String[]{""+friendship.getFriend().getId(),""+friendship.getUser().getId()});
        }
        return deletedFriendship;
    }

    @Override
    public List<Friendship> getFriendships(User user, FriendshipState state){

        List<Friendship> friendships = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String whereClause = DB.TABLE_FRIENDSHIPS.COL_USER_ID + " = ? AND "+user.getId()+" = ?";
        String[] whereArgs = new String[]{""+user.getId(),""+state.getValue()};

        Cursor c = db.query(DB.TABLE_FRIENDSHIPS.NAME, null, whereClause, whereArgs, null, null, null);

        if(c.moveToFirst()){

            do{
                int id = c.getInt(c.getColumnIndex(DB.TABLE_FRIENDSHIPS.COL_ID));
                int friendId = c.getInt(c.getColumnIndex(DB.TABLE_FRIENDSHIPS.COL_FRIEND_ID));

//                Friendship fs = new Friendship(id, user, )


            } while (c.moveToNext());
        }

        c.close();

        return friendships;
    }

    @Override
    public List<Friendship> getNewFriendships(User user){

        List<Friendship> friendships = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String whereClause = DB.TABLE_FRIENDSHIPS.COL_USER_ID + " = ? AND "+user.getId()+" = ?";
        String[] whereArgs = new String[]{""+user.getId(),""+FriendshipState.REQUEST_RECEIVED.getValue()};

        Cursor c = db.query(DB.TABLE_FRIENDSHIPS.NAME, null, whereClause, whereArgs, null, null, null);

        if(c.moveToFirst()){

            do{
                int id = c.getInt(c.getColumnIndex(DB.TABLE_FRIENDSHIPS.COL_ID));
                int friendId = c.getInt(c.getColumnIndex(DB.TABLE_FRIENDSHIPS.COL_USER_ID));

//                Friendship fs = new Friendship(id, user, )


            } while (c.moveToNext());
        }

        c.close();

        return friendships;
    }
}