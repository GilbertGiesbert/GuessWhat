CREATE TABLE IF NOT EXISTS friendships (

    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL UNIQUE COLLATE NOCASE,
    is_private INTEGER NOT NULL,
    solution TEXT NOT NULL,
    hints TEXT NOT NULL
    )