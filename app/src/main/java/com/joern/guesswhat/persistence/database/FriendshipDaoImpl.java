package com.joern.guesswhat.persistence.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joern.guesswhat.constants.DB;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipRequestType;
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
    public boolean createFriendship(String eMailRequester, String eMailAcceptor) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.FRIENDSHIPS.COL_EMAIL_REQUESTER, eMailRequester);
        values.put(DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR, eMailAcceptor);
        values.put(DB.FRIENDSHIPS.COL_STATE, FriendshipState.REQUEST_SEND.getValue());

        return -1 != db.insert(DB.FRIENDSHIPS.TABLE_NAME, null, values);
    }

    @Override
    public boolean updateFriendship(Friendship friendship) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB.FRIENDSHIPS.COL_STATE, friendship.getFriendshipState().getValue());

        return 0 < db.update(DB.FRIENDSHIPS.TABLE_NAME, values, DB.FRIENDSHIPS.COL_EMAIL_REQUESTER + " = ? AND " + DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR + " = ?", new String[]{friendship.getEMailRequester(), friendship.geteMailAcceptor()});
    }

    @Override
    public boolean deleteFriendship(Friendship friendship) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return 0 < db.delete(DB.FRIENDSHIPS.TABLE_NAME, DB.FRIENDSHIPS.COL_EMAIL_REQUESTER + " = ? AND " + DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR + " = ?", new String[]{friendship.getEMailRequester(), friendship.geteMailAcceptor()});
    }

    @Override
    public List<Friendship> getFriendships(User user, FriendshipRequestType type, FriendshipState... states){

//        select * from users
//        join (select * from friends where friends.req=2) as f1
//        on users.id=f1.acc
//
//        union
//
//        select * from users
//        join (select * from friends where friends.acc=2) as f2
//        on users.id=f2.req


        String colUserMail = FriendshipRequestType.RECEIVED.equals(type) ? DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR : DB.FRIENDSHIPS.COL_EMAIL_REQUESTER;
        String colFriendMail = FriendshipRequestType.SENT.equals(type) ? DB.FRIENDSHIPS.COL_EMAIL_ACCEPTOR : DB.FRIENDSHIPS.COL_EMAIL_REQUESTER;

        StringBuilder columns = new StringBuilder();

        for(int i = 0; i < states.length; i++){

            if(i==0 && states.length > 1){
                columns.append("(");
            }

            columns.append(DB.FRIENDSHIPS.COL_STATE);
            columns.append(" = ");
            columns.append(states[i].getValue());

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
                "SELECT  * FROM " + DB.FRIENDSHIPS.TABLE_NAME +
                " WHERE " + colUserMail + " = '" + user.getEmail() + "'" +
                " AND " + columns
                ;

        List<Friendship> friendships = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){

            do{
                String friendMail = c.getString(c.getColumnIndex(colFriendMail));
                int state = c.getInt(c.getColumnIndex(DB.FRIENDSHIPS.COL_STATE));
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