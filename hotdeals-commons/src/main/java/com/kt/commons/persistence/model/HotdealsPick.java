package com.kt.commons.persistence.model;

import org.joda.time.DateTime;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.kthcorp.commons.lang.BaseObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Table("hotdeals_pick")
public class HotdealsPick extends BaseObject {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@PrimaryKey
	private HotdealsPickKey key;

	@Getter
	@Setter
	@Column("name")
	private String name;

	@Getter
	@Setter
	@Column("agreement")
	private boolean agreement;

	@Getter
	@Setter
	@Column("pick_yn")
	private boolean pickYn;

	@Getter
	@Setter
	@Column("timestamp")
	private DateTime timestamp;

	public HotdealsPick() {
		// ignore..
	}
}
