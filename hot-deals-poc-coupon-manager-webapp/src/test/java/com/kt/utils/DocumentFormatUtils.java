package com.kt.utils;

import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.snippet.Attributes;

public class DocumentFormatUtils {

	public static Attributes.Attribute dateFormat() {
		return key("format").value("yyyy-MM-dd HH:mm:ss");
	}

}
