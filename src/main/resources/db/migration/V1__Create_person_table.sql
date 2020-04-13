CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `account_id` bigint(20) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `modify_time` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;