# OS 설정 변경

### 메모리를 다 사용해도 충분한 메모리가 있는 것처럼 처리
 
```
$ sudo echo "vm.overcommit_memory = 1" | sudo tee -a /etc/sysctl.conf
```
 
- 적용 및 확인

```
$ sudo sysctl -p && sysctl vm.overcommit_memory
```

```
. 0 - 적당히 휴리스틱하게 처리
. 1 - 항상 허용
. 2 - vm.overcommit_memory_ratio 에 적용된 범위까지만 허용
```

### 네트워크 연결 개수 설정

```
$ sudo echo "net.core.somaxconn = 20480" | sudo tee -a /etc/sysctl.conf
```

 
- 적용 및 확인.

```
$ sudo sysctl -p && sysctl net.core.somaxconn
```

### ulimit 수정

```
$ sudo vi /etc/security/limits.conf
```

```
# End of file
*            soft    nproc  8192
*            hard    nproc  16384
*            soft    nofile 8192
*            hard    nofile 65536
```
위의 옵션 설정 후 터미널 재접속 후 확인

```
$ ulimit -a
```

### System Hang 을 막기 위해 THP 사용 안하도록 설정 (옵션)

```
$ sudo sh -c "echo never > /sys/kernel/mm/transparent_hugepage/enabled"
```

- 그리고 재 부팅시 재설정을 변경하기 위해 /etc/rc.local 에 위의 명령어를 넣어 주도록한다.

```
$ sudo vi /etc/rc.local
```

- touch 밑 설정한다.
- exit 0를 설정한 경우 exit 0 위에 넣어준다.

```
echo never > /sys/kernel/mm/transparent_hugepage/enabled
```

# Redis Server 설정
서버명  | PORT
------------- | -------------
Master Node  | 7001
Slave Node | 7002

```
+------------------+      +---------------+
|      Master      | ---> |    Replica    |
| (receive writes) |      |  (exact copy) |
+------------------+      +---------------+
```
## Master / Slaser 서버 설정 변경
Redis Config에서 "dbfilename"을 주석 처리해도 daum.rdb 파일은 생성된다.

bind를 실제 PublicIP 또는 PrivateIP로 변경해서 접속 할 수 있도록한다.

## Slave 설정 변경
Redis Slave에 아래 Config를 Master 서버 정보로 변경한다.

```
# replicaof <masterip> <masterport>
replicaof 10.211.55.4 7001
```

# Redis Sentinel 설정
## Sentinel의 기능
1. 모니터링
2. 알림기능
3. 페일오버
4. 환경 구성 프로바이더

Sentinel은 홀수(1, 3, 5)를 사용해야한다.

## Sentinel 설정 변경
Sentinel이 감시할 레디스 Master 인스턴스 정보를 넣어준다.

quorum 은 의사결정에 필요한 최소 Sentinel 노드 수라고 생각하면된다.

즉, 3대의 Sentinel을 설정한 경우 과반수가 되는 2개의 Sentinel이 동의하면 의사결정이 진행된다.

단 1대인 경우에는 1로 설정한다.

그리고 IP 주소는 로컬에 아닌 실제 통신에 사용되는 IP 설정.

```
# sentinel monitor mymaster <redis master host> <redis master port> <quorum>
$ sentinel monitor mymaster 10.211.55.4 7001 1
```

- SDOWN vs ODOWN

```
More advanced concepts이라는 페이지에서 SDOWN과 ODOWN이라는 단어가 나온다.
SDOWN은 Subjectively Down condition의 축약어이고 ODOWN은 Objectively Down condition의 축약어이다.
SDOWN은 Sentinel 인스턴스가 Master와 접속이 끊긴 경우 주관적인 다운 상태로 바뀐다.
이것은 잠시 네트워크 순단 등으로 인해 일시적인 현상일 수 있으므로 우선 SDOWN 상태가 됩니다.
그러나 SDOWN 상태인 Sentinel 서버들이 많아지면 ODOWN 상태(quorum), 즉 객관적인 다운 상태로 바뀐다.
이때부터 실질적인 페일오버(failover) 작업이 시작된다.
```

sentinel down-after-milliseconds : 단위는 millisecond, 마스터가 다운되었다고 인지하는 시간, Default는 30초이나 3초에서 5초 사이로 설정,

```
sentinel down-after-milliseconds mymaster 3000
```

## Sentinel 접속 후 확인

```
$ redis-cli -a qwer1234 -h 10.211.55.4 -p 26379

10.211.55.4:26379> info sentinel
```

## Sentinel 접속 Failover

```
10.211.55.4:26379> sentinel failover mymaster
```




# 참조
[Redis - Redis 설치 및 설정, 간단한 사용방법](https://coding-start.tistory.com/126?category=791662)

[Redis SENTINEL Introduction](http://redisgate.jp/redis/sentinel/sentinel.php)

[Redis SENTINEL FAILOVER](http://redisgate.jp/redis/sentinel/sentinel_failover.php)

