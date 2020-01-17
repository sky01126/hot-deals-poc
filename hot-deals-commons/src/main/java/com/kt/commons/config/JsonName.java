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
 * Json Name 설정
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@SuppressWarnings("all")
public class JsonName {

	/** 최초 생성일 */
	public static final String CREATE_DATE = "create_date";

	/** 현재 시간 */
	public static final String CURRENT_DATE = "current_date";

	/** 데이터. */
	public static final String DATA = "data";

	/** Result Error List */
	public static final String ERRORS = "errors";

	/** 리스트. */
	public static final String LIST = "list";

	/** 현재 페이지 */
	public static final String NOW_PAGE = "page";

	/** 한 페이지에 보여줄 ROW */
	public static final String NOW_PAGE_SIZE = "size";

	/** Result Code */
	public static final String RESULT_CODE = "result_code";

	/** Result Message */
	public static final String RESULT_MESSAGE = "result_msg";

	/** 일련번호 */
	public static final String SEQUENCE = "seq";

	/** Result Token */
	public static final String TOKEN = "token";

	/** 전체 페이지 */
	public static final String TOTAL_PAGE = "total_page";

	/** 전체 페이지 ROW */
	public static final String TOTAL_SIZE = "total_size";

	/** 최종 수정일 */
	public static final String UPDATE_DATE = "update_date";

	/** 사용여부 코드 */
	public static final String USE_CODE = "use_code";

	/** 사용자 아이디. */
	public static final String USER_ID = "userid";

	/** 사용자명. */
	public static final String USER_NAME = "user_name";

	/** 사용자 비밀번호 */
	public static final String USER_PASSWORD = "passwd";

	private JsonName() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

}
