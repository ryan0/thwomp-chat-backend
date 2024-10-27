CREATE TABLE users
(
    id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    email varchar(320) NOT NULL UNIQUE,
    created_at timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE chats
(
    id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name varchar(255) NOT NULL,
    created_at timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE user_chat_registry
(
    id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    created_at timestamp NOT NULL DEFAULT NOW(),
    user_id integer NOT NULL,
    chat_id integer NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_chat FOREIGN KEY (chat_id)
        REFERENCES chats(id)
        ON DELETE CASCADE
);


CREATE TABLE messages
(
    id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    message_text varchar(2000) NOT NULL,
    sent_at timestamp NOT NULL DEFAULT NOW(),
    user_id integer NOT NULL,
    chat_id integer NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL,

    CONSTRAINT fk_chat FOREIGN KEY (chat_id)
        REFERENCES chats(id)
        ON DELETE SET NULL
);
