package com.kt.persistence.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@PrimaryKeyClass
public class CassandraCustomerKey implements Serializable {

	@PrimaryKeyColumn(name = "phone_no", type = PARTITIONED)
	private String phoneNo;

	@PrimaryKeyColumn(name = "event_id", ordinal = 0)
	private String eventId;

	public CassandraCustomerKey(final String phoneNo, final String eventId) {
		this.phoneNo = phoneNo;
		this.eventId = eventId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	@Override
	public String toString() {
		return "CustomerKey{" + "phoneNo='" + phoneNo + '\'' + ", eventId=" + eventId + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CassandraCustomerKey customerKey = (CassandraCustomerKey) o;

		if (phoneNo != null ? !phoneNo.equals(customerKey.phoneNo) : customerKey.phoneNo != null)
			return false;
		return eventId != null ? !eventId.equals(customerKey.eventId) : customerKey.eventId != null;
	}

	@Override
	public int hashCode() {
		int result = phoneNo != null ? phoneNo.hashCode() : 0;
		result = 31 * result + (eventId != null ? eventId.hashCode() : 0);

		return result;
	}
}
