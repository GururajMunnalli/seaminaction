/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP TABLE IF EXISTS `FACILITY`;
CREATE TABLE `FACILITY` (
  `id` bigint(20) NOT NULL auto_increment,
  `address` varchar(50) default NULL,
  `name` varchar(50) NOT NULL,
  `state` varchar(2) default NULL,
  `type` varchar(15) NOT NULL,
  `country` varchar(30) default NULL,
  `description` text,
  `uri` varchar(255) default NULL,
  `city` varchar(30) default NULL,
  `zip` varchar(5) default NULL,
  `county` varchar(30) default NULL,
  `phone` varchar(10) default NULL,
  `price_range` int(11) default NULL,
  `logo_data` blob,
  `logo_content_type` varchar(255) default NULL,
  `owner_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `COURSE`;
CREATE TABLE `COURSE` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `description` text,
  `greens` enum('UNKNOWN','BENT','BERMUDA') NOT NULL,
  `designer` varchar(50) default NULL,
  `fairways` enum('UNKNOWN','BENT','BERMUDA','ZOYSIA','BLUEGRASS','RYE','FESCUE') NOT NULL,
  `year_built` int(4) default NULL,
  `num_holes` int(2) NOT NULL default '18',
  `signature_hole` bigint(20) default NULL,
  `facility_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `facility_id` (`facility_id`),
  CONSTRAINT `fk_course_ref_facility` FOREIGN KEY (`facility_id`) REFERENCES `FACILITY` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `HOLE`;
CREATE TABLE `HOLE` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(25) default NULL,
  `number` int(2) NOT NULL,
  `m_par` int(1) NOT NULL,
  `l_par` int(1) NOT NULL,
  `l_handicap` int(2) default NULL,
  `m_handicap` int(2) default NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uk_hole_number` (`number`,`course_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `fk_hole_ref_course` FOREIGN KEY (`course_id`) REFERENCES `COURSE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `TEE`;
CREATE TABLE `TEE` (
  `hole_id` bigint(20) NOT NULL,
  `tee_set_id` bigint(20) NOT NULL,
  `distance` int(4) NOT NULL,
  PRIMARY KEY  (`hole_id`,`tee_set_id`),
  KEY `HOLE_ID` (`hole_id`),
  KEY `TEE_SET_ID` (`tee_set_id`),
  CONSTRAINT `fk_tee_ref_hole` FOREIGN KEY (`hole_id`) REFERENCES `HOLE` (`id`),
  CONSTRAINT `fk_tee_ref_tee_set` FOREIGN KEY (`tee_set_id`) REFERENCES `TEE_SET` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `TEE_SET`;
CREATE TABLE `TEE_SET` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(25) default NULL,
  `pos` int(1) default NULL,
  `color` varchar(10) NOT NULL,
  `l_course_rating` double default NULL,
  `l_slope_rating` double default NULL,
  `m_course_rating` double default NULL,
  `m_slope_rating` double default NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uk_tee_set_color` (`color`,`course_id`),
  UNIQUE KEY `uk_tee_set_pos` (`pos`,`course_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `fk_tee_set_ref_course` FOREIGN KEY (`course_id`) REFERENCES `COURSE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `MEMBER`;
CREATE TABLE `MEMBER` (
  `id` bigint(20) NOT NULL auto_increment,
  `username` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `email_address` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email_address` (`email_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `GOLFER`;
CREATE TABLE `GOLFER` (
  `member_id` bigint(20) NOT NULL,
  `location` varchar(255) default NULL,
  `image_data` blob,
  `gender` varchar(255) default NULL,
  `last_name` varchar(40) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `joined` datetime NOT NULL,
  `dob` date default NULL,
  `specialty` varchar(255) default NULL,
  `pro_status` varchar(255) default NULL,
  `image_content_type` varchar(255) default NULL,
  PRIMARY KEY  (`member_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `fk_golfer_ref_member` FOREIGN KEY (`member_id`) REFERENCES `MEMBER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ROLE`;
CREATE TABLE `ROLE` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `MEMBER_ROLE`;
CREATE TABLE `MEMBER_ROLE` (
  `member_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`member_id`,`role_id`),
  KEY `member_id` (`member_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `fk_member` FOREIGN KEY (`member_id`) REFERENCES `MEMBER` (`id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `ROLE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `COURSE_COMMENT`;
CREATE TABLE `COURSE_COMMENT` (
  `id` bigint(20) NOT NULL auto_increment,
  `version` int(11) default NULL,
  `text` text NOT NULL,
  `date_posted` datetime NOT NULL,
  `golfer_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `golfer_id` (`golfer_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `fk_comment_ref_golfer` FOREIGN KEY (`golfer_id`) REFERENCES `GOLFER` (`member_id`),
  CONSTRAINT `fk_comment_ref_course` FOREIGN KEY (`course_id`) REFERENCES `COURSE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `FAVORITE`;
CREATE TABLE `FAVORITE` (
  `id` bigint(20) NOT NULL auto_increment,
  `entityName` varchar(255) default NULL,
  `entityId` bigint(20) default NULL,
  `golfer_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `golfer_id` (`golfer_id`),
  CONSTRAINT `fk_favorite_ref_golfer` FOREIGN KEY (`golfer_id`) REFERENCES `GOLFER` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `ROUND`;
CREATE TABLE `ROUND` (
  `id` bigint(20) NOT NULL auto_increment,
  `date` date NOT NULL,
  `version` int(11) default NULL,
  `notes` text,
  `total_score` int(11) NOT NULL,
  `weather` varchar(255) NOT NULL,
  `tee_set_id` bigint(20) NOT NULL,
  `golfer_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `tee_set_id` (`tee_set_id`),
  KEY `golfer_id` (`golfer_id`),
  CONSTRAINT `fk_round_ref_tee_set` FOREIGN KEY (`tee_set_id`) REFERENCES `TEE_SET` (`id`),
  CONSTRAINT `fk_round_ref_golfer` FOREIGN KEY (`golfer_id`) REFERENCES `GOLFER` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `SCORE`;
CREATE TABLE `SCORE` (
  `id` bigint(20) NOT NULL auto_increment,
  `putts` int(11) default NULL,
  `strokes` int(11) NOT NULL,
  `fairway` bit(1) default NULL,
  `gir` bit(1) default NULL,
  `hole_id` bigint(20) NOT NULL,
  `round_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ID` (`id`),
  KEY `round_id` (`round_id`),
  KEY `hole_id` (`hole_id`),
  CONSTRAINT `fk_score_ref_round` FOREIGN KEY (`round_id`) REFERENCES `ROUND` (`id`),
  CONSTRAINT `fk_score_ref_hole` FOREIGN KEY (`hole_id`) REFERENCES `HOLE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `TRIVIA_CATEGORY`;
CREATE TABLE `TRIVIA_CATEGORY` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `TRIVIA_QUESTION`;
CREATE TABLE `TRIVIA_QUESTION` (
  `id` bigint(20) NOT NULL auto_increment,
  `answer` varchar(255) default NULL,
  `question` varchar(255) default NULL,
  `category_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `fk_question_ref_category` FOREIGN KEY (`category_id`) REFERENCES `TRIVIA_CATEGORY` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
