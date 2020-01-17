/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.enums.type;

import com.kthcorp.commons.lang.StringUtils;

/**
 * 빌드 / 배포 환경 설정 정보.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
public enum RoleTypes {

	/** 어드민. */
	ROLE_ADMIN("ROLE_ADMIN"),

	/** 일반 사용자. */
	ROLE_USER("ROLE_USER"),

	/** 권한이 없는 사용자. */
	ROLE_ANONYMOUS("ROLE_ANONYMOUS");

	private String value;

	RoleTypes(String value) {
		this.value = value;
	}

	/**
	 * Getter Value
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Value를 이용해서 Enum Object를 리턴한다.
	 *
	 * @param value the value
	 * @return the role types
	 */
	public static RoleTypes getObject(String value) {
		for (RoleTypes type : values()) {
			if (StringUtils.equalsAnyIgnoreCase(value, type.getValue())) {
				return type;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
