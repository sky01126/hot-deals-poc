package com.kt.persistence.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.kt.persistence.model.CassandraCustomer;
import com.kt.persistence.model.CassandraCustomerKey;


@Repository
public interface CustomerCassandraRepository extends CassandraRepository<CassandraCustomer, CassandraCustomerKey> {
}
