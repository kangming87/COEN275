/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.5.49 : Database - stockmarket
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stockmarket` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `stockmarket`;

/*Table structure for table `deal_price_quantity` */

DROP TABLE IF EXISTS `deal_price_quantity`;

CREATE TABLE `deal_price_quantity` (
                                       `id` bigint(32) NOT NULL AUTO_INCREMENT,
                                       `dealId` bigint(32) NOT NULL,
                                       `userId` bigint(32) NOT NULL,
                                       `stockId` bigint(32) NOT NULL,
                                       `price` decimal(19,2) DEFAULT NULL,
                                       `quantity` int(10) DEFAULT NULL,
                                       `status` enum('Buy','BuySuccess','BuyPartSuccess','Sale','SaleSuccess','SalePartSuccess') NOT NULL,
                                       PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `deal_price_quantity` */
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (500, 1996, 1, 32.4, 10000, 'BuySuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (501, 1996, 2, 3.2, 10000, 'BuySuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (502, 1996, 3, 10, 10000, 'BuySuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (503, 1996, 4, 80, 10000, 'BuySuccess');

insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (504, 1996, 1, 48, 5000, 'SaleSuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (505, 1996, 2, 1.9, 5000, 'SaleSuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (506, 1996, 3, 12.2, 5000, 'SaleSuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (507, 1996, 4, 53, 5000, 'SaleSuccess');

insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (508, 1997, 1, 48, 5000, 'BuySuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (509, 1997, 2, 1.9, 5000, 'BuySuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (510, 1997, 3, 12.2, 5000, 'BuySuccess');
insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (511, 1997, 4, 53, 5000, 'BuySuccess');


insert  into `deal_price_quantity` (`dealId`, `userId`, `stockId`, `price`,`quantity`, `status`) values (512, 2, 1, 20, 10000, 'BuySuccess');
/*Table structure for table `stock_info` */

DROP TABLE IF EXISTS `stock_info`;

CREATE TABLE `stock_info` (
                              `stockId` bigint(32) NOT NULL AUTO_INCREMENT,
                              `stockCode` bigint(32) NOT NULL,
                              `stockName` varchar(255) NOT NULL,
                              PRIMARY KEY (`stockId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `stock_info` */

insert  into `stock_info` (`stockId`,`stockCode`,`stockName`) values
    (1, 1001,'stock1001');

insert  into `stock_info`(`stockCode`,`stockName`) values
    (1002,'stock1002');

insert  into `stock_info`(`stockCode`,`stockName`) values
    (1003,'stock1003');

insert  into `stock_info`(`stockCode`,`stockName`) values
    (1004,'stock1004');




/*Table structure for table `stock_info_detail` */

DROP TABLE IF EXISTS `stock_info_detail`;

CREATE TABLE `stock_info_detail` (
                                     `id` bigint(32) NOT NULL AUTO_INCREMENT,
                                     `stockId` bigint(32) NOT NULL,
                                     `stockCode` bigint(32) NOT NULL,
                                     `stockName` varchar(255) NOT NULL,
                                     `start_price` decimal(19,2) DEFAULT NULL,
                                     `curr_price` decimal(19,2) DEFAULT NULL,
                                     `max_price` decimal(19,2) DEFAULT NULL,
                                     `min_price` decimal(19,2) DEFAULT NULL,
                                     PRIMARY KEY (`id`),
                                     KEY `stockId` (`stockId`),
                                     CONSTRAINT `stock_info_detail_ibfk_1` FOREIGN KEY (`stockId`) REFERENCES `stock_info` (`stockId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `stock_info_detail` */
insert  into `stock_info_detail`(`stockId`,`stockCode`,`stockName`, `start_price`, `curr_price`, `max_price`, `min_price`) values (1, 1001,'stock1001', 32.4, 39.3, 50.3, 32.4);
insert  into `stock_info_detail`(`stockId`,`stockCode`,`stockName`, `start_price`, `curr_price`, `max_price`, `min_price`) values (2, 1002,'stock1002', 3.2, 5, 6.1, 1.9);
insert  into `stock_info_detail`(`stockId`,`stockCode`,`stockName`, `start_price`, `curr_price`, `max_price`, `min_price`) values (3, 1003,'stock1003', 10, 20, 40, 5);
insert  into `stock_info_detail`(`stockId`,`stockCode`,`stockName`, `start_price`, `curr_price`, `max_price`, `min_price`) values (4, 1004,'stock1004', 80, 200, 290, 40);

/*Table structure for table `user_buy_info` */

DROP TABLE IF EXISTS `user_buy_info`;

CREATE TABLE `user_buy_info` (
                                 `id` bigint(32) NOT NULL AUTO_INCREMENT,
                                 `userId` bigint(32) NOT NULL,
                                 `stockId` bigint(32) NOT NULL,
                                 `stockCode` bigint(32) NOT NULL,
                                 `stockName` varchar(255) NOT NULL,
                                 `quantity` int(10) DEFAULT NULL,
                                 `buyprice` decimal(19,2) DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_buy_info` */
insert into `user_buy_info` (`id`) values (10000);

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
                             `userId` bigint(32) NOT NULL AUTO_INCREMENT,
                             `username` varchar(255) DEFAULT NULL,
                             `password` varchar(255) DEFAULT NULL,
                             `cash` decimal(19,2) DEFAULT 1000,
                             PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_info` */
insert  into `user_info`(`userId`,`username`,`password`, `cash`) values (1996,'root','lsc115500', '1000');
insert  into `user_info`(`userId`,`username`,`password`, `cash`) values (1997,'user1','123', '1000');
insert  into `user_info`(`userId`,`username`,`password`, `cash`) values (1998,'user2','123', '1000');

insert  into `user_info`(`userId`,`username`,`password`, `cash`) values (1,'buyer','123', '10000');
insert  into `user_info`(`userId`,`username`,`password`, `cash`) values (2,'seller','123', '1000');
/*Table structure for table `user_sale_info` */

DROP TABLE IF EXISTS `user_sale_info`;

CREATE TABLE `user_sale_info` (
                                  `id` bigint(32) NOT NULL AUTO_INCREMENT,
                                  `userId` bigint(32) NOT NULL,
                                  `stockId` bigint(32) NOT NULL,
                                  `stockCode` bigint(32) NOT NULL,
                                  `stockName` varchar(255) NOT NULL,
                                  `quantity` int(10) DEFAULT NULL,
                                  `saleprice` decimal(19,2) DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_sale_info`
insert into `user_sale_info` (`userId`, `stockId`, `stockCode`, `stockName`, `quantity`, `saleprice`) values (1996, 1, 1001, 'stock1001', 20, 20);
Table structure for table `user_stocks_info` */

DROP TABLE IF EXISTS `user_stocks_info`;

CREATE TABLE `user_stocks_info` (
                                    `id` bigint(32) NOT NULL AUTO_INCREMENT,
                                    `userId` bigint(32) NOT NULL,
                                    `stockId` bigint(32) NOT NULL,
                                    `stockCode` bigint(32) NOT NULL,
                                    `stockName` varchar(255) NOT NULL,
                                    `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `quantity` int(10) DEFAULT NULL,
                                    `status` enum('Buy','BuySuccess','BuyPartSuccess','Sale','SaleSuccess','SalePartSuccess') NOT NULL,
                                    `price` decimal(19,2) DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_stocks_info` */

/*Table structure for table `user_stocks_infolist` */

DROP TABLE IF EXISTS `user_stocks_infolist`;

CREATE TABLE `user_stocks_infolist` (
                                        `userId` bigint(32) NOT NULL,
                                        `stockId` bigint(32) NOT NULL,
                                        PRIMARY KEY (`userId`,`stockId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_stocks_infolist` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
