CREATE TABLE IF NOT EXISTS t_attachment (
    id      serial NOT NULL,
    content bytea NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,

    CONSTRAINT t_attachment_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_user (
    id                    serial NOT NULL,
    login                 varchar(50) NOT NULL UNIQUE,
    password              varchar(255) NOT NULL,
    name                  varchar(255),
    failed_login_attempts smallint,
    blocked_till          datetime,
    current_refresh_token varchar(50),
    avatar_id             int,
    created               timestamp without time zone NOT NULL,
    updated               timestamp without time zone NOT NULL,

    CONSTRAINT t_user_pk PRIMARY KEY (id),
    CONSTRAINT t_attachment_fk FOREIGN KEY (avatar_id) REFERENCES t_attachment (id)
);

CREATE TABLE IF NOT EXISTS t_chat (
    id      serial NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,

    CONSTRAINT t_chat_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS t_chat_user (
    chat_id int NOT NULL,
    user_id int NOT NULL,

    CONSTRAINT t_chat_user PRIMARY KEY (chat_id, user_id),
    CONSTRAINT t_chat_fk   FOREIGN KEY (chat_id) REFERENCES t_chat (id),
    CONSTRAINT t_user_id   FOREIGN KEY (user_id) REFERENCES t_user (id)
);

CREATE TABLE IF NOT EXISTS t_message (
    id           serial NOT NULL,
    text         text NOT NULL,
    chat_id      int NOT NULL,
    from_user_id int NOT NULL,
    created      timestamp without time zone NOT NULL,
    updated      timestamp without time zone NOT NULL,

    CONSTRAINT t_message_id PRIMARY KEY (id),
    CONSTRAINT t_chat_fk FOREIGN KEY (chat_id) REFERENCES t_chat (id),
    CONSTRAINT t_user_fk FOREIGN KEY (from_user_id) REFERENCES t_user (id)
);