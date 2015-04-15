CREATE TABLE IF NOT EXISTS friends

    (   id INTEGER PRIMARY KEY AUTOINCREMENT,

        eMailRequester TEXT NOT NULL,
        eMailAcceptor TEXT NOT NULL,
        friendshipRequestState INTEGER NOT NULL
    )