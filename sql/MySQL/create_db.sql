CREATE DATABASE localwomen;


CREATE TABLE `localwomen`.`advert_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));


CREATE TABLE `localwomen`.`advert_size` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `localwomen`.`publication` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));


CREATE TABLE `localwomen`.`business` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `firstname` VARCHAR(50) NOT NULL,
  `lastname` VARCHAR(50) NOT NULL,
  `address1` VARCHAR(256) NULL,
  `address2` VARCHAR(256) NULL,
  `city` VARCHAR(50) NOT NULL,
  `county` VARCHAR(50) NULL,
  `postcode` VARCHAR(8) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `tel` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));


CREATE TABLE `localwomen`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `business_id` INT NOT NULL,
  `created` DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_order_business_id_idx` (`business_id` ASC),
  CONSTRAINT `fk_order_business_id`
    FOREIGN KEY (`business_id`)
    REFERENCES `localwomen`.`business` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


CREATE TABLE `localwomen`.`order_part` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `month` INT NOT NULL,
  `year` INT NOT NULL,
  `orders_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_order_part_orders_id_idx` (`orders_id` ASC),
  CONSTRAINT `fk_order_part_orders_id`
    FOREIGN KEY (`orders_id`)
    REFERENCES `localwomen`.`orders` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


CREATE TABLE `localwomen`.`order_publication` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ad_type` INT NOT NULL,
  `ad_size` INT NOT NULL,
  `note` BLOB NULL,
  `order_part_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_order_publication_order_part_id_idx` (`order_part_id` ASC),
  CONSTRAINT `fk_order_publication_order_part_id`
    FOREIGN KEY (`order_part_id`)
    REFERENCES `localwomen`.`order_part` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);