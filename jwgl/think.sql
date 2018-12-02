create DATABASE guanli;
use guanli;
CREATE TABLE think_class (
  class_id int(11) not NULL COMMENT '班级编号',
  class_name varchar(255) NOT NULL COMMENT '班级名称',
  institute varchar(255) NOT NULL COMMENT '学院'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='班级表';

CREATE TABLE `think_institute` (
  `institute_name` varchar(255) NOT NULL COMMENT '院系名称',
  `institute_introduction` varchar(255) NOT NULL COMMENT '院系介绍',
  `president` varchar(255) NOT NULL COMMENT '主任'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='学院表';

CREATE TABLE `think_major` (
  `major_name` varchar(255) NOT NULL COMMENT '专业名称',
  `major_introduction` varchar(255) NOT NULL COMMENT '专业说明',
  `institute` varchar(255) NOT NULL COMMENT '学院'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='专业表';

CREATE TABLE `think_student` (
  `student_id` int(11) NOT NULL COMMENT '学号',
  `student_name` varchar(255) NOT NULL COMMENT '姓名',
  `class` varchar(255) NOT NULL COMMENT '班级',
  `student_birthday` date  COMMENT '生日',
  `student_home` varchar(255)  COMMENT '家庭住址'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='学生表';

CREATE TABLE `think_subject` (
  `subject_id` int(11) NOT NULL COMMENT '课程编号',
  `subject_name` varchar(255) NOT NULL COMMENT '课程名称',
  `subject_introduction` varchar(255) NOT NULL COMMENT '课程说明',
  `compulsory` enum('yes','no') NOT NULL DEFAULT 'yes' COMMENT '是否必修',
  `public` enum('yes','no') NOT NULL DEFAULT 'yes' COMMENT '是否公共课',
  `period` int(11) NOT NULL COMMENT '学时',
  `credit` int(11) NOT NULL COMMENT '学分',
  `institute` varchar(255) NOT NULL COMMENT '专业',
  `material_cover` mediumblob  COMMENT '材料封面'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='课程表';

CREATE TABLE `think_teacher` (
  `teacher_id` int(11) not NULL COMMENT '教师ID',
  `teacher_name` varchar(255) NOT NULL COMMENT '教师姓名',
  `teacher_title` varchar(255) NOT NULL COMMENT '教师职称',
  `institute` varchar(255) NOT NULL COMMENT '学院',
  `class` varchar(255) NOT NULL COMMENT '教授课程',
  `introduction` varchar(255)  COMMENT '介绍',
  `teacher_photo` mediumblob  COMMENT '照片'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='教师表';

CREATE TABLE `think_user` (
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `user_password` varchar(255) NOT NULL COMMENT '用户密码',
  `head_portrait` mediumblob  COMMENT '头像',
  `time` datetime  COMMENT '登录时间',
  `location` varchar(255)  COMMENT '登陆地址',
  `browser` varchar(255)  COMMENT '浏览器',
  `user_type` enum('student','teacher','admin') NOT NULL DEFAULT 'student' COMMENT '角色',
  `user_id` int(11) 
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户表';

create table think_question1(
  question1id int(9) AUTO_INCREMENT COMMENT '题目序号',
  question1 varchar(1000) not null COMMENT '题目',
  answer enum('a','b','c','d') not null DEFAULT 'a' COMMENT '答案'
)

create table think_question2(
  question2id int(9) AUTO_INCREMENT COMMENT '题目序号',
  question2 varchar(1000) not null COMMENT '题目',
  isa enum('y','n') not null DEFAULT 'y' COMMENT '答案',
  isb enum('y','n') not null DEFAULT 'y' COMMENT '答案',
  isc enum('y','n') not null DEFAULT 'y' COMMENT '答案',
  isd enum('y','n') not null DEFAULT 'y' COMMENT '答案',
)

create table think_question3(
  question3id int(9) AUTO_INCREMENT COMMENT '题目序号',
  question3 varchar(1000) not null COMMENT '题目',
  answer enum('y','n') not null DEFAULT 'y' COMMENT '答案'
)

create table think_question4(
  question4id int(9) AUTO_INCREMENT COMMENT '题目序号',
  question4 varchar(1000) not null COMMENT '题目',
  answer varchar(255) not null COMMENT '答案' 
)


ALTER TABLE `think_class`
  ADD PRIMARY KEY (`class_id`);

ALTER TABLE `think_student`
  ADD PRIMARY KEY (`student_id`);

ALTER TABLE `think_subject`
  ADD PRIMARY KEY (`subject_id`);
  
ALTER TABLE `think_teacher`
  ADD PRIMARY KEY (`teacher_id`);

ALTER TABLE `think_class`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级编号';

ALTER TABLE `think_student`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学号';

ALTER TABLE `think_subject`
  MODIFY `subject_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程编号';

ALTER TABLE `think_teacher`
  MODIFY `teacher_id` int(11) NOT NULL AUTO_INCREMENT ;




insert into think_class(class_name,institute) values ('OS1','数软学院'),('OS2','数软学院'), ('中文1','语言学院');
insert into think_institute values ("数软学院","数据科学与软件工程","张传亮"),("语言学院","各种语言","刘磊");
insert into think_major values ('OS','开源','数软学院'),('中文','学习中文','语言学院');
insert into think_student(student_name,class) values ('阿史','OS1'),('阿雨','OS2'),('阿磊','中文1');
insert into think_subject(subject_name,subject_introduction,compulsory,public,period,credit,institute) values 
('数据结构','学习数据的结构','yes','no',16,2,'OS'),
('软件工程','学习如何构建软件','yes','no',16,2,'OS'),
('现代文鉴赏','学习现代文','yes','no',16,2,'中文');
insert into think_teacher(teacher_name,teacher_title,institute,class) values
('张传亮','老师','数软学院','数据结构'),
('张三','老师','语言学院','现代文鉴赏');
insert into think_user(user_name,user_password,user_type,user_id)values
('shijiaqi','shijiaqi','student',1),
('xuxingyu','xuxingyu','student',2),
('liulei','liulei','student',3),
('zhangchuanliang','zhangchuanliang','teacher',1),
('zhangsan','zhangsan','teacher',2);
