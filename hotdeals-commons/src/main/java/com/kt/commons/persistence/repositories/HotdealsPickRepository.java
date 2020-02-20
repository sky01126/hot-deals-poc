package com.kt.commons.persistence.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.kt.commons.persistence.model.HotdealsPick;
import com.kt.commons.persistence.model.HotdealsPickKey;

@Repository
public interface HotdealsPickRepository extends CassandraRepository<HotdealsPick, HotdealsPickKey> {
}
