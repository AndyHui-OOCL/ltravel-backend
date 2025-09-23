create table official_comment
(
    id              bigint auto_increment
        primary key,
    event_comment   varchar(255) null,
    overall_comment varchar(255) null,
    promote_reason  varchar(255) null,
    rating          double       null,
    travel_plan_id  bigint       null
);

