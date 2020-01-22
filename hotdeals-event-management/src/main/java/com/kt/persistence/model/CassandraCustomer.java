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
	
	@Column("agreement")
	private boolean agreement;
	
	@Column("timestamp")
	private LocalDateTime timestamp;
	
	@Column("fcfs_no")
	private int fcfsNo;
	
	@Column("pick_yn")
	private boolean pickYn;

	public CassandraCustomer(CassandraCustomerKey key, String name, 
			boolean agreement, LocalDateTime timestamp, int fcfsNo, boolean pickYn) {
		this.key = key;
		this.name = name;
		this.agreement = agreement;
		this.timestamp = timestamp;
		this.fcfsNo = fcfsNo;
		this.pickYn = pickYn;
	}

	@Override
	public String toString() {
		return "Customer{" + "key=" + key + ", name=" + name + ", agreement=" + agreement 
				+ ", timestamp=" + timestamp + ", fcfsNo=" + fcfsNo + ", pickYn=" + pickYn 
				+ '}';
	}
}
