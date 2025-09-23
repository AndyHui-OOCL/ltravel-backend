create table comment
(
    id                  bigint auto_increment
        primary key,
    description         varchar(255) null,
    is_like             bit          null,
    travel_component_id bigint       null,
    user_id             bigint       null
);