> #### [Redis Sentinel Install](./document/redis_sentinel_install.md)
>
> #### Docker Cassandra 실행 및 테이블 생성
> ```
> $ docker run --name cassandra -d -p 7000:7000 -p 9042:9042 cassandra
> ```
> ```
> $ create keyspace if not exists hotdeals
WITH replication = {
'class' : 'NetworkTopologyStrategy',
'DC1-kt-data-garage' : 3
};
> ```
> ```
> $ USE hotdeals;
> ```
> ```
> $ create table hotdeals_event (
  event_id    text primary key,
  event_name  text,
  event_type  text,
  fcfs_num    int,
  date_from   timestamp,
  date_to     timestamp
);
> ```
> ```
> $ create table hotdeals_fcfs (
  phone_no    text,
  event_id    text,
  name        text,
  agreement   boolean,
  fcfs_no     int,
  timestamp   timestamp,
  primary key (phone_no, event_id)
);
> ```
> ```
> $ create table hotdeals_pick (
  phone_no    text,
  event_id    text,
  name        text,
  agreement   boolean,
  pick_yn     boolean,
  timestamp   timestamp,
  primary key (phone_no, event_id)
);
> ```
> ```
> $ insert into hotdeals_event (event_id, event_name, event_type, fcfs_num, date_from, date_to)
values ('2020011701','ipod_half','F',100,'2020-01-17 17:00+09','2020-01-17 19:00+09');
);
> ```
>
> #### Cassandra 접속 Spring Boot Project 실행 시 JAVA_OTP 설정 (datastax Could not load JNR C Library 방지)
> ```
> -Dcom.datastax.driver.USE_NATIVE_CLOCK=false
> ```
>
> #### [연동규격서](./document/hotdeals.html)