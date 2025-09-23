create table component_images
(
    id                  bigint auto_increment
        primary key,
    name                varchar(255) null,
    travel_component_id bigint       null,
    url                 varchar(255) null,
    constraint
        foreign key (travel_component_id) references travel_components (id)
);

