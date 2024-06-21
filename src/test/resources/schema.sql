create table IF NOT EXISTS notes
(
    id           bigint auto_increment
        primary key,
    content      varchar(255) not null,
    date_created datetime(6)  null,
    last_updated datetime(6)  null
);
