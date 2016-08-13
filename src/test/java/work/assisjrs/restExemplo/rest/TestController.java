package work.assisjrs.restExemplo.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import work.assisjrs.restExemplo.config.HibernateConfig;
import work.assisjrs.restExemplo.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class, HibernateConfig.class })
public class TestController {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		// this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_welcome() throws Exception {
		/*
		 * MvcResult result = this.mockMvc.perform(get("/hello/mkyong"))
		 * .andExpect(status().isOk()) .andReturn();
		 */
	}
}
