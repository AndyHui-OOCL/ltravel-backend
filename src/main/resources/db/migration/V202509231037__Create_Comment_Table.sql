create table comment
(
    id                  bigint auto_increment
        primary key,
    description         varchar(255) null,
    is_like             bit          null,
    travel_component_id bigint       null,
    user_id             bigint       null,
    constraint fk_comment_travel_component_id
        foreign key (travel_component_id) references travel_components (id),
    constraint fk_comment_user_id
        foreign key (user_id) references users (id)
);