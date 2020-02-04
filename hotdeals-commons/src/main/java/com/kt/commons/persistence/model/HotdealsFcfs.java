package com.kt.commons.persistence.model;

import org.joda.time.DateTime;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kthcorp.commons.lang.BaseObject;

import lombok.Getter;
import lombok.Setter;

//@AllArgsConstructor
@Table("hotdeals_fcfs")
public class HotdealsFcfs extends BaseObject {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@PrimaryKey
	@JsonProperty("key")
	private HotdealsFcfsKey key;

	@Getter
	@Setter
	@Column("name")
	@JsonProperty("name")
	private String name;

	@Getter
	@Setter
	@Column("agreement")
	@JsonProperty("agreement")
	private boolean agreement;

	@Getter
	@Setter
	@Column("fcfs_no")
	@JsonProperty("fcfs_no")
	private String fcfsNo;

	@Getter
	@Setter
	@Column("timestamp")
	@JsonProperty("timestamp")
	private DateTime timestamp;

}
