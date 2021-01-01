package com.cupriver.nutrition;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Application smoke testing unit tests.
 * @author Chandra Prakash (www.cupriver.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("user")
public class NutritionApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$._links.userprofile").exists())
				.andExpect(jsonPath("$._links.foodLogs").exists());
	}
	
	@Test
	public void shouldCreateEntity() throws Exception {
		mockMvc.perform(post("/foodlog").content(
				"{\n" + 
				"  \"userName\": \"user\",\n" + 
				"  \"date\": \"2004-02-04\",\n" + 
				"  \"time\": \"02:30\",\n" + 
				"  \"text\": \"1 cup rice with milk\",\n" + 
				"   \"numberOfCalories\": 180\n" + 
				"}").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print()).andExpect(
						status().isCreated());
	}
}
