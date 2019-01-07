drop database if exists jiadian;
create database if not exists jiadian;
use jiadian;


create table user (
    userid varchar(7) not null primary key ,
    username varchar(10) not null,
    userpwd varchar(32) not null,
    userpic BLOB,
    department varchar(20) not null,
    userrole enum('管理员','销售','售后','经理') not null,
    signintime datetime,
    signinip varchar(20),
    explorer varchar(20)
)ENGINE=InnoDB;
insert into user (userid,username,userpwd,department,userrole) values
("EM00001","shijiaqi","202cb962ac59075b964b07152d234b70","老大部门","管理员"),
("EM00002","xiaoshou1","202cb962ac59075b964b07152d234b70","电视","销售"),
("EM00003","xiaoshou2","202cb962ac59075b964b07152d234b70","空调","销售"),
("EM00004","shouhou","202cb962ac59075b964b07152d234b70","电视","售后"),
("EM00005","jingli","202cb962ac59075b964b07152d234b70","电视","经理");



create table department(
    departmentS varchar(20) not null primary key,
    departmentL varchar(20) not null
)ENGINE=InnoDB;
insert into department values 
("曲面电视","电视"),
("超薄电视","电视"),
("OLED电视","电视"),
("壁挂式空调","空调"),
("柜式空调","空调"),
("中央空调","空调"),
("滚筒洗衣机","洗衣机"),
("洗烘一体机","洗衣机"),
("波轮洗衣机","洗衣机");



create table product(
    productid varchar(11) not null primary key,
    productname varchar(20) not null,
    productpic BLOB,
    productsort varchar(200),
    foreign key(productsort) references department(departmentS),
    baseprice decimal(9,2),
    price decimal(9,2),
    unit varchar(10),
    bewrite varchar(50)
)ENGINE=InnoDB;
insert into product(productid,productname,productsort,baseprice,price,unit,bewrite) values
('PD201900001','三星2019电视曲面','曲面电视','5000','7000','台','很不错，曲面屏的');



create table client(
    clientid varchar(9) not null primary key,
    clientname varchar(10) not null,
    phonenumber varchar(20) ,
    home varchar(30) ,
    vip enum('是','否') default '否'
)ENGINE=InnoDB;
insert into client values
('CT0200001','小史','13012344321','浙江宁波A小区','是'),
('CT0200002','小刘','13012344321','山东济南B小区','否');




create table orderform(
    formid varchar(13) not null primary key,
    productid varchar(11),
    foreign key(productid) references product(productid),
    clientid varchar(9),
    foreign key(clientid) references client(clientid),
    orderdate datetime default now(),
    buyway varchar(10) ,
    buynumber int(10) ,
    price decimal(9,2) ,
    sumprice decimal(9,2) ,
    userid varchar(7),
    foreign key(userid) references user(userid)
)ENGINE=InnoDB;
insert into orderform values
('OD20190100001','PD201900001','CT0200001',now(),'线下购买',1,'7000','7000','EM00001');



create table aftersale(
    afromid varchar(13) not null primary key,
    formid varchar(13),
    foreign key(formid) references orderform(formid),
    quetionsort varchar(10),
    question varchar(20),
    afterdate datetime default now(),
    status enum('办结','办理中') default '办理中',
    userid varchar(7),
    foreign key(userid) references user(userid)
)ENGINE=InnoDB;
insert into aftersale values
("SV20190100001",'OD20190100001','维修','电视坏掉啦',now(),'办理中','EM00001');



create table result(
    resultid varchar(13) not null primary key,
    afromid varchar(13),
    foreign key(afromid) references aftersale(afromid),
    resultsort varchar(10),
    result varchar(20),
    price decimal(9,2),
    resultdate datetime default now(),
    userid varchar(7),
    foreign key(userid) references user(userid)
)ENGINE=InnoDB;
insert into result values
('FD20190100001','SV20190100001','返厂修理','修理完毕','1000',now(),'EM00001');

