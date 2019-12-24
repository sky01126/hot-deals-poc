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
 * Cache Name
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
public class CacheName {

	/**
	 * Default Cache Name
	 */
	public static final String DEFAULT_CACHE = "default-cache";

	/**
	 * 영구적인 Cache Name
	 */
	public static final String PERMANENT_CACHE = "permanent-cache";

	/**
	 * Spring Security에 서 사용되는 Cache Name
	 */
	public static final String SECURITY_CACHE = "security-cache";

	private CacheName() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

}
