package work.assisjrs.restExemplo.rest.controller;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@Transactional
@ContextConfiguration(classes = { DBUnitConfig.class, WebConfig.class })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@DatabaseSetup("/Datasets/LoginControllerTest.xml")
public class LoginControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void oEndpointDeveResponderJson() throws Exception {
		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\"");
		json.append("}");

		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).content(json.toString()))
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Test
	public void casoUseUmMetodoDiferenteDePostLancar405() throws Exception {
		mockMvc.perform(get("/login/")).andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void emCasoDeSucessoReponderComStatus200() throws Exception {
		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\"");
		json.append("}");

		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).content(json.toString()))
				.andExpect(status().isOk());
	}

	@Test
	public void aComunicacaoDeveUsarUTF8() throws Exception {
		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\"");
		json.append("}");

		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
				.content(json.toString())).andExpect(content().encoding("UTF-8"));
	}

	@Test
	public void casoSucessoRetornarIgualAoEndpointDeCriacao() throws Exception {
		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\"");
		json.append("}");

		StringBuilder jsonResposta = new StringBuilder();

		jsonResposta.append("{");
		jsonResposta.append("    \"name\": \"João da Silva\",");
		jsonResposta.append("    \"email\": \"joao@silva.org\",");
		jsonResposta.append("    \"password\": \"2ab96390c7dbe3439de74d0c9b0b1767\",");
		jsonResposta.append("    \"phones\": [");

		jsonResposta.append("        {");
		jsonResposta.append("            \"number\": \"987654321\",");
		jsonResposta.append("            \"ddd\": \"21\"");
		jsonResposta.append("        }");
		jsonResposta.append("    ],");

		jsonResposta.append("    \"id\": 666");
		jsonResposta.append("}");

		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
			   .andExpect(content().json(jsonResposta.toString()));
	}

	@Test
	public void casoOEmailNaoExistaRetornarMensagemUsuarioEOuSenhaInvalidos() throws Exception {
		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("    \"email\": \"usuarioNaoExiste@silva.org\",");
		json.append("    \"password\": \"hunter2\"");
		json.append("}");

		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
			   .andExpect(jsonPath("$.mensagem").value("Usuário e/ou senha inválidos"));
	}
	
	@Test
	public void casoOEmailNaoExistaRetornarStatus401() throws Exception {
		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("    \"email\": \"usuarioNaoExiste@silva.org\",");
		json.append("    \"password\": \"hunter2\"");
		json.append("}");

		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void casoOEmailExistaMasASenhaNaoBataRetornarUsuarioEOuSenhaInvalidos() throws Exception {
		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"senhaInvalida\"");
		json.append("}");

		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
			   .andExpect(jsonPath("$.mensagem").value("Usuário e/ou senha inválidos"));
	}
	
	@Test
	public void retornarOTokenNoHeader() throws Exception {
		StringBuilder json = new StringBuilder();
		
		json.append("{");
		json.append("    \"email\": \"joao@silva.org\",");
		json.append("    \"password\": \"hunter2\"");
		json.append("}");
		
		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
	           .andExpect(header().string("Authorization", notNullValue()));
	}
	
	@Test
	public void casoOEmailExistaMasASenhaNaoBataRetornarStatus401() throws Exception {
		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("    \"email\": \"usuarioNaoExiste@silva.org\",");
		json.append("    \"password\": \"senhaInvalida\"");
		json.append("}");

		mockMvc.perform(post("/login/").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8").content(json.toString()))
				.andExpect(status().isUnauthorized());
	}
}
