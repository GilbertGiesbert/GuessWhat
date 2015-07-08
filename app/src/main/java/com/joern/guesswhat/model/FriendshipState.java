package com.joern.guesswhat.model;

public enum FriendshipState {

    INVITE(1),
    ACTIVE(2),
    REQUEST(3);

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