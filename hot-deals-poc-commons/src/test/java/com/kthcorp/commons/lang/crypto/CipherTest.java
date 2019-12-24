/*
 * Copyright ⓒ [2017] KTH corp.All rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kthcorp.commons.lang.crypto;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import com.kt.commons.config.MemberProperty;

/**
 * CipherTest.java
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see
 * @since 8.0
 */
@SuppressWarnings("all")
public class CipherTest {

	private static final Logger log = LoggerFactory.getLogger(CipherTest.class);

	@Test
	@Ignore
	@Order(1)
	public void testAes() throws CipherException {
		AESCipher cipher = AESCipher.instance(MemberProperty.AUTH_TOKEN_SECRET_KEY);
		String encrypt = cipher.encrypt("테스트");
		encrypt = cipher
				.encrypt("a123bc50-5996-d062-472f-c6b2-6a0613f4d7342261.ttL_xepg14XXlPSk07pBjcR-mS0QRdL2FTdI6baJaA4");
		log.debug("AESCipher - Encrypt: {}", encrypt);
		log.debug("AESCipher - Decrypt: {}", cipher.decrypt(encrypt));
	}

	@Test
	@Ignore
	@Order(2)
	public void testSeed() throws CipherException {
		SeedCipher cipher = SeedCipher.instance(MemberProperty.AUTH_TOKEN_SECRET_KEY);
		String encrypt = cipher.encrypt("테스트");
		encrypt = cipher
				.encrypt("a123bc50-5996-d062-472f-c6b2-6a0613f4d7342261.ttL_xepg14XXlPSk07pBjcR-mS0QRdL2FTdI6baJaA4");
		log.debug("SeedCipher - Encrypt: {}", encrypt);
		log.debug("SeedCipher - Decrypt: {}", cipher.decrypt(encrypt));
	}

}
