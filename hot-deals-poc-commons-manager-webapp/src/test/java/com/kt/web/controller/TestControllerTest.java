package com.kt.web.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;

import com.kt.Application;
import com.kt.dto.request.TestRequest;
import com.kthcorp.commons.lang.JsonUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestControllerTest extends AbstractControllerTest {

	@Test
	@Order(1)
	@Ignore
	// @formatter:off
	public void testDoGet() throws Exception {
		mockMvc.perform(get("/test/get")
				.param("id", "id")
				.param("name", "name")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(document.document(
						requestParameters(
								parameterWithName("id").description("아이디 파라미터")
								, parameterWithName("name").description("이름 파라미터").optional()
						),
                        responseFields(
                                fieldWithPath("result_code").type(JsonFieldType.NUMBER).description("Result Code (200 OK)")
                                , fieldWithPath("result_msg").type(JsonFieldType.STRING).description("Result Message")
                                , fieldWithPath("data.userid").type(JsonFieldType.STRING).description("사용자 아이디")
                                , fieldWithPath("data.user_name").type(JsonFieldType.STRING).description("사용자명")
                        )
				));

	}
	// @formatter:on

	@Test
	@Order(2)
	@Ignore
	// @formatter:off
	public void testDoPost() throws Exception {
		mockMvc.perform(post("/test/post")
			  .param("id", "userid")
			  .param("name", "username")
			  .accept(MediaType.APPLICATION_JSON)
			  .contentType(MediaType.APPLICATION_JSON))
			  .andDo(print())
			  .andExpect(status().isOk())
			  .andDo(document.document(
					  requestParameters(
								parameterWithName("id").description("아이디 파라미터")
								, parameterWithName("name").description("이름 파라미터").optional()
					  ),
                      responseFields(
                              fieldWithPath("result_code").type(JsonFieldType.NUMBER).description("Result Code (200 OK)")
                              , fieldWithPath("result_msg").type(JsonFieldType.STRING).description("Result Message")
                              , fieldWithPath("data.userid").type(JsonFieldType.STRING).description("사용자 아이디")
                              , fieldWithPath("data.user_name").type(JsonFieldType.STRING).description("사용자명")
                      )
				));

	}
	// @formatter:on

	@Test
	@Order(3)
	@Ignore
	// @formatter:off
	public void testDoPut() throws Exception {
		TestRequest req = new TestRequest();
		req.setId("USER_ID");
		req.setName("USER_NAME");

		mockMvc.perform(put("/test/put")
			  .accept(MediaType.APPLICATION_JSON)
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(JsonUtils.getJson(req)))
			  .andDo(print())
			  .andExpect(status().isOk())
			  .andDo(document.document(
					  requestFields(
							  fieldWithPath("id").type(JsonFieldType.STRING).description("아이디 파라미터")
							  , fieldWithPath("name").type(JsonFieldType.STRING).description("이름 파라미터")
					  ),
                      responseFields(
                              fieldWithPath("result_code").type(JsonFieldType.NUMBER).description("Result Code (200 OK)")
                              , fieldWithPath("result_msg").type(JsonFieldType.STRING).description("Result Message")
                              , fieldWithPath("data.userid").type(JsonFieldType.STRING).description("사용자 아이디")
                              , fieldWithPath("data.user_name").type(JsonFieldType.STRING).description("사용자명")
                      )
				));

	}
	// @formatter:on

	@Test
	@Order(4)
	@Ignore
	// @formatter:off
	public void testDoDelete() throws Exception {
		mockMvc.perform(delete("/test/delete/{id}", 1)
			  .accept(MediaType.APPLICATION_JSON)
			  .contentType(MediaType.APPLICATION_JSON))
			  .andDo(print())
			  .andExpect(status().isOk())
			  .andDo(document.document(
                      pathParameters(
                              parameterWithName("id").description("아이디 파라미터")
                      ),
                      responseFields(
                              fieldWithPath("result_code").type(JsonFieldType.NUMBER).description("Result Code (200 OK)")
                              , fieldWithPath("result_msg").type(JsonFieldType.STRING).description("Result Message")
                              , fieldWithPath("data.userid").type(JsonFieldType.STRING).description("사용자 아이디")
                              , fieldWithPath("data.user_name").type(JsonFieldType.STRING).description("사용자명")
                      )
				));

	}
	// @formatter:on

}
