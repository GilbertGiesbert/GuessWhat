package com.joern.guesswhat.constants;

/**
 * Created by joern on 17.04.2015.
 */
public class DB {

    public static final class LOCAL_USERS{

        public static final String TABLE_NAME = "local_users";

        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORDHASH = "password_hash";
    }

    public static final class REGISTERED_USERS{

        public static final String TABLE_NAME = "registered_users";

        public static final String COL_ID = "id";
        public static final String COL_NAME = "name";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORDHASH = "password_hash";
    }


    public static final class FRIENDSHIPS{

        public static final String TABLE_NAME = "friendships";

        public static final String COL_EMAIL_REQUESTER = "eMailRequester";
        public static final String COL_EMAIL_ACCEPTOR = "eMailAcceptor";
        public static final String COL_STATE = "state";
    }
}