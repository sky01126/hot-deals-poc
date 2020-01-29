package com.kt.persistence.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.kt.persistence.model.HotdealsFcfs;
import com.kt.persistence.model.HotdealsFcfsKey;

@Repository
public interface HotdealsFcfsRepository extends CassandraRepository<HotdealsFcfs, HotdealsFcfsKey> {
}
