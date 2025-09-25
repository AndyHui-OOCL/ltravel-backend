create table travel_plan
(
    id              bigint auto_increment
        primary key,
    city_name       varchar(255) null,
    description     varchar(255) null,
    is_local_travel bit          not null,
    is_popular      bit          not null,
    title           varchar(255) null
);
