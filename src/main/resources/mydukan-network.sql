-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: social_network
-- ------------------------------------------------------
-- Server version	5.7.21-0ubuntu0.17.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `social_network`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `social_network` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `social_network`;

--
-- Table structure for table `bid_onrfq`
--

DROP TABLE IF EXISTS `bid_onrfq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bid_onrfq` (
  `id` bigint(20) NOT NULL,
  `order_status_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `user_class_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1qjniv2o7wp1j10sh66tlgfst` (`order_status_id`),
  KEY `FK263n9662ne9y3qqsh81keq4je` (`post_id`),
  KEY `FKa37iq88cibo6rptj6glfshttq` (`user_class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bid_onrfq_bid_price_lists`
--

DROP TABLE IF EXISTS `bid_onrfq_bid_price_lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bid_onrfq_bid_price_lists` (
  `bid_onrfq_id` bigint(20) NOT NULL,
  `bid_price_lists_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_mouo7mkjcy3ttjysm93k7sowx` (`bid_price_lists_id`),
  KEY `FKsf8ylmjc1kovc5ktxht3vj4b4` (`bid_onrfq_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bid_onrfq_price_lists`
--

DROP TABLE IF EXISTS `bid_onrfq_price_lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bid_onrfq_price_lists` (
  `bid_onrfq_id` bigint(20) NOT NULL,
  `price_lists_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_d8cleg96h3ieg26aqjvqn4eyx` (`price_lists_id`),
  KEY `FK74wvwa8fmori5tnb1idgd8ub6` (`bid_onrfq_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL,
  `like_count` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  KEY `FKk71j9cpqtbo6180e0bkwwqa6k` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment_like`
--

DROP TABLE IF EXISTS `comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_like` (
  `id` bigint(20) NOT NULL,
  `comment_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqlv8phl1ibeh0efv4dbn3720p` (`comment_id`),
  KEY `FKhlyw0u6q4bj9kmr180al4ngur` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `discussion_handle`
--

DROP TABLE IF EXISTS `discussion_handle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discussion_handle` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `following`
--

DROP TABLE IF EXISTS `following`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `following` (
  `id` bigint(20) NOT NULL,
  `is_approved` bit(1) NOT NULL,
  `is_block_posts_of_follow_user` bit(1) NOT NULL,
  `following_user_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKixpr1ph911m6i0bfkxs8gjoam` (`following_user_id`,`user_id`),
  KEY `FK577059ame2qt1cofeh00r9dqk` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_on_price_list`
--

DROP TABLE IF EXISTS `order_on_price_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_on_price_list` (
  `id` bigint(20) NOT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `user_class_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5pi3nr7ed8k33448o3dlyq29m` (`post_id`),
  KEY `FKksl8p2rirahr9yy754duqadkj` (`user_class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_on_price_list_order_request_for_quotations`
--

DROP TABLE IF EXISTS `order_on_price_list_order_request_for_quotations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_on_price_list_order_request_for_quotations` (
  `order_on_price_list_id` bigint(20) NOT NULL,
  `order_request_for_quotations_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_346o3eoqfqw1hgbvh8l1djcmt` (`order_request_for_quotations_id`),
  KEY `FKb6g8uax0dsajfrxc83qifpvn0` (`order_on_price_list_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_on_price_list_request_for_quotations`
--

DROP TABLE IF EXISTS `order_on_price_list_request_for_quotations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_on_price_list_request_for_quotations` (
  `order_on_price_list_id` bigint(20) NOT NULL,
  `request_for_quotations_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_2jct6fge68l4nxdnpiphrlvs5` (`request_for_quotations_id`),
  KEY `FK77djh9fjus06uauipmggi300u` (`order_on_price_list_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_status` (
  `id` bigint(20) NOT NULL,
  `status_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `phone_model`
--

DROP TABLE IF EXISTS `phone_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone_model` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL,
  `like_count` int(11) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `report_abuse` int(11) NOT NULL,
  `post_visibility_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `is_public_post` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `post_condition_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5o16qn9u0p9bqr7odek9w29ye` (`post_visibility_id`),
  KEY `FK4eshu6l407gvmyv29un5ohpxx` (`post_condition_id`),
  KEY `FKol4as5v2jw12negvsvmr0yoq3` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_condition`
--

DROP TABLE IF EXISTS `post_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_condition` (
  `id` bigint(20) NOT NULL,
  `condition_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_like`
--

DROP TABLE IF EXISTS `post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_like` (
  `id` bigint(20) NOT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6c9l4bulka88xc5yobhtxc4u` (`id`,`post_id`),
  KEY `FKj7iy0k7n3d0vkh8o7ibjna884` (`post_id`),
  KEY `FKrbx56qaqia3wk2sshy1trfqnn` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_post_like_list`
--

DROP TABLE IF EXISTS `post_post_like_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_post_like_list` (
  `post_id` bigint(20) NOT NULL,
  `post_like_list_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_6gjjho5jvik4qq7kt7yc3aoi5` (`post_like_list_id`),
  KEY `FKlxn6a1vyed9f306m9rv1r3mjf` (`post_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_price_lists`
--

DROP TABLE IF EXISTS `post_price_lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_price_lists` (
  `post_id` bigint(20) NOT NULL,
  `price_lists_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_j23s86ywnklkf7en9vf8f1cbc` (`price_lists_id`),
  KEY `FKit081ugi3i3gi2dx2im8ud3p1` (`post_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_quantity_price_schemes`
--

DROP TABLE IF EXISTS `post_quantity_price_schemes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_quantity_price_schemes` (
  `post_id` bigint(20) NOT NULL,
  `quantity_price_schemes_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_i0bosx1wwyv9nyv3qpqdq8ps7` (`quantity_price_schemes_id`),
  KEY `FKe67sumravt07jqo7dhdiu71jh` (`post_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_rate`
--

DROP TABLE IF EXISTS `post_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_rate` (
  `id` bigint(20) NOT NULL,
  `current_price` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `offer_price` double DEFAULT NULL,
  `units` int(11) NOT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog7h380q7kx7jxt3ccyyoyp7j` (`post_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_request_for_quotations`
--

DROP TABLE IF EXISTS `post_request_for_quotations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_request_for_quotations` (
  `post_id` bigint(20) NOT NULL,
  `request_for_quotations_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_5dfwqfn5b7ndftc0vara7pxc4` (`request_for_quotations_id`),
  KEY `FKq2okkal4guj9rwh2jtroebxh7` (`post_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_value_schemes`
--

DROP TABLE IF EXISTS `post_value_schemes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_value_schemes` (
  `post_id` bigint(20) NOT NULL,
  `value_schemes_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ksmtql92muhcb5sfknrqsov6b` (`value_schemes_id`),
  KEY `FKqis07hju5nfa3kubfx6o7a64i` (`post_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_visibility`
--

DROP TABLE IF EXISTS `post_visibility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_visibility` (
  `id` bigint(20) NOT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrlw46332hrrdvpi08vl1h12xw` (`post_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_visibility_visible_to_users`
--

DROP TABLE IF EXISTS `post_visibility_visible_to_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_visibility_visible_to_users` (
  `post_visibility_id` bigint(20) NOT NULL,
  `visible_to_users_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_rb7vgc0c1x5kmyoml1d2g3qdf` (`visible_to_users_id`),
  KEY `FKmjluhnukub3yahffusj3jfkag` (`post_visibility_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price_list`
--

DROP TABLE IF EXISTS `price_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_list` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_limited_stock` bit(1) NOT NULL,
  `is_limited_time` bit(1) NOT NULL,
  `is_price_decreased` bit(1) NOT NULL,
  `is_price_increased` bit(1) NOT NULL,
  `price` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `phone_model_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4op3jl4ey2jjjjvpndvimbfdo` (`phone_model_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price_list_phone_models`
--

DROP TABLE IF EXISTS `price_list_phone_models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_list_phone_models` (
  `price_lists_id` bigint(20) NOT NULL,
  `phone_models_id` bigint(20) NOT NULL,
  `price_list_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_3g0pavbif810g9r3t0ijue1eq` (`phone_models_id`),
  KEY `FK8tiqaqv8r6udo5dxg29ri8v2f` (`price_lists_id`),
  KEY `FKrbb4ob3yu6eicp8mqxk2ty462` (`price_list_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price_template`
--

DROP TABLE IF EXISTS `price_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_template` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quantity_price_scheme`
--

DROP TABLE IF EXISTS `quantity_price_scheme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quantity_price_scheme` (
  `id` bigint(20) NOT NULL,
  `end_range` int(11) NOT NULL,
  `is_for_all` bit(1) DEFAULT NULL,
  `percent` int(11) NOT NULL,
  `start_range` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `phone_model_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq4l2ji1p1w9h9pvs4biltg48t` (`phone_model_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `request_for_quotation`
--

DROP TABLE IF EXISTS `request_for_quotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_for_quotation` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `expected_price` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `phone_model_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKox91b2p3b9wgud9t3tik4lhgd` (`phone_model_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share`
--

DROP TABLE IF EXISTS `share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_class`
--

DROP TABLE IF EXISTS `user_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_class` (
  `id` bigint(20) NOT NULL,
  `is_open_follow` bit(1) NOT NULL,
  `is_open_message` bit(1) NOT NULL,
  `is_open_profile` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_class_following_list`
--

DROP TABLE IF EXISTS `user_class_following_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_class_following_list` (
  `user_class_id` bigint(20) NOT NULL,
  `following_list_id` bigint(20) NOT NULL,
  KEY `FK5256p50n16bv045346dmg750b` (`following_list_id`),
  KEY `FKs19jkijqpkr1fbuka8jpmkwwm` (`user_class_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `follower_count` int(11) NOT NULL,
  `following_count` int(11) NOT NULL,
  `is_verified_user` bit(1) NOT NULL,
  `post_count` int(11) NOT NULL,
  `user_handle` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `value_scheme`
--

DROP TABLE IF EXISTS `value_scheme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `value_scheme` (
  `id` bigint(20) NOT NULL,
  `end_range` int(11) NOT NULL,
  `percent` int(11) NOT NULL,
  `price_after_discount` int(11) NOT NULL,
  `start_range` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-07 16:52:53
