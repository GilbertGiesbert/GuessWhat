package com.joern.guesswhat.model;

/**
 * Created by joern on 14.04.2015.
 */
public class Friendship {

    private String eMailRequester;
    private String eMailAcceptor;
    private FriendshipState requestState;

    public Friendship(String eMailRequester, String eMailAcceptor, FriendshipState requestState) {
        this.eMailRequester = eMailRequester;
        this.eMailAcceptor = eMailAcceptor;
        this.requestState = requestState;
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

    public FriendshipState getRequestState() {
        return requestState;
    }

    public void setRequestState(FriendshipState requestState) {
        this.requestState = requestState;
    }
}