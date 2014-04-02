SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `streamalert` ;
CREATE SCHEMA IF NOT EXISTS `streamalert` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `streamalert` ;

-- -----------------------------------------------------
-- Table `streamalert`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `streamalert`.`user` ;

CREATE TABLE IF NOT EXISTS `streamalert`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `twitchid` VARCHAR(100) NOT NULL,
  `chromacolor` VARCHAR(6) NOT NULL DEFAULT '00FF00',
  `bgcolor` VARCHAR(6) NOT NULL DEFAULT 'FFFFFF',
  `fontcolor` VARCHAR(6) NOT NULL DEFAULT '000000',
  `fontsize` INT(2) NOT NULL DEFAULT 16,
  `userpicture` TINYINT(1) NOT NULL DEFAULT True,
  `externalcss` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `twitchid_UNIQUE` (`twitchid` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
