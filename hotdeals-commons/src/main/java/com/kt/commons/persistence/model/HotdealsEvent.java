package com.kt.commons.persistence.model;

import org.joda.time.DateTime;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kthcorp.commons.lang.BaseObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Table("hotdeals_event")
public class HotdealsEvent extends BaseObject {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@PrimaryKey
	@JsonProperty("key")
	private HotdealsEventKey key;

	@Getter
	@Setter
	@Column("date_from")
	@JsonProperty("date_from")
	private DateTime dateFrom;

	@Getter
	@Setter
	@Column("date_to")
	@JsonProperty("date_to")
	private DateTime dateTo;

	@Getter
	@Setter
	@Column("event_name")
	@JsonProperty("event_name")
	private String eventName;

	@Getter
	@Setter
	@Column("event_type")
	@JsonProperty("event_type")
	private String eventType;

	@Getter
	@Setter
	@Column("fcfs_num")
	@JsonProperty("fcfs_num")
	private int fcfsNum;

	public HotdealsEvent() {
		// ignore..
	}

}
