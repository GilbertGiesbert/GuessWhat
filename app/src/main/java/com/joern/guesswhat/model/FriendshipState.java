package com.joern.guesswhat.model;

public enum FriendshipState {

    REQUEST_SENT(1),

    REQUEST_RECEIVED(2),
    REQUEST_RECEIVED_PENDING(3),

    ACTIVE(4);

    private int value;

    private FriendshipState(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FriendshipState valueOf(int input){

        for(FriendshipState state: FriendshipState.values()){
            if(state.getValue() == input){
                return state;
            }
        }
        return null;
    }
}