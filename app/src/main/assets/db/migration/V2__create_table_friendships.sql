CREATE TABLE IF NOT EXISTS friendships

    (   id INTEGER PRIMARY KEY AUTOINCREMENT,

        eMailRequester TEXT NOT NULL,
        eMailAcceptor TEXT NOT NULL,
        state INTEGER NOT NULL
    )