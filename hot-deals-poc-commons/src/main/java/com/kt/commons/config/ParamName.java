/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */
package com.kt.commons.config;

/**
 * Parameter Name 설정.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@SuppressWarnings("all")
public class ParamName {

	/** 사용자 아이디 */
	public static final String USER_ID = JsonName.USER_ID;

	/** 사용자 비밀번호 */
	public static final String USER_PASSWORD = JsonName.USER_PASSWORD;

	private ParamName() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

}
