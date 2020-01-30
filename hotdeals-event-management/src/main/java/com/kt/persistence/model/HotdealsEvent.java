package com.kt.persistence.model;

import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

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
	private HotdealsEventKey key;

	@Getter
	@Setter
	@Column("date_from")
	private LocalDateTime dateFrom;

	@Getter
	@Setter
	@Column("date_to")
	private LocalDateTime dateTo;

	@Getter
	@Setter
	@Column("event_name")
	private String eventName;

	@Getter
	@Setter
	@Column("event_type")
	private String eventType;

	@Getter
	@Setter
	@Column("fcfs_num")
	private int fcfsNum;

	public HotdealsEvent() {
		// ignore..
	}

}
