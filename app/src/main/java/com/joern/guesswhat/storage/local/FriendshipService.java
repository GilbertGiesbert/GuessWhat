package com.joern.guesswhat.storage.local;

import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 08.07.2015.
 */
public interface FriendshipService {

    public Friendship getFriendship(User user, User friend);
    public boolean requestFriendship(User user, User friend);
    public boolean acceptFriendship(Friendship friendship);
    public boolean deleteFriendship(Friendship friendship);

    public List<Friendship> getFriendshipsActive(User user);
    public List<Friendship> getFriendshipInvites(User user);
    public List<Friendship> getFriendshipRequests(User user);
}