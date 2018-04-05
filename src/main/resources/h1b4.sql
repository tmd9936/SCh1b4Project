
--중간 중간에 드랍이 있으니 커밋할 부분을 실행해 주세요
drop table member;

create table member(
    member_id VARCHAR2(20) primary key
    ,member_password varchar2(20) not null
    ,member_name varchar2(20) not null
    ,nation varchar2(20) not null
    ,point varchar(2) default 0
);

drop table teacher;

create table teacher(
    teacher_id varchar2(20) primary key
    ,teacher_password varchar2(20) not null
    ,teacher_name varchar2(20) not null
    ,teacher_onstate varchar2(20) not null
    ,teacher_ip varchar2(30) not null
    ,teacher_hit number default 0
);

drop table contents;

create table contents(
    contents_num number primary key
    ,contents_title varchar2(100) not null
    ,endtime varchar2(30) not null
    ,thumbnail varchar2(50) not null
    ,deletestate varchar2(20) not null
    ,deletedate varchar2(20) 
    ,deleteid varchar2(20) 
    ,category varchar2(20) not null
    ,contents_url varchar2(50) not null
);

drop SEQUENCE contents_seq;

create SEQUENCE contents_seq start with 1 increment by 1;

drop table transcript;

create table transcript(
    contents_num number not null
    ,ts_num number primary key
    ,ts_start varchar2(20) not null
    ,ts_dur varchar2(20) not null
    ,ts_text varchar2(1000) not null
    ,constraint contents_fk foreign key(contents_num)
        references contents(contents_num) on delete cascade
);

drop SEQUENCE transcript_seq;

create SEQUENCE transcript_seq start with 1 increment by 1;

drop table bookmark;

create table bookmark(
    bookmark_id number primary key
    ,member_id varchar2(20) not null
    ,contents_num number UNIQUE
    ,inputdate date default sysdate
    ,constraint bookmark_fk foreign key(member_id)
        references member(member_id) on delete cascade
);

drop SEQUENCE bookmark_seq;

create SEQUENCE bookmark_seq start with 1 increment by 1;

drop table contents_reply;

create table contents_reply(
    reply_num number primary key
    ,contents_num number not null
    ,member_id varchar2(20) not null
    ,reply_text varchar2(200) not null
    ,inputdate date default sysdate
    ,constraint reply_fk foreign key(contents_num)
        references contents(contents_num) on delete cascade
);

drop SEQUENCE contents_reply_seq;

create SEQUENCE contents_reply_seq start with 1 increment by 1; 