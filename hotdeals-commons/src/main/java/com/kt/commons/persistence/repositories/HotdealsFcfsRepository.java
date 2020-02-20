package com.kt.commons.persistence.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.kt.commons.persistence.model.HotdealsFcfs;
import com.kt.commons.persistence.model.HotdealsFcfsKey;

@Repository
public interface HotdealsFcfsRepository extends CassandraRepository<HotdealsFcfs, HotdealsFcfsKey> {
}
