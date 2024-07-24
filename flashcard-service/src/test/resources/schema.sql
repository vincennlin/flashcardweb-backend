create table if not exists authorities
(
    id   bigint auto_increment
    primary key,
    name varchar(20) not null
    );

create table if not exists flashcards
(
    id           bigint auto_increment
    primary key,
    date_created varchar(255)                                                                null,
    extra_info   varchar(255)                                                                null,
    last_updated varchar(255)                                                                null,
    note_id      bigint                                                                      not null,
    question     varchar(3000)                                                               null,
    type         varchar(20) null, -- 改用 varchar 以支援 H2
    user_id      bigint                                                                      not null
    );

create table if not exists fill_in_the_blank
(
    full_answer varchar(3000) null,
    id          bigint        not null
    primary key,
    constraint FK_fill_in_the_blank_flashcards
    foreign key (id) references flashcards (id)
    );

create table if not exists in_blank_answers
(
    id           bigint auto_increment
    primary key,
    text         varchar(255) null,
    flashcard_id bigint       null,
    constraint FK_in_blank_answers_fill_in_the_blank
    foreign key (flashcard_id) references fill_in_the_blank (id)
    );

create table if not exists multiple_choice
(
    id               bigint not null
    primary key,
    answer_option_id bigint null,
    constraint UK_multiple_choice_answer_option_id
    unique (answer_option_id),
    constraint FK_multiple_choice_flashcards
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
    constraint FK_options_multiple_choice
    foreign key (flashcard_id) references multiple_choice (id)
    );

alter table multiple_choice
    add constraint FK_multiple_choice_options
        foreign key (answer_option_id) references options (id);

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
    constraint FK_roles_authorities_roles
    foreign key (role_id) references roles (id),
    constraint FK_roles_authorities_authorities
    foreign key (authority_id) references authorities (id)
    );

create table if not exists short_answers
(
    short_answer varchar(3000) null,
    id           bigint        not null
    primary key,
    constraint FK_short_answers_flashcards
    foreign key (id) references flashcards (id)
    );

create table if not exists tags
(
    id       bigint auto_increment
    primary key,
    tag_name varchar(255) null,
    user_id  bigint       not null,
    constraint UK_tags_user_id
    unique (tag_name, user_id)
    );

create table if not exists flashcards_tags
(
    flashcard_id bigint not null,
    tag_id       bigint not null,
    constraint FK_flashcards_tags_tags
    foreign key (tag_id) references tags (id),
    constraint FK_flashcards_tags_flashcards
    foreign key (flashcard_id) references flashcards (id)
    );

create table if not exists true_false_answers
(
    true_false_answer bit    null,
    id                bigint not null
    primary key,
    constraint FK_true_false_answers_flashcards
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
    constraint UK_users_email
    unique (email),
    constraint UK_users_username
    unique (username)
    );

create table if not exists user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint FK_user_roles_roles
    foreign key (role_id) references roles (id),
    constraint FK_user_roles_users
    foreign key (user_id) references users (id)
    );
