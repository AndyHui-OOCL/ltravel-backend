create table plan_images
(
    id             bigint auto_increment
        primary key,
    name           varchar(255) null,
    travel_plan_id bigint       null,
    url            varchar(255) null,
    constraint foreign key (travel_plan_id) references travel_plan (id)
);

