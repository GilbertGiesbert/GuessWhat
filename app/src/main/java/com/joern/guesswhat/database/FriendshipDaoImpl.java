package com.joern.guesswhat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joern.guesswhat.activity.friends.PendingFriendshipType;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipRequestState;
import com.joern.guesswhat.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public class FriendshipDaoImpl implements FriendshipDao {

    private static final String LOG_TAG = FriendshipDaoImpl.class.getName();

    private static final String TABLE_FRIENDS = "friends";
    private static final String COL_EMAIL_REQUESTER = "eMailRequester";
    private static final String COL_EMAIL_ACCEPTOR = "eMailAcceptor";
    private static final String COL_FRIENDSHIP_REQUEST_STATE = "friendshipRequestState";

    private DatabaseHelper dbHelper;

    public FriendshipDaoImpl(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }


    @Override
    public boolean createFriendship(String eMailRequester, String eMailAcceptor) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_EMAIL_REQUESTER, eMailRequester);
        values.put(COL_EMAIL_ACCEPTOR, eMailAcceptor);
        values.put(COL_FRIENDSHIP_REQUEST_STATE, FriendshipRequestState.PENDING_RECOGNITION.getValue());

        return -1 != db.insert(TABLE_FRIENDS, null, values);
    }

    @Override
    public boolean updateFriendship(User user1, User user2) {
        return false;
    }

    @Override
    public boolean deleteFriendship(User user1, User user2) {
        return false;
    }

    @Override
    public List<Friendship> getAllFriendships(User user) {
        return null;
    }

    @Override
    public List<Friendship> getAllPendingFriendships(User user, PendingFriendshipType type) {


        String colUserMail = PendingFriendshipType.RECEIVED.equals(type) ? COL_EMAIL_ACCEPTOR : COL_EMAIL_REQUESTER;
        String colFriendMail = PendingFriendshipType.RECEIVED.equals(type) ? COL_EMAIL_REQUESTER : COL_EMAIL_ACCEPTOR;;

        List<Friendship> friendships = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FRIENDS +
                " WHERE " + colUserMail + " = '" + user.getEmail() + "'" +
                " AND (" + COL_FRIENDSHIP_REQUEST_STATE + " = " + FriendshipRequestState.PENDING_RECOGNITION.getValue() +
                " OR " + COL_FRIENDSHIP_REQUEST_STATE + " = " + FriendshipRequestState.PENDING_ACCEPTANCE.getValue() + ")";

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do{
                String friendMail = c.getString(c.getColumnIndex(colFriendMail));
                int state = c.getInt(c.getColumnIndex(COL_FRIENDSHIP_REQUEST_STATE));
                FriendshipRequestState friendshipRequestState = FriendshipRequestState.valueOf(state);

                if(PendingFriendshipType.RECEIVED.equals(type)){
                    friendships.add(new Friendship(friendMail, user.getEmail(), friendshipRequestState));
                }else{
                    friendships.add(new Friendship(user.getEmail(), friendMail, friendshipRequestState));
                }

            } while (c.moveToNext());
        }

        c.close();

        return friendships;

    }

    @Override
    public List<Friendship> getAllFriendshipsToAccept(User user) {
        return null;
    }
}