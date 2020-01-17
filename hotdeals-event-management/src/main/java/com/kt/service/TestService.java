package com.kt.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.commons.service.AbstractService;
import com.kt.dto.response.TestResponse;
import com.kt.persistence.model.TestModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@SuppressWarnings("all")
@Transactional(readOnly = true)
public class TestService extends AbstractService {

	/**
	 * Getter Validation.
	 *
	 * @return ValidationResponse
	 */
	public TestResponse getTest() {
		int randomValue = (int) (Math.random() * 100 + 1);
		TestModel model = new TestModel();
		model.setUserId(String.valueOf(randomValue));
		model.setUserName("테스트" + randomValue);

		int status = HttpStatus.OK.value();
		// TestResponse res = new TestResponse(status, getResponseMessage(status));
		TestResponse res = new TestResponse(status, "SUCCESS");
		res.setData(model);
		return res;
	}

}
