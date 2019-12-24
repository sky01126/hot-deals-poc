/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt.commons.config;

import com.kthcorp.commons.lang.http.HttpHeaders;

/**
 * 회원 정보에서 사용되는 Config
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@SuppressWarnings("all")
public class MemberProperty {

	// ------------------------------ 사용자 아이디 / 비밀번호 싸이즈.
	/** 사용자 아이디의 최소 자릿수. */
	public static final int MIN_USERID = 5;

	/** 사용자 아이디의 최대 자릿수. */
	public static final int MAX_USERID = 12;

	/** 비밀번호의 최소 자릿수. */
	public static final int MIN_PASSWD = 8;

	/** 비밀번호의 최대 자릿수. */
	public static final int MAX_PASSWD = 12;

	// ------------------------------ 로그인 Config
	/** TOKEN Expire Time (초단위) */
	public static final int TOKEN_EXPIRE_SECONDS = 365 * 24 * 60 * 60;

	/** Token 분리 기호. */
	public static final String TOKEN_SEPARATOR = ".";

	public static final Boolean IS_AUTH_TOKEN_EXPIRE = Boolean.TRUE;

	/** 인증 토큰명. */
	public static final String AUTH_TOKEN_HEADER_NAME = HttpHeaders.AUTH_TOKEN;

	public static final String AUTH_TOKEN_SECRET_KEY = "example.com";

	// ------------------------------ 로그인 아이디 / 비밀번호
	/** 사용자 아이디 KEY */
	public static final String SECURITY_USERNAME_KEY = ParamName.USER_ID;

	/** 사용자 비밀번호 KEY */
	public static final String SECURITY_PASSWORD_KEY = ParamName.USER_PASSWORD;

	/** 사용자 ROLE KEY */
	public static final String SECURITY_ROLE_KEY = "role";

	private MemberProperty() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

}
