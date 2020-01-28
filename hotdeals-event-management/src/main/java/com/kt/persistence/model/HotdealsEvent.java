package com.kt.persistence.model;

import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.kthcorp.commons.lang.BaseObject;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Table("hotdeals_event")
public class HotdealsEvent extends BaseObject {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private HotdealsEventKey key;

	@Column("event_name")
	private String eventName;

	@Column("event_type")
	private String eventType;

	@Column("fcfs_num")
	private int fcfsNum;

	@Column("date_from")
	private LocalDateTime dateFrom;

	@Column("date_to")
	private LocalDateTime dateTo;

}
