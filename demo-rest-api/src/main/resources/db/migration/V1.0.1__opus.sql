CREATE TABLE opus
(
    id               bigint GENERATED ALWAYS AS IDENTITY,
    title            varchar(255),
    publication_date date,
    periodical_name  varchar(255),
    author_id        int,
    dtype             varchar(31),
    CONSTRAINT opus_pk PRIMARY KEY (id),
    CONSTRAINT opus_author_fk FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
);