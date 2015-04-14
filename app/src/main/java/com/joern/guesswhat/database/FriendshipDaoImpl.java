package com.joern.guesswhat.database;

import android.content.Context;

import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public class FriendshipDaoImpl implements FriendshipDao {

    private static final String LOG_TAG = FriendshipDaoImpl.class.getName();

    private static final String TABLE_FRIENDS = "friends";
    private static final String COL_FRIENDSHIP_REQUESTER = "friendshipRequester";
    private static final String COL_FRIENDSHIP_ACCEPTOR = "friendshipAcceptor";
    private static final String COL_FRIENDSHIP_REQUEST_STATE = "friendshipRequestState";
    private static final String COL_FRIENDSHIP_REQUEST_EXPIRE_MONTH = "friendshipRequestExpireMonth";

    private DatabaseHelper dbHelper;

    public FriendshipDaoImpl(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }


    @Override
    public boolean createFriendship(User friendshipRequester, User friendshipAcceptor) {
        return false;
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
    public List<Friendship> getAllFriendshipsRequestedAndPending(User user) {
        return null;
    }

    @Override
    public List<Friendship> getAllFriendshipsRequestedButNotAccepted(User user) {
        return null;
    }

    @Override
    public List<Friendship> getAllFriendshipsToAccept(User user) {
        return null;
    }
}