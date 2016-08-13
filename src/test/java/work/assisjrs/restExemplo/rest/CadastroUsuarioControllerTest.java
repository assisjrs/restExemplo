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
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON_VALUE).content(json.toString()))
	     	   .andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void oCadastroDeveUsarPost() throws Exception {		
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON_VALUE).content(json.toString()))
  	   		   .andExpect(status().isOk());
	}
	
	@Test
	public void casoOCadastroUseUmMetodoDiferenteDePostLancar405() throws Exception {		
		mockMvc.perform(get("/cadastro"))
	     	   .andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void emCasoDeSucessoReponderComStatus200() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON_VALUE).content(json.toString()))
	     	   .andExpect(status().isOk());
	}
	
	@Test
	public void aComunicacaoDeveUsarUTF8() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(content().encoding("UTF-8"));
	}
	
	@Test
	public void aoCadastrarRetornarOUsuario() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(content().json(json.toString()));
	}
}
