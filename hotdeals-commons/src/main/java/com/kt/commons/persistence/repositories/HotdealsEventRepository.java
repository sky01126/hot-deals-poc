package com.kt.commons.persistence.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.kt.commons.persistence.model.HotdealsEvent;
import com.kt.commons.persistence.model.HotdealsEventKey;

@Repository
public interface HotdealsEventRepository extends CassandraRepository<HotdealsEvent, HotdealsEventKey> {
}
