package work.assisjrs.restExemplo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import work.assisjrs.restExemplo.config.HibernateConfig;
import work.assisjrs.restExemplo.config.WebConfig; 


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class, HibernateConfig.class })
public class CadastroUsuarioControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
								 .build();
	}

	@Test
	public void oEndpointDeveResponderJson() throws Exception {		
		mockMvc.perform(post("/cadastro"))
	     	   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void oCadastroDeveUsarPost() throws Exception {		
		mockMvc.perform(post("/cadastro"))
  	   		   .andExpect(status().isOk());
	}
	
	@Test
	public void casoOCadastroUseUmMetodoDiferenteDePostLancar405() throws Exception {		
		mockMvc.perform(get("/cadastro"))
	     	   .andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void salvarTest() throws Exception {		
		mockMvc.perform(post("/cadastro"))
	     	   .andExpect(status().isOk())
	           .andExpect(content().json("{mensagem: null}"));
	}
}
