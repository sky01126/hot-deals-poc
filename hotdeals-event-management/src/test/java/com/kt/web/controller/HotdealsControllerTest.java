package com.kt.web.controller;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.common.collect.Maps;
import com.kt.Application;
import com.kthcorp.commons.lang.JsonUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HotdealsControllerTest extends AbstractControllerTest {

	@Test
	@Order(1)
	// @formatter:off
	public void testInitEventInfo() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/event/init")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(document.document(
						PayloadDocumentation.responseFields(
                        		PayloadDocumentation.fieldWithPath("result_code").type(JsonFieldType.NUMBER).description("Result Code (200 OK)")
                                , PayloadDocumentation.fieldWithPath("result_msg").type(JsonFieldType.STRING).description("Result Message")
                                , PayloadDocumentation.fieldWithPath("data.event_id").type(JsonFieldType.STRING).description("이벤트 아이디")
                                , PayloadDocumentation.fieldWithPath("data.event_type").type(JsonFieldType.NUMBER).description("이벤트 타입 (2 : 응모형 이벤트, 3 : 선착순+응모형 이벤트)")
                                , PayloadDocumentation.fieldWithPath("data.close_yn").type(JsonFieldType.BOOLEAN).description("선착순+응모형 이벤트에서 선착순 마감 여부 (true : 선착순 마감, false : 선착순 진행중)")
                        )
				));
	}
	// @formatter:on

	@Test
	@Order(1)
	// @formatter:off
	public void testPutEventInfo() throws Exception {
		Map<String, Object> req = Maps.newLinkedHashMap();
		req.put("event_id", "2020010101");
		req.put("phone_no", "01011111111");
		req.put("name", "TEST");
		req.put("aggrement", true);


		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/event/type/{EVENT_TYPE}", 3)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtils.getJson(req)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(document.document(
						RequestDocumentation.pathParameters(
								RequestDocumentation.parameterWithName("EVENT_TYPE").description("이벤트 타입 - 2 : 응모형 이벤트, - 3 : 선착순+응모형 이벤트")
	                      ),
						PayloadDocumentation.requestFields(
								PayloadDocumentation.fieldWithPath("event_id").type(JsonFieldType.STRING).description("이벤트 아이디")
								  , PayloadDocumentation.fieldWithPath("phone_no").type(JsonFieldType.STRING).description("핸드폰번호")
								  , PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
								  , PayloadDocumentation.fieldWithPath("aggrement").type(JsonFieldType.BOOLEAN).description("동의여부")
						  ),
						PayloadDocumentation.responseFields(
                        		PayloadDocumentation.fieldWithPath("result_code").type(JsonFieldType.NUMBER).description("Result Code (200 OK)")
                                , PayloadDocumentation.fieldWithPath("result_msg").type(JsonFieldType.STRING).description("Result Message")
                                , PayloadDocumentation.fieldWithPath("data.event_id").type(JsonFieldType.STRING).description("이벤트 아이디")
                                , PayloadDocumentation.fieldWithPath("data.event_type").type(JsonFieldType.NUMBER).description("이벤트 타입 (2 : 응모형 이벤트, 3 : 선착순+응모형 이벤트)")
                                , PayloadDocumentation.fieldWithPath("data.duplicate_yn").type(JsonFieldType.BOOLEAN).description("이벤트 중복 등록 여부 (true: 중복 등록, false : 최초 등록)")
                        )
				));
	}
	// @formatter:on

}
