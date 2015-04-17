package com.joern.guesswhat.model;

/**
 * Created by joern on 14.04.2015.
 */
public class Friendship {

    private String eMailRequester;
    private String eMailAcceptor;
    private FriendshipState friendshipState;

    public Friendship(String eMailRequester, String eMailAcceptor, FriendshipState friendshipState) {
        this.eMailRequester = eMailRequester;
        this.eMailAcceptor = eMailAcceptor;
        this.friendshipState = friendshipState;
    }

    public String getEMailRequester() {
        return eMailRequester;
    }

    public void setEMailRequester(String eMailRequester) {
        this.eMailRequester = eMailRequester;
    }

    public String geteMailAcceptor() {
        return eMailAcceptor;
    }

    public void setEMailAcceptor(String eMailAcceptor) {
        this.eMailAcceptor = eMailAcceptor;
    }

    public FriendshipState getFriendshipState() {
        return friendshipState;
    }

    public void setFriendshipState(FriendshipState friendshipState) {
        this.friendshipState = friendshipState;
    }
}