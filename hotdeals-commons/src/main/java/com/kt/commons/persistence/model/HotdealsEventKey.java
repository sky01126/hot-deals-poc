package com.kt.commons.persistence.model;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@PrimaryKeyClass
public class HotdealsEventKey {

	@Getter
	@Setter
	@JsonProperty("event_id")
	@PrimaryKeyColumn(name = "event_id", type = PARTITIONED)
	private String eventId;

	public HotdealsEventKey() {
		// ignore..
	}

}
