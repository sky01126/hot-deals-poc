> #### [Redis Sentinel Install](./document/redis_sentinel_install.md)
>
> #### Docker Cassandra 실행 및 테이블 생성
> ```
> $ docker run --name cassandra -d -p 7000:7000 -p 9042:9042 cassandra
> ```
> ```
> $ CREATE KEYSPACE hotdeals WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor': 3};
> ```
> ```
> $ USE hotdeals;
> ```
> ```
> $ CREATE TABLE customer (
>     phone_no        text
>     , event_id      text
>     , name          text
>     , agreement     text
>     , fcfs_no       int
>     , pick_yn       blob
>     , timestamp     timestamp
>     , PRIMARY KEY (phone_no, event_id)
> );
> ```
>
> #### Cassandra 접속 Spring Boot Project 실행 시 JAVA_OTP 설정 (datastax Could not load JNR C Library 방지)
> ```
> -Dcom.datastax.driver.USE_NATIVE_CLOCK=false
> ```
>
> #### [연동규격서](./document/hotdeals.html)