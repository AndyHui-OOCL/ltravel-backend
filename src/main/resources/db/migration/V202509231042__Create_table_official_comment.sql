CREATE TABLE `official_comment` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `event_comment` varchar(255) DEFAULT NULL,
                                    `overall_comment` varchar(255) DEFAULT NULL,
                                    `promote_reason` varchar(255) DEFAULT NULL,
                                    `rating` double DEFAULT NULL,
                                    `travel_plan_id` bigint DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;