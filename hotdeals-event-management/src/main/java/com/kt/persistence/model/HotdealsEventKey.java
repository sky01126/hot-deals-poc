package com.kt.persistence.model;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@PrimaryKeyClass
public class HotdealsEventKey {

	@Getter
	@Setter
	@PrimaryKeyColumn(name = "event_id", type = PARTITIONED)
	private String eventId;

	public HotdealsEventKey() {
		// ignore..
	}

}
