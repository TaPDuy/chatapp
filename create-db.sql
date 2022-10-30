use app_multi_chat;
/*drop table user;

viewname là trường hiển thị tên trên trang tài khoản
gender là giới tính lấy 0 hoặc 1 tương ứng
image là hình ảnh được chuyển từ mảng byte
imageString là đường dẫn đến ảnh trong thư viện
status là trang thái đang hoạt động hay không mặc định 0 là ko hđ
*/
CREATE TABLE `app_multi_chat`.`tbluser` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sdt` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `viewname` VARCHAR(50) NOT NULL,
  `fullname` VARCHAR(100) NOT NULL,
  `gender` VARCHAR(50) NOT NULL DEFAULT '',
  `image` longblob,
  `imageString` varchar(255) DEFAULT '',
  `address` VARCHAR(100) NULL,
  `dob` DATETIME NULL,
  `status` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `sdt_UNIQUE` (`sdt` ASC) VISIBLE)
  ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into tbluser(sdt, password, viewnametbluser, fullname) values("0374458897", "315147", "Smile", "Lê Văn Công");

CREATE TABLE `app_multi_chat`.`tblgroup` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
);

CREATE TABLE `app_multi_chat`.`tbl_group_user` (
  `group_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`group_id`, `user_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    FOREIGN KEY (`group_id`)
    REFERENCES `app_multi_chat`.`tblgroup` (`id`)
    ON DELETE CASCADE,
    FOREIGN KEY (`user_id`)
    REFERENCES `app_multi_chat`.`tbluser` (`id`)
    ON DELETE CASCADE
);

/*
status trạng thái 0 ứng chưa là bạn 1 là bạn 2 là đang đợi đồng ý
*/
create table tblfriend(
 id int not null auto_increment primary key,
 user_id int not null,
 userf_id int not null,
 status char(1) NOT NULL,
 FOREIGN KEY (`user_id`) REFERENCES `tbluser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
 FOREIGN KEY (`userf_id`) REFERENCES `tbluser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE);
 
CREATE TABLE `app_multi_chat`.`tbl_message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NULL,
  `sender_id` INT NULL,
  `time_sent` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_user_sender_id_idx` (`sender_id` ASC) VISIBLE,
  CONSTRAINT `fk_message_user_sender_id`
    FOREIGN KEY (`sender_id`)
    REFERENCES `app_multi_chat`.`tbluser` (`id`)
    ON DELETE SET NULL
);
     
CREATE TABLE `app_multi_chat`.`tbl_receiver` (
  `message_id` INT NOT NULL,
  `receiver_id` INT NOT NULL,
  `group_id` INT NULL,
  PRIMARY KEY (`message_id`, `receiver_id`),
  INDEX `fk_receiver_user_receiver_id_idx` (`receiver_id` ASC) VISIBLE,
  INDEX `fk_receiver_group_id_idx` (`group_id` ASC) VISIBLE,
  CONSTRAINT `fk_receiver_message_id`
    FOREIGN KEY (`message_id`)
    REFERENCES `app_multi_chat`.`tbl_message` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_receiver_user_receiver_id`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `app_multi_chat`.`tbluser` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_receiver_group_id`
    FOREIGN KEY (`group_id`)
    REFERENCES `app_multi_chat`.`tblgroup` (`id`)
    ON DELETE CASCADE
);