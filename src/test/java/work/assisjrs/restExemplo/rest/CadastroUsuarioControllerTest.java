package work.assisjrs.restExemplo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import work.assisjrs.restExemplo.DBUnitConfig;
import work.assisjrs.restExemplo.config.WebConfig; 


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { DBUnitConfig.class, WebConfig.class })
@Transactional
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
								  DbUnitTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@DatabaseSetup("/Datasets/UsuariosTest.xml")
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
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).content(json.toString()))
	     	   .andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void casoOCadastroUseUmMetodoDiferenteDePostLancar405() throws Exception {		
		mockMvc.perform(get("/cadastro/"))
	     	   .andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void emCasoDeSucessoReponderComStatus200() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"status200@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).content(json.toString()))
	     	   .andExpect(status().isOk());
	}
	
	@Test
	public void aComunicacaoDeveUsarUTF8() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"utf8@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(content().encoding("UTF-8"));
	}
	
	@Test
	public void aoCadastrarRetornarOUsuario() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"usuario@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(content().json(json.toString()));
	}
	
	@Test
	public void aoCadastrarRetornarOUsuarioComId() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"id@gmail.com\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(jsonPath("$.id").value(IsNot.not(0)));
	}
	
	@Test
	public void aoCadastrarRetornarOUsuarioComCreated() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"id@gmail.com\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(jsonPath("$.created").exists());
	}
	
	@Test
	public void aoCadastrarRetornarOUsuarioComModified() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"modified@gmail.com\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(jsonPath("$.modified").exists());
	}
	
	@Test
	public void aoCadastrarRetornarOUsuarioComLastLogin() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"lastLogin@silva.org\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(jsonPath("$.last_login").exists());
	}
	
	@Test
	public void casoOEmailJaExistaRetornarMensagemDeErroEmailJaExistente() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"emailJaCadastrado@gmail.com\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(jsonPath("$.mensagem").value("E-mail já existente"));
	}
	
	@Test
	public void casoOEmailJaExistaRetornarStatusDeConflito() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"name\": \"João da Silva\",");
		json.append("    \"email\": \"emailJaCadastrado@gmail.com\",");
		json.append("    \"password\": \"hunter2\",");
		json.append("    \"phones\": [");
		json.append("        {");
		json.append("            \"number\": \"987654321\",");
		json.append("            \"ddd\": \"21\"");
		json.append("        }");
		json.append("    ]");
		json.append("}");
		
		mockMvc.perform(post("/cadastro/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
			   .andExpect(status().isConflict());
	}
}
