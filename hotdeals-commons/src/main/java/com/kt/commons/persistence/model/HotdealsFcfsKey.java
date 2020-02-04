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
public class HotdealsFcfsKey {

	@Getter
	@Setter
	@JsonProperty("phone_no")
	@PrimaryKeyColumn(name = "phone_no", type = PARTITIONED)
	private String phoneNo;

	@Getter
	@Setter
	@JsonProperty("event_id")
	@PrimaryKeyColumn(name = "event_id", ordinal = 0)
	private String eventId;

	public HotdealsFcfsKey() {
		// ignore..
	}

}
