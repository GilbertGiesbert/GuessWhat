CREATE TABLE IF NOT EXISTS friends

    (   id INTEGER PRIMARY KEY AUTOINCREMENT,

        friendshipRequester INTEGER NOT NULL,
        friendshipAcceptor INTEGER NOT NULL,
        friendshipRequestState INTEGER NOT NULL,
        friendshipRequestExpireMonth INTEGER NOT NULL,

         FOREIGN KEY(friendshipRequester) REFERENCES users(id),
         FOREIGN KEY(friendshipAcceptor) REFERENCES users(id)
         )