package com.joern.guesswhat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joern.guesswhat.model.FriendshipRequestType;
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

    private static final String TABLE_FRIENDSHIPS = "friendships";
    private static final String COL_EMAIL_REQUESTER = "eMailRequester";
    private static final String COL_EMAIL_ACCEPTOR = "eMailAcceptor";
    private static final String COL_STATE = "state";

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
        values.put(COL_STATE, FriendshipState.REQUEST_SEND.getValue());

        return -1 != db.insert(TABLE_FRIENDSHIPS, null, values);
    }

    @Override
    public boolean updateFriendship(Friendship friendship) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_STATE, friendship.getRequestState().getValue());

        return 0 < db.update(TABLE_FRIENDSHIPS, values, COL_EMAIL_REQUESTER + " = ? AND " + COL_EMAIL_ACCEPTOR + " = ?", new String[]{friendship.getEMailRequester(), friendship.geteMailAcceptor()});
    }

    @Override
    public boolean deleteFriendship(Friendship friendship) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return 0 < db.delete(TABLE_FRIENDSHIPS, COL_EMAIL_REQUESTER + " = ? AND " + COL_EMAIL_ACCEPTOR + " = ?", new String[]{friendship.getEMailRequester(), friendship.geteMailAcceptor()});
    }

    @Override
    public List<Friendship> getFriendships(User user, FriendshipRequestType type, FriendshipState... states){

        String colUserMail = FriendshipRequestType.RECEIVED.equals(type) ? COL_EMAIL_ACCEPTOR : COL_EMAIL_REQUESTER;
        String colFriendMail = FriendshipRequestType.SENT.equals(type) ? COL_EMAIL_ACCEPTOR : COL_EMAIL_REQUESTER;

        StringBuilder columns = new StringBuilder();

        for(int i = 0; i < states.length; i++){

            if(i==0 && states.length > 1){
                columns.append("(");
            }
            columns.append(COL_STATE + " = " + states[i].getValue());

            if(states.length > 1){
                boolean isLastLoop = i == states.length-1;
                if(isLastLoop){
                    columns.append(")");
                }else{
                    columns.append(" OR ");
                }
            }
        }

        String selectQuery =
                "SELECT  * FROM " + TABLE_FRIENDSHIPS +
                " WHERE " + colUserMail + " = '" + user.getEmail() + "'" +
                " AND " + columns
                ;

        List<Friendship> friendships = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do{
                String friendMail = c.getString(c.getColumnIndex(colFriendMail));
                int state = c.getInt(c.getColumnIndex(COL_STATE));
                FriendshipState friendshipState = FriendshipState.valueOf(state);

                if(FriendshipRequestType.SENT.equals(type)){
                    friendships.add(new Friendship(user.getEmail(), friendMail, friendshipState));
                }else{
                    friendships.add(new Friendship(friendMail, user.getEmail(), friendshipState));
                }

            } while (c.moveToNext());
        }

        c.close();

        return friendships;

    }
}