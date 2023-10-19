-- Author
DROP TABLE IF EXISTS `Author`;

CREATE TABLE `Author` (
  `id` bigint NOT NULL,
  `accountName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `vorname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `Author` WRITE;

INSERT INTO `Author` VALUES (1,'islamaj','hallo','test');

UNLOCK TABLES;


DROP TABLE IF EXISTS `Author_SEQ`;

CREATE TABLE `Author_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `Author_SEQ` WRITE;

INSERT INTO `Author_SEQ` VALUES (51),(51);

UNLOCK TABLES;



-- Comment
DROP TABLE IF EXISTS `Comment`;

CREATE TABLE `Comment` (
  `id` bigint NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `entryId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4ddgxtla7pder9qjrm03qk9e4` (`entryId`),
  CONSTRAINT `FK4ddgxtla7pder9qjrm03qk9e4` FOREIGN KEY (`entryId`) REFERENCES `Entry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `Comment` WRITE;

INSERT INTO `Comment` VALUES (1,'hallooo',NULL);

UNLOCK TABLES;

DROP TABLE IF EXISTS `Comment_SEQ`;

CREATE TABLE `Comment_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `Comment_SEQ` WRITE;

INSERT INTO `Comment_SEQ` VALUES (51),(51);

UNLOCK TABLES;



-- Entry
DROP TABLE IF EXISTS `Entry`;

CREATE TABLE `Entry` (
  `id` bigint NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `likes` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `authorId` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi7lp5elvikuvegots1l5nyfc6` (`author_id`),
  KEY `FK7qcv1yetvhpdfjnxjf3kweleb` (`authorId`),
  CONSTRAINT `FK7qcv1yetvhpdfjnxjf3kweleb` FOREIGN KEY (`authorId`) REFERENCES `Author` (`id`),
  CONSTRAINT `FKi7lp5elvikuvegots1l5nyfc6` FOREIGN KEY (`author_id`) REFERENCES `Author` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `Entry` WRITE;

INSERT INTO `Entry` VALUES (151,'X MEN',0,'Halli Hallo',NULL,NULL);

UNLOCK TABLES;

DROP TABLE IF EXISTS `Entry_SEQ`;

CREATE TABLE `Entry_SEQ` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `Entry_SEQ` WRITE;

INSERT INTO `Entry_SEQ` VALUES (201),(201);

UNLOCK TABLES;
