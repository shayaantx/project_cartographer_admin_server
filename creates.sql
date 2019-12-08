
CREATE TABLE IF NOT EXISTS `banned_email_domains` (
  `domain` varchar(254) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `comp` (
  `comp_id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_1` text COLLATE utf8mb4_unicode_ci,
  `serial_2` text COLLATE utf8mb4_unicode_ci,
  `serial_3` text COLLATE utf8mb4_unicode_ci,
  `op_sys` text COLLATE utf8mb4_unicode_ci,
  `comp_link` int(11) NOT NULL DEFAULT '0',
  `last_active` date DEFAULT NULL,
  `comp_ban` tinyint(1) NOT NULL DEFAULT '0',
  `comp_ban_date` date DEFAULT NULL,
  `comp_ban_date_end` date DEFAULT NULL,
  `comp_comments` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`comp_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=31931 ;


CREATE TABLE IF NOT EXISTS `compY` (
  `comp_id` int(11) NOT NULL AUTO_INCREMENT,
  `serial` varchar(32) CHARACTER SET utf8 NOT NULL,
  `comp_link` int(11) NOT NULL DEFAULT '0',
  `last_active` date DEFAULT NULL,
  `comp_ban` tinyint(1) NOT NULL DEFAULT '0',
  `comp_ban_date` date DEFAULT NULL,
  `comp_ban_date_end` date DEFAULT NULL,
  `comp_comments` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`comp_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=18061 ;


CREATE TABLE IF NOT EXISTS `comp_user` (
  `user_id` int(11) NOT NULL,
  `validation_token` varchar(32) CHARACTER SET utf8 NOT NULL,
  `trigger_ip` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `delete_after` date DEFAULT NULL,
  PRIMARY KEY (`validation_token`),
  KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `disable_user` (
  `user_id` int(11) NOT NULL,
  `validation_token` varchar(32) CHARACTER SET utf8 NOT NULL,
  `trigger_ip` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `delete_after` date DEFAULT NULL,
  PRIMARY KEY (`validation_token`),
  KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE IF NOT EXISTS `domains_unverified` (
  `domain` varchar(254) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `domains_whitelist` (
  `domain` varchar(254) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `new_user` (
  `email` varchar(254) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(31) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `trigger_ip` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `validation_token` varchar(32) CHARACTER SET utf8 NOT NULL,
  `delete_after` date DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `recover_user` (
  `user_id` int(11) NOT NULL,
  `validation_token` varchar(32) CHARACTER SET utf8 NOT NULL,
  `trigger_ip` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `delete_after` date DEFAULT NULL,
  PRIMARY KEY (`validation_token`),
  KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `recover_user` (
  `user_id` int(11) NOT NULL,
  `validation_token` varchar(32) CHARACTER SET utf8 NOT NULL,
  `trigger_ip` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `delete_after` date DEFAULT NULL,
  PRIMARY KEY (`validation_token`),
  KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `statistics` (
  `date` date NOT NULL,
  `logins` int(11) NOT NULL DEFAULT '0',
  `logins_unique` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `team_big_battle` (
  `user_id` int(11) NOT NULL,
  `user_exp` int(11) NOT NULL,
  `user_rank` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `team_slayer` (
  `user_id` int(11) NOT NULL,
  `user_exp` int(11) NOT NULL,
  `user_rank` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(254) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(31) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `login_token` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `last_login` date DEFAULT NULL,
  `last_log` int(11) NOT NULL DEFAULT '0',
  `user_ip` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `user_type` tinyint(1) NOT NULL DEFAULT '1',
  `user_ban_date` date DEFAULT NULL,
  `user_ban_date_end` date DEFAULT NULL,
  `user_comments` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=51349 ;


CREATE TABLE IF NOT EXISTS `user_comp` (
  `user_comp_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `comp_id` int(11) NOT NULL,
  `user_comp_comments` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`user_comp_id`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_comp_id` (`comp_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=35064 ;


CREATE TABLE IF NOT EXISTS `user_compY` (
  `user_id` int(11) NOT NULL,
  `comp_id` int(11) NOT NULL,
  `user_comp_comments` text COLLATE utf8mb4_unicode_ci,
  KEY `fk_user_id` (`user_id`),
  KEY `fk_comp_id` (`comp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_data` (
  `user_id` int(11) NOT NULL,
  `date_joined` date DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE admin_users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE admin_authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES admin_users(username)
);

CREATE UNIQUE INDEX ix_auth_username
  on admin_authorities (username,authority);