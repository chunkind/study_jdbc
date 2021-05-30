drop table customer purge;
drop table stock purge;
drop table shares purge;

create table customer(
    ssn varchar2(50) primary key,
    cust_name varchar2(50) not null,
    address varchar2(100) not null
);
 
create table stock(
    symbol varchar2(20) primary key,
    price number(10,2) not null
);
 
create table shares(
    ssn varchar2(30) not null,
    symbol varchar2(20) not null,
    quantity number(10) not null
);
 
insert into stock ( symbol,price) values( 'SUNW', 68.75);
insert into stock ( symbol,price) values( 'CyAs', 22.675);
insert into stock ( symbol,price) values( 'DUKE', 6.25);
insert into stock ( symbol,price) values( 'ABStk', 18.5);
insert into stock ( symbol,price) values( 'JSVco', 9.125);
insert into stock ( symbol,price) values( 'TMAs', 82.375);
insert into stock ( symbol,price) values( 'BWInc', 11.375);
insert into stock ( symbol,price) values( 'GMEnt', 44.625);
insert into stock ( symbol,price) values( 'PMLtd', 203.375);
insert into stock ( symbol,price) values( 'JDK', 33.5);

insert into customer values( '111-111', 'Yufirst1', 'Seoul');
insert into customer values( '111-112', 'Yufirst2', 'Seoul');
insert into customer values( '111-113', 'Yufirst3', 'Seoul');
insert into customer values( '111-114', 'Yufirst4', 'Seoul');
insert into customer values( '111-115', 'yufirst5', 'JeonJu');
insert into customer values( '111-116', 'Yufirst6', 'Seoul');
insert into customer values( '111-117', 'Yufirst7', 'Seoul');
insert into customer values( '111-118', 'Yufirst8', 'Seoul');
insert into customer values( '111-119', 'Yufirst9', 'Seoul');

insert into shares values('111-111', 'SUNW', 2);
insert into shares values('111-111', 'DUKE', 5);
insert into shares values('111-111', 'JDK', 5);
insert into shares values('111-112', 'BWInc', 10);
insert into shares values('111-112', 'PMLtd', 2);
insert into shares values('111-112', 'TMAs', 9);
insert into shares values('111-112', 'CyAs', 8);
insert into shares values('111-113', 'DUKE', 8);
insert into shares values('111-113', 'ABStk', 15);
insert into shares values('111-113', 'JSVco', 19);
insert into shares values('111-113', 'TMAs', 1);
insert into shares values('111-114', 'BWInc', 3);
insert into shares values('111-114', 'GMEnt', 60);
insert into shares values('111-115', 'DUKE', 1);
insert into shares values('111-116', 'GMEnt', 22);
insert into shares values('111-116', 'PMLtd', 13);
insert into shares values('111-116', 'SUNW', 8);
insert into shares values('111-116', 'BWInc', 33);
insert into shares values('111-116', 'DUKE', 22);
insert into shares values('111-117', 'SUNW', 2);
insert into shares values('111-117', 'ABStk', 4);
insert into shares values('111-117', 'TMAs', 5);
insert into shares values('111-117', 'BWInc', 10);
insert into shares values('111-117', 'GMEnt', 3);
insert into shares values('111-117', 'PMLtd', 4);
insert into shares values('111-117', 'JDK', 5);
insert into shares values('111-118', 'JSVco', 3);
insert into shares values('111-118', 'JDK', 2);
insert into shares values('111-119', 'BWInc', 4);
insert into shares values('111-119', 'GMEnt', 9);
insert into shares values('111-119', 'ABStk', 8);
commit;


/////////// Shares table에 Foreign key 추가 방법 ////////////////
ALTER TABLE shares ADD CONSTRAINT fk_shares_ssn foreign key(ssn) references customer(ssn);
ALTER TABLE shares ADD CONSTRAINT fk_shares_symbol foreign key(symbol) references stock(symbol);

///////////접속 컨넥션 프로세스 용량을 늘리는 방법////////////
SQL-PLUS로 시스템 로그인 한 후 
SQL> alter system set processes=500 scope=spfile;
SQL> alter system register;
SQL> shutdown immediate//?
SQL> startup//?





