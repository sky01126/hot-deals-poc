package com.kt.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;

public class CustomResponseFieldsSnippet extends AbstractFieldsSnippet {

	public CustomResponseFieldsSnippet(String type, List<FieldDescriptor> descriptors) {
		super(type, descriptors, null, false);
	}

	public CustomResponseFieldsSnippet(String type, List<FieldDescriptor> descriptors,
			boolean ignoreUndocumentedFields) {
		super(type, descriptors, null, ignoreUndocumentedFields);
	}

	public CustomResponseFieldsSnippet(String type, List<FieldDescriptor> descriptors, Map<String, Object> attributes,
			boolean ignoreUndocumentedFields) {
		super(type, descriptors, attributes, ignoreUndocumentedFields);
	}

	@Override
	protected MediaType getContentType(Operation operation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected byte[] getContent(Operation operation) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
