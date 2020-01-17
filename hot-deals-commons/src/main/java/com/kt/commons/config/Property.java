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

import java.io.Serializable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kthcorp.commons.lang.NumberUtils;
import com.kthcorp.commons.lang.StringUtils;

/**
 * YAML Property
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @see Serializable
 * @since 8.0
 */
public class Property implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(Property.class);

	private static Properties properties;

	private Property() throws IllegalAccessException {
		throw new IllegalAccessException("access to class not allowed.");
	}

	/**
	 * Property를 설정한다.
	 *
	 * @param prop setting property.
	 */
	public static void set(final Properties prop) {
		if (Property.properties == null) {
			log.info("Setting Properties:: {}", prop);
			Property.properties = prop;
		} else {
			log.info("Properties is not null...");
		}
	}

	/**
	 * Property Key의 String Value 리턴.
	 *
	 * @param key the property key.
	 * @return the property string value.
	 */
	public static String get(final String key) {
		return StringUtils.trim(properties.getProperty(key));
	}

	/**
	 * Getting Response Status Result Message.
	 * <p>
	 * 중괄호"{NUMBER}"를 Replace String으로 변환한다.
	 *
	 * <pre>
	 * <code>
	 * test.test: Test0 {0}. Test1 {1}...
	 * </code>
	 * </pre>
	 *
	 * @param key the result key
	 * @param replace the replace string
	 * @return the result message
	 */
	public String get(final String key, final String replace) {
		String message = StringUtils.trim(properties.getProperty(key));
		return StringUtils.replaceIgnoreCase(message, "{0}", replace);
	}

	/**
	 * Getting Response Status Result Message.
	 * <p>
	 * 중괄호"{NUMBER}"를 Replace String으로 변환한다.
	 *
	 * <pre>
	 * <code>
	 * test.test: Test0 {0}. Test1 {1}...
	 * </code>
	 * </pre>
	 *
	 * @param key the result key
	 * @param replaces the replace string array
	 * @return the result message
	 */
	public String get(final String key, final String[] replaces) {
		String message = StringUtils.trim(properties.getProperty(key));
		for (int i = 0; i < replaces.length; i++) {
			message = StringUtils.replaceIgnoreCase(message, "{" + i + "}", replaces[i]);
		}
		return message;
	}

	/**
	 * Property Key의 String Value 리턴.
	 *
	 * @param key the property key.
	 * @param searchString the String to search for, may be null
	 * @param replaceString the String to replace it with, may be null
	 * @return the property string value.
	 */
	public static String get(final String key, final String searchString, final String replaceString) {
		return StringUtils.replaceIgnoreCase(StringUtils.trim(properties.getProperty(key)), searchString,
				replaceString);
	}

	/**
	 * Property Key의 String Value 리턴.
	 *
	 * @param key the property key.
	 * @param searchList the Strings to search for, no-op if null
	 * @param replaceStrings the Strings to replace them with, no-op if null
	 * @return the property string value.
	 */
	public static String get(final String key, final String[] searchList, final String[] replaceStrings) {
		return StringUtils.replaceEach(StringUtils.trim(properties.getProperty(key)), searchList, replaceStrings);
	}

	/**
	 * Property Key의 Integer Value 리턴.
	 *
	 * @param key the property key.
	 * @return the int represented by the string, or <code>0</code> if conversion fails
	 */
	public static int getInt(final String key) {
		return getInt(key, 0);
	}

	/**
	 * Property Key의 Integer Value 리턴.
	 *
	 * @param key the property key.
	 * @param defaultValue the default value
	 * @return the int represented by the string, or the default if conversion fails
	 */
	public static int getInt(String key, final int defaultValue) {
		return NumberUtils.toInt(StringUtils.trim(properties.getProperty(key)), defaultValue);
	}

	/**
	 * Property Key의 Long Value 리턴.
	 *
	 * @param key the property key.
	 * @return the long represented by the string, or <code>0</code> if conversion fails
	 */
	public static long getLong(final String key) {
		return getLong(key, 0);
	}

	/**
	 * Property Key의 Long Value 리턴.
	 *
	 * @param key the property key.
	 * @param defaultValue the default value
	 * @return the long represented by the string, or the default if conversion fails
	 */
	public static long getLong(String key, final int defaultValue) {
		return NumberUtils.toLong(StringUtils.trim(properties.getProperty(key)), defaultValue);
	}

	/**
	 * Property Key의 Float Value 리턴.
	 *
	 * @param key the property key.
	 * @return the float represented by the string, or <code>0.0f</code> if conversion fails
	 */
	public static float getFloat(final String key) {
		return getFloat(key, 0);
	}

	/**
	 * Property Key의 Float Value 리턴.
	 *
	 * @param key the property key.
	 * @param defaultValue the default value
	 * @return the float represented by the string, or defaultValue if conversion fails
	 */
	public static float getFloat(String key, final int defaultValue) {
		return NumberUtils.toFloat(StringUtils.trim(properties.getProperty(key)), defaultValue);
	}

	/**
	 * Property Key의 Float Value 리턴.
	 *
	 * @param key the property key.
	 * @return the double represented by the string, or <code>0.0f</code> if conversion fails
	 */
	public static double getDouble(final String key) {
		return getDouble(key, 0);
	}

	/**
	 * Property Key의 Float Value 리턴.
	 *
	 * @param key the property key.
	 * @param defaultValue the default value
	 * @return the double represented by the string, or defaultValue if conversion fails
	 */
	public static double getDouble(String key, final int defaultValue) {
		return NumberUtils.toDouble(StringUtils.trim(properties.getProperty(key)), defaultValue);
	}

}
