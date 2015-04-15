package com.joern.guesswhat.model;

public enum FriendshipRequestState {

    ACCEPTED(1), REJECTED(2), PENDING(3), CANCELLED(4);

    private int value;

    private FriendshipRequestState(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}