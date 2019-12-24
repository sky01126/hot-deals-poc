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
 * View Page 경로, Model And View URL, Restful URL 설정.
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@SuppressWarnings("all")
public class UrlName {

	// ---------------------------------------- Model And View Page
	/** 사용자 로그인 VIEW */
	public static final String VIEW_MEMBER_LOGIN = "member/login";

	/** 사용자 신규가입 VIEW */
	public static final String VIEW_MEMBER_JOIN = "member/join";

	// ---------------------------------------- Model And View URL
	/** 사용자 BASE URL */
	public static final String MEMBER = "/member";

	/** 사용자 로그인 URL */
	public static final String MEMBER_LOGIN = MEMBER + "/login";

	/** 사용자 신규가입 URL */
	public static final String MEMBER_JOIN = MEMBER + "/join";

	/** 사용자 로그인 실패 URL */
	public static final String MEMBER_LOGIN_FAIL = MEMBER_LOGIN + "?error=true";

	/** 사용자 로그아웃 URL */
	public static final String MEMBER_LOGOUT = MEMBER + "/logout";

	/** 사용자 로그아웃 성공 후 이동 URL */
	public static final String MEMBER_LOGOUT_SUCCESS = MEMBER_LOGIN + "?logout";

	// ---------------------------------------- Restful URL
	/** RESTFul Base URL */
	public static final String REST_PREFIX = "/api";

	/** RESTFul Member URL */
	public static final String REST_MEMBER = REST_PREFIX + MEMBER;

	/** 로그인 API URL (Security Ignore에 등록되면 Filter가 아닌 Controller로 접속된다.) */
	public static final String REST_MEMBER_LOGIN = REST_MEMBER + "/login/submit";

	/** 로그아웃 API URL */
	public static final String REST_MEMBER_LOGOUT = REST_MEMBER + "/logout/submit";

	/** 회원가입 API URL */
	public static final String REST_MEMBER_JOIN = REST_MEMBER + "/join/submit";

	/** 유효성검증 API URL */
	public static final String REST_MEMBER_VALIDATE = REST_MEMBER + "/validate";

	/** 사용자 아이디 유효성 체크 API URL */
	public static final String REST_MEMBER_VALIDATE_USER_ID = REST_MEMBER_VALIDATE + "/userid";

	/** 사용자 비밀번호 유효성 체크 API URL */
	public static final String REST_MEMBER_VALIDATE_PASSWORD = REST_MEMBER_VALIDATE + "/passwd";

	/** 사용자에게 권한 부여된 메뉴 리스트 URL */
	public static final String REST_PROGRAM_MEMU = REST_MEMBER + "/program/menu";

	private UrlName() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

}
