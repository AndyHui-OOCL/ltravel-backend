create table travel_components
(
    id                   bigint auto_increment
        primary key,
    address              varchar(255) null,
    current_occupation   varchar(255) null,
    description          varchar(255) null,
    future_occupation    varchar(255) null,
    is_location             boolean          null,
    name                 varchar(255) null,
    open_time            varchar(255) null,
    rating               float        not null,
    suggestion_play_time int          not null,
    way_of_commute       varchar(255) null
);
