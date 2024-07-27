ALTER TABLE multiple_choice DROP CONSTRAINT FKratayhbwedodds55sn2dxori7;
ALTER TABLE flashcards DROP CONSTRAINT FK8ff9yo2jfbhcasyg60yk7v93a;

DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles_authorities;
DROP TABLE IF EXISTS true_false_answers;
DROP TABLE IF EXISTS in_blank_answers;
DROP TABLE IF EXISTS options;
DROP TABLE IF EXISTS multiple_choice;
DROP TABLE IF EXISTS fill_in_the_blank;
DROP TABLE IF EXISTS short_answers;
DROP TABLE IF EXISTS flashcards_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS review_states;
DROP TABLE IF EXISTS review_infos;
DROP TABLE IF EXISTS flashcards;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

create table if not exists authorities
(
    id   bigint auto_increment
        primary key,
    name varchar(20) not null
);

create table if not exists flashcards
(
    id             bigint auto_increment
        primary key,
    date_created   datetime(6)                                                                 null,
    extra_info     varchar(255)                                                                null,
    last_updated   datetime(6)                                                                 null,
    note_id        bigint                                                                      not null,
    question       varchar(3000)                                                               null,
    type           enum ('FILL_IN_THE_BLANK', 'MULTIPLE_CHOICE', 'SHORT_ANSWER', 'TRUE_FALSE') null,
    user_id        bigint                                                                      not null,
    review_info_id bigint                                                                      null,
    constraint UKrswb9h16vb7fl40i2rrt2agqx
        unique (review_info_id)
);

create table if not exists fill_in_the_blank
(
    full_answer varchar(3000) null,
    id          bigint        not null
        primary key,
    constraint FKbbeectun2p4f42letdl5ejrpu
        foreign key (id) references flashcards (id)
);

create table if not exists in_blank_answers
(
    id           bigint auto_increment
        primary key,
    text         varchar(255) null,
    flashcard_id bigint       null,
    constraint FKb6vs7hp860udja6cx4ejf0o7r
        foreign key (flashcard_id) references fill_in_the_blank (id)
);

create table if not exists multiple_choice
(
    id               bigint not null
        primary key,
    answer_option_id bigint null,
    constraint UK6y6qi75t0gthc962rmehlngbv
        unique (answer_option_id),
    constraint FK9ebpq5knpcxlxi7c7n384bved
        foreign key (id) references flashcards (id)
);

create table if not exists notes
(
    id           bigint auto_increment
        primary key,
    content      varchar(3000) not null,
    date_created datetime(6)   null,
    last_updated datetime(6)   null,
    user_id      bigint        not null
);

create table if not exists options
(
    id           bigint auto_increment
        primary key,
    text         varchar(255) null,
    flashcard_id bigint       null,
    constraint FKgbcvtq8fmx8y6s8lv9tovnb4k
        foreign key (flashcard_id) references multiple_choice (id)
);

alter table if exists multiple_choice
    add constraint FKratayhbwedodds55sn2dxori7
        foreign key (answer_option_id) references options (id);

create table if not exists review_infos
(
    id              bigint auto_increment
        primary key,
    last_reviewed   datetime(6) null,
    next_review     datetime(6) null,
    review_interval int         null,
    review_level    int         null,
    flashcard_id    bigint      null,
    constraint UKh92mui4ienhmkwik8saqksmjk
        unique (flashcard_id),
    constraint FKo5f0lfi18hit1usmuhjke221l
        foreign key (flashcard_id) references flashcards (id)
);

alter table if exists flashcards
    add constraint FK8ff9yo2jfbhcasyg60yk7v93a
        foreign key (review_info_id) references review_infos (id);

create table if not exists review_states
(
    id              bigint auto_increment
        primary key,
    date_reviewed   datetime(6)                            null,
    last_reviewed   datetime(6)                            null,
    next_review     datetime(6)                            null,
    review_interval int                                    null,
    review_level    int                                    null,
    review_option   enum ('AGAIN', 'EASY', 'GOOD', 'HARD') null,
    review_info_id  bigint                                 null,
    constraint FKb37tm8p76hv7e0gfx4jfe87qn
        foreign key (review_info_id) references review_infos (id)
);

create table if not exists roles
(
    id   bigint auto_increment
        primary key,
    name varchar(20) not null
);

create table if not exists roles_authorities
(
    role_id      bigint not null,
    authority_id bigint not null,
    primary key (role_id, authority_id),
    constraint FKq3iqpff34tgtkvnn545a648cb
        foreign key (role_id) references roles (id),
    constraint FKt69njxcgfcto5wcrd9ocmb35h
        foreign key (authority_id) references authorities (id)
);

create table if not exists short_answers
(
    short_answer varchar(3000) null,
    id           bigint        not null
        primary key,
    constraint FKsbtvr5ca2030p12sv47fyr3dv
        foreign key (id) references flashcards (id)
);

create table if not exists tags
(
    id       bigint auto_increment
        primary key,
    tag_name varchar(255) null,
    user_id  bigint       not null,
    constraint UK9q7l5p1fi3pt3q7jch4u15kye
        unique (tag_name, user_id)
);

create table if not exists flashcards_tags
(
    flashcard_id bigint not null,
    tag_id       bigint not null,
    constraint FK9w345a9n04low9kwpv4yiby8g
        foreign key (tag_id) references tags (id),
    constraint FKfb0pu9rq34hfn7in06m6jxoc
        foreign key (flashcard_id) references flashcards (id)
);

create table if not exists true_false_answers
(
    true_false_answer bit    null,
    id                bigint not null
        primary key,
    constraint FKban1y1ti26o7hacos2uhptww8
        foreign key (id) references flashcards (id)
);

create table if not exists users
(
    id           bigint auto_increment
        primary key,
    date_created datetime(6)  null,
    email        varchar(255) not null,
    last_updated datetime(6)  null,
    name         varchar(255) null,
    password     varchar(255) not null,
    username     varchar(255) not null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username)
);

create table if not exists user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint FKh8ciramu9cc9q3qcqiv4ue8a6
        foreign key (role_id) references roles (id),
    constraint FKhfh9dx7w3ubf1co1vdev94g3f
        foreign key (user_id) references users (id)
);
