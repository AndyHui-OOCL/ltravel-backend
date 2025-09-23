CREATE TABLE `comment` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `description` varchar(255) DEFAULT NULL,
                           `is_like` bit(1) DEFAULT NULL,
                           `travel_component_id` bigint DEFAULT NULL,
                           `user_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;