DROP TABLE IF EXISTS `TA_AUT_USER`;
CREATE TABLE `TA_AUT_USER` (
  `I_ID` 	  int(11) NOT NULL AUTO_INCREMENT,
  `I_Status`  int(11) DEFAULT NULL,
  
  `I_Type_01` int(11) DEFAULT NULL COMMENT 'kiểu adm, agent, visitor, member....',
  `I_Type_02` int(11) DEFAULT NULL COMMENt 'kiểu thứ cấp, ví dụ 1 học sinh sẽ có tk cho cha mẹ',
   
  `T_Login` varchar(100)  DEFAULT NULL,
  `T_Pass` varchar(1000)  DEFAULT NULL,
  
  `T_Info_01` text  DEFAULT NULL COMMENT 'email',
  `T_Info_02` text  DEFAULT NULL COMMENT 'tel',
  
  `D_Date_01` datetime DEFAULT NULL COMMENT 'dt new',
  `D_Date_02` datetime DEFAULT NULL COMMENT 'dt mod',
  
  PRIMARY KEY (`I_ID`),
  KEY `idx_TUSER_01` (`T_Login`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;




