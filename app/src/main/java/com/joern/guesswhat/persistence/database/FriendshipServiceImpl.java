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

    private UserDao userDao;
    private FriendshipDao friendshipDao;

    public FriendshipServiceImpl(Context context){
        userDao = new UserDaoImpl(context);
        friendshipDao = new FriendshipDaoImpl(context);
    }

    @Override
    public Friendship getFriendship(User user, User friend) {
        return friendshipDao.readFriendship(user, friend);
    }

    @Override
    public boolean requestFriendship(User user, User friend) {

        // create friendship for user
        boolean success = friendshipDao.createFriendship(user, friend, FriendshipState.REQUEST);

        if(success){

            // create corresponding friendship for friend
            success = friendshipDao.createFriendship(friend, user, FriendshipState.INVITE);
        }

        return success;
    }

    @Override
    public boolean acceptFriendship(Friendship friendship) {

        // accept user's friendship
        Friendship fsUser = friendshipDao.readFriendship(friendship.getUser(), friendship.getFriend());
        fsUser.setFriendshipState(FriendshipState.ACTIVE);
        boolean success = friendshipDao.updateFriendship(fsUser);

        if(success){

            // accept corresponding friend's friendship
            Friendship fsFriend = friendshipDao.readFriendship(friendship.getFriend(), friendship.getUser());
            fsFriend.setFriendshipState(FriendshipState.ACTIVE);
            success = friendshipDao.updateFriendship(fsFriend);
        }

        return success;
    }

    @Override
    public boolean deleteFriendship(Friendship friendship) {

        // delete user's friendship
        Friendship fsUser = friendshipDao.readFriendship(friendship.getUser(), friendship.getFriend());
        boolean success = friendshipDao.deleteFriendship(fsUser);

        if(success){

            // delete corresponding friend's friendship
            Friendship fsFriend = friendshipDao.readFriendship(friendship.getFriend(), friendship.getUser());
            success = friendshipDao.deleteFriendship(fsFriend);
        }

        return success;
    }

    @Override
    public List<Friendship> getFriendshipsActive(User user) {
        return userDao.getFriendships(user, FriendshipState.ACTIVE);
    }

    @Override
    public List<Friendship> getFriendshipInvites(User user) {
        return userDao.getFriendships(user, FriendshipState.INVITE);
    }

    @Override
    public List<Friendship> getFriendshipRequests(User user) {
        return userDao.getFriendships(user, FriendshipState.REQUEST);
    }
}