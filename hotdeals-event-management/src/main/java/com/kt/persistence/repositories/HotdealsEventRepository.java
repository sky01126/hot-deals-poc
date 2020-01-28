package com.kt.persistence.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.kt.persistence.model.HotdealsEvent;
import com.kt.persistence.model.HotdealsEventKey;

@Repository
public interface HotdealsEventRepository extends CassandraRepository<HotdealsEvent, HotdealsEventKey> {
}
