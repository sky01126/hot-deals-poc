package com.kt.persistence.model;

import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("customer")
public class CassandraCustomer {

	@PrimaryKey
	private CassandraCustomerKey key;

	@Column("name")
	private String name;
	
	@Column("refresh_time")
	private LocalDateTime refreshTime;

	public CassandraCustomer(CassandraCustomerKey key, String name, LocalDateTime refreshTime) {
		this.key = key;
		this.name = name;
		this.refreshTime = refreshTime;
	}

	@Override
	public String toString() {
		return "Customer{" + "key=" + key + ", name=" + name + ", refreshTime=" + refreshTime + '}';
	}
}
