create table travel_days
(
    id                  bigint auto_increment
        primary key,
    component_order     int    not null,
    day_num             int    not null,
    plan_id             bigint null,
    travel_component_id bigint null,
    travel_plan_id      bigint null,
    constraint
        foreign key (travel_plan_id) references travel_plan (id),
    constraint
        foreign key (travel_component_id) references travel_components (id)
);

