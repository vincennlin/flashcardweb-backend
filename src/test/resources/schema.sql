create table if not exists notes
(
    id           bigint auto_increment
        primary key,
    content      varchar(255) not null,
    date_created datetime(6)  null,
    last_updated datetime(6)  null,
    user_id      bigint       not null,
    constraint FKechaouoa6kus6k1dpix1u91c
        foreign key (user_id) references users (id)
);

create table if not exists flashcards
(
    id           bigint auto_increment
        primary key,
    date_created varchar(255)                                                                null,
    extra_info   varchar(255)                                                                null,
    last_updated varchar(255)                                                                null,
    question     varchar(255)                                                                null,
    type         enum ('FILL_IN_THE_BLANK', 'MULTIPLE_CHOICE', 'SHORT_ANSWER', 'TRUE_FALSE') null,
    note_id      bigint                                                                      null,
    user_id      bigint                                                                      not null,
    constraint FKqnyjswltpmxi4ptylxtnfh8ch
        foreign key (note_id) references notes (id),
    constraint FKsmdc3wqt436om8w84cat8joxq
        foreign key (user_id) references users (id)
);

create table if not exists fill_in_the_blank
(
    full_answer varchar(255) null,
    id          bigint       not null
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

create table if not exists options
(
    id           bigint auto_increment
        primary key,
    text         varchar(255) null,
    flashcard_id bigint       null,
    constraint FKgbcvtq8fmx8y6s8lv9tovnb4k
        foreign key (flashcard_id) references multiple_choice (id)
);

alter table multiple_choice
    add constraint if not exists FKratayhbwedodds55sn2dxori7
        foreign key (answer_option_id) references options (id);

create table if not exists short_answers
(
    short_answer varchar(255) null,
    id           bigint       not null
        primary key,
    constraint FKsbtvr5ca2030p12sv47fyr3dv
        foreign key (id) references flashcards (id)
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
    id       bigint auto_increment
        primary key,
    email    varchar(255) not null,
    name     varchar(255) null,
    password varchar(255) not null,
    username varchar(255) not null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username)
);

create table if not exists roles
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
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