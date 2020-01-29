package com.kt.persistence.model;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@PrimaryKeyClass
public class HotdealsPickKey {

	@Getter
	@Setter
	@PrimaryKeyColumn(name = "phone_no", type = PARTITIONED)
	private String phoneNo;

	@Getter
	@Setter
	@PrimaryKeyColumn(name = "event_id", ordinal = 0)
	private String eventId;

	public HotdealsPickKey() {
		// ignore..
	}

}
