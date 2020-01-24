package com.kt.web.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;

import com.kt.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // WebEnvironment.RANDOM_PORT
public class HotdealsControllerTest extends AbstractControllerTest {

	@Test
	// @formatter:off
	public void testGetInitEvent() throws Exception {
		mockMvc.perform(get("/api/v1/event/init")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(document.document(
                        responseFields(
                                fieldWithPath("result_code").type(JsonFieldType.NUMBER).description("Result Code (200 OK)")
                                , fieldWithPath("result_msg").type(JsonFieldType.STRING).description("Result Message")
                                , fieldWithPath("data.event_id").type(JsonFieldType.STRING).description("이벤트 아이디")
                                , fieldWithPath("data.event_type").type(JsonFieldType.NUMBER).description("이벤트 타입 - 2 : 응모형 이벤트, 3 : 선착순+응모형 이벤트")
                                , fieldWithPath("data.close_yn").type(JsonFieldType.BOOLEAN).description("선착순+응모형 이벤트에서 선착순 마감 여부 (true : 선착순 마감, false : 선착순 진행중)")
                        )
				));
	}
	// @formatter:on

}
