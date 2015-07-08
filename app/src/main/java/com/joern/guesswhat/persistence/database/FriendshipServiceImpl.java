package com.joern.guesswhat.persistence.database;

import android.content.Context;

import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 08.07.2015.
 */
public class FriendshipServiceImpl implements FriendshipService {

    FriendshipDao dao;

    public FriendshipServiceImpl(Context context){
        dao = new FriendshipDaoImpl(context);
    }

    @Override
    public Friendship getFriendship(User user, User friend) {
        return dao.readFriendship(user, friend);
    }

    @Override
    public boolean requestFriendship(User user, User friend) {

        // create friendship for user
        boolean success = dao.createFriendship(user, friend, FriendshipState.REQUEST);

        if(success){

            // create corresponding friendship for friend
            success = dao.createFriendship(friend, user, FriendshipState.INVITE);
        }

        return success;
    }

    @Override
    public boolean acceptFriendship(Friendship friendship) {

        // accept user's friendship
        Friendship fsUser = dao.readFriendship(friendship.getUser(), friendship.getFriend());
        fsUser.setFriendshipState(FriendshipState.ACTIVE);
        boolean success = dao.updateFriendship(fsUser);

        if(success){

            // accept corresponding friend's friendship
            Friendship fsFriend = dao.readFriendship(friendship.getFriend(), friendship.getUser());
            fsFriend.setFriendshipState(FriendshipState.ACTIVE);
            success = dao.updateFriendship(fsFriend);
        }

        return success;
    }

    @Override
    public boolean deleteFriendship(Friendship friendship) {

        // delete user's friendship
        Friendship fsUser = dao.readFriendship(friendship.getUser(), friendship.getFriend());
        boolean success = dao.deleteFriendship(fsUser);

        if(success){

            // delete corresponding friend's friendship
            Friendship fsFriend = dao.readFriendship(friendship.getFriend(), friendship.getUser());
            success = dao.deleteFriendship(fsFriend);
        }

        return success;
    }

    @Override
    public List<Friendship> getFriendshipsActive(User user) {
        return dao.getFriendships(user, FriendshipState.ACTIVE);
    }

    @Override
    public List<Friendship> getFriendshipInvites(User user) {
        return dao.getFriendships(user, FriendshipState.INVITE);
    }

    @Override
    public List<Friendship> getFriendshipRequests(User user) {
        return dao.getFriendships(user, FriendshipState.REQUEST);
    }
}