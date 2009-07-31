/*
SQLyog Enterprise - MySQL GUI v5.02
Host - 5.0.18-nt : Database - galicia
*********************************************************************
Server version : 5.0.18-nt
*/


create database if not exists `galicia`;

USE `galicia`;

/*Table structure for table `ctable` */

DROP TABLE IF EXISTS `ctable`;

CREATE TABLE `ctable` (
  `Cid` int(11) NOT NULL auto_increment,
  `PLid` int(11) NOT NULL,
  `intent_number` int(11) default NULL,
  PRIMARY KEY  (`Cid`),
  KEY `PLid` (`PLid`),
  CONSTRAINT `ctable_ibfk_1` FOREIGN KEY (`PLid`) REFERENCES `ltable` (`Lid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ctable` */

insert into `ctable` values 
(1,1,0),
(2,1,4),
(3,1,1),
(4,1,1),
(5,1,1),
(6,1,1),
(7,1,3),
(8,1,3),
(9,1,2);

/*Table structure for table `defaultname` */

DROP TABLE IF EXISTS `defaultname`;

CREATE TABLE `defaultname` (
  `att_0` int(11) default NULL,
  `att_1` int(11) default NULL,
  `att_2` int(11) default NULL,
  `att_3` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `defaultname` */

insert into `defaultname` values 
(1,0,0,0),
(0,1,0,0),
(0,0,1,0),
(0,0,0,1),
(1,1,1,0),
(1,1,0,1);

/*Table structure for table `edgetable` */

DROP TABLE IF EXISTS `edgetable`;

CREATE TABLE `edgetable` (
  `Edgeid` int(11) NOT NULL auto_increment,
  `PLid` int(11) NOT NULL,
  `Fatherid` int(11) default NULL,
  `Childrenid` int(11) default NULL,
  PRIMARY KEY  (`Edgeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `edgetable` */

insert into `edgetable` values 
(1,1,3,2),
(2,1,1,3),
(3,1,4,2),
(4,1,1,4),
(5,1,5,2),
(6,1,1,5),
(7,1,6,2),
(8,1,1,6),
(9,1,7,2),
(11,1,8,2),
(12,1,1,8),
(13,1,9,7),
(14,1,1,9),
(15,1,3,9),
(16,1,4,9);

/*Table structure for table `itable` */

DROP TABLE IF EXISTS `itable`;

CREATE TABLE `itable` (
  `Iid` int(11) NOT NULL auto_increment,
  `PLid` int(11) NOT NULL,
  `name` varchar(1000) default NULL,
  `slit` varchar(1000) default NULL,
  PRIMARY KEY  (`Iid`),
  KEY `PLid` (`PLid`),
  CONSTRAINT `itable_ibfk_1` FOREIGN KEY (`PLid`) REFERENCES `ltable` (`Lid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `itable` */

insert into `itable` values 
(1,1,NULL,NULL),
(2,1,' att_0 att_1 att_2 att_3',NULL),
(3,1,'att_0',NULL),
(4,1,'att_1',NULL),
(5,1,'att_2',NULL),
(6,1,'att_3',NULL),
(7,1,'att_0 att_1 att_2',NULL),
(8,1,'att_0 att_1 att_3',NULL),
(9,1,'att_0 att_1',NULL);

/*Trigger structure for table `itable` */

DELIMITER $$;

DROP TRIGGER `increase_concept`$$

CREATE TRIGGER `increase_concept` BEFORE INSERT ON `itable` FOR EACH ROW  BEGIN
	update ltable set node_number=node_number+1 where Lid=1;
END$$


DELIMITER ;$$

/*Table structure for table `ltable` */

DROP TABLE IF EXISTS `ltable`;

CREATE TABLE `ltable` (
  `Lid` int(11) NOT NULL auto_increment,
  `node_number` int(11) default '0',
  PRIMARY KEY  (`Lid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ltable` */

insert into `ltable` values 
(1,9);

/* Procedure structure for procedure `addEdge` */

drop procedure if exists `addEdge`;

DELIMITER $$;

CREATE PROCEDURE `addEdge`(in parrent int,in child int,out num int)
BEGIN
   insert into edgetable (PLid,Fatherid,Childrenid) values (1,parrent,child);
   select count(*) into num from edgetable; 
END$$

DELIMITER ;$$

/* Procedure structure for procedure `add_concept` */

drop procedure if exists `add_concept`;

DELIMITER $$;

CREATE PROCEDURE `add_concept`(in name_value varchar(1000),in size int,out result int,out id int)
BEGIN
  declare a int;
  select count(*) into a from itable where name=name_value;
  if a = 0 then
    insert into ctable (PLid,intent_number) values (1,size);
    insert into itable (PLid,name) values (1,name_value);
    set result=1;
    select count(*) into id from ctable;
  else
    set result=2;
  end if;
END$$

DELIMITER ;$$

/* Procedure structure for procedure `deleteEdge` */

drop procedure if exists `deleteEdge`;

DELIMITER $$;

CREATE PROCEDURE `deleteEdge`(in param int)
BEGIN
declare a int;
declare b int;
declare c int;
select Fatherid , Childrenid into a, b from edgetable where Edgeid=param;
select edgeid into c from edgetable where Fatherid = a and Childrenid = ANY (select Childrenid from edgetable where Fatherid = b);
delete from edgetable where Edgeid=c;
END$$

DELIMITER ;$$

/* Procedure structure for procedure `initial_lattice` */

drop procedure if exists `initial_lattice`;

DELIMITER $$;

CREATE PROCEDURE `initial_lattice`(in name varchar(1000), in number int)
BEGIN
   insert into ctable (Cid,PLid,intent_number) values (1,1,0);
   insert into ctable (Cid,PLid,intent_number) values (2,1,number);
   insert into itable (Iid,PLid) values (1,1);
   insert into itable (Iid,PLid,name) values (2,1,name);
END$$

DELIMITER ;$$
