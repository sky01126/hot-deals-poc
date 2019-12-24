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
 * MessageConfig.java
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@SuppressWarnings("all")
public class MessageProperty {

	/** 아이디가 공백인 경우의 메시지. */
	public static final String USERID_IS_EMPTY = "아이디를 입력해주세요.";

	/** 아이디의 길이 유효성체므 메시지. */
	public static final String USERID_LENGTH_VALIDATION = "아이디는 " + MemberProperty.MIN_USERID + "자리 이상, "
			+ MemberProperty.MAX_USERID + "자리 이하만 사용 가능합니다.";

	/** 아이디에 공백을 입력한 경우의 메시지. */
	public static final String USERID_IS_BLANK = "아이디에 공백을 입력할 수 없습니다.";

	/** 비밀번호가 공백인 경우의 메시지. */
	public static final String PASSWORD_IS_EMPTY = "비밀번호를 입력해주세요.";

	/** 비밀번호에 공백을 입력한 경우의 메시지. */
	public static final String PASSWORD_IS_BLANK = "비밀번호에 공백을 입력할 수 없습니다.";

	/** 비밀번호의 길이 유효성체므 메시지. */
	public static final String PASSWORD_LENGTH_VALIDATION = "비밀번호는 " + MemberProperty.MIN_PASSWD + "자리 이상, "
			+ MemberProperty.MAX_PASSWD + "자리 이하만 사용 가능합니다.";

	/** 로그인 실패 메시지. */
	public static final String LOGIN_FAIL = "아이디 또는 비밀번호를 확인해주세요.";

	/** 인증되지 않은 사용자 메시지. */
	public static final String ANONYMOUS = "로그인 후 다시 이용해주세요.";

	/** 인증 만료 메시지. */
	public static final String TOKEN_EXPIRE = "인증이 만료되었습니다.";

	/** 알수 없는 TOKEN에 대한 메시지. */
	public static final String TOKEN_UNKNOWN = "인증 토큰이 유효하지 않습니다.";

	/** 접근 권한 없음 메시지. */
	public static final String FORBIDDEN = "접근 권한이 없습니다.";

	private MessageProperty() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

}
