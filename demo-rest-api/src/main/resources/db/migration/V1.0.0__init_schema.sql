CREATE TABLE author
(
    id            bigint GENERATED ALWAYS AS IDENTITY,
    first_name    varchar(255),
    last_name     varchar(255),
    date_of_birth date,
    date_of_death date,
    CONSTRAINT author_pk PRIMARY KEY (id),
    CONSTRAINT author_id_unique UNIQUE (id)
);

CREATE TABLE local_descriptor
(
    id               bigint GENERATED ALWAYS AS IDENTITY,
    source_system    varchar(255),
    local_identifier varchar(255),
    author_id        int,
    CONSTRAINT local_descriptor_pk PRIMARY KEY (id),
    CONSTRAINT local_descriptor_author_fk FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
);