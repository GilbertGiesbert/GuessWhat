package com.joern.guesswhat.constants;

/**
 * Created by joern on 17.04.2015.
 */
public class DB {

    public static final class TABLE_USERS {

        public static final String NAME = "users";

        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORD_HASH = "password_hash";
    }

    public static final class TABLE_FRIENDSHIPS {

        public static final String NAME = "friendships";

        public static final String COL_ID = "id";
        public static final String COL_USER_ID = "user_id";
        public static final String COL_FRIEND_ID = "friend_id";
        public static final String COL_STATE = "state";
    }
}