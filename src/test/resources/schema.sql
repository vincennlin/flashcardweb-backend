create table IF NOT EXISTS notes
(
    id           bigint auto_increment
        primary key,
    content      varchar(255) not null,
    date_created datetime(6)  null,
    last_updated datetime(6)  null
);

create table IF NOT EXISTS flashcards
(
    id           bigint auto_increment
        primary key,
    answer       varchar(255) null,
    date_created varchar(255) null,
    extra_info   varchar(255) null,
    last_updated varchar(255) null,
    question     varchar(255) null,
    note_id      bigint       null,
    constraint FKqnyjswltpmxi4ptylxtnfh8ch
        foreign key (note_id) references notes (id)
);

