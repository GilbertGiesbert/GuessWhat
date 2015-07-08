package com.joern.guesswhat.model;

/**
 * Created by joern on 14.04.2015.
 */
public class Friendship {

    private int id;
    private User user;
    private User friend;
    private FriendshipState friendshipState;

    public Friendship(int id, User user, User friend, FriendshipState friendshipState) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.friendshipState = friendshipState;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public FriendshipState getFriendshipState() {
        return friendshipState;
    }

    public void setFriendshipState(FriendshipState friendshipState) {
        this.friendshipState = friendshipState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}