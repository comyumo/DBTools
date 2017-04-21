
 drop table if exists `customer`;
 CREATE TABLE `customer` ( 
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
    `name` nvarchar (255) NOT NULL COMMENT '客户名称' ,
    `contact` nvarchar (255) NOT NULL COMMENT '联系人' ,
    `telephone` varchar (18) NOT NULL COMMENT '电话' ,
    `email` varchar (9) COMMENT '邮箱' ,
    `remark` nvarchar (255) COMMENT '说明' ,
    `it` datetime COMMENT '登录时间' ,
    `iu` varchar (40) COMMENT '登录者' ,
    `ut` datetime COMMENT '更新时间' ,
    `uu` varchar (40) COMMENT '更新者' ,
    PRIMARY KEY (`id`)
);

