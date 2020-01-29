package com.kt.persistence.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.kt.persistence.model.HotdealsPick;
import com.kt.persistence.model.HotdealsPickKey;

@Repository
public interface HotdealsPickRepository extends CassandraRepository<HotdealsPick, HotdealsPickKey> {
}
