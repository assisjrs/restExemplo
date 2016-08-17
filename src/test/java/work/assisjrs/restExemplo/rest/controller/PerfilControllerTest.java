package work.assisjrs.restExemplo.rest.controller;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import work.assisjrs.restExemplo.model.entity.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(classes = { DBUnitConfig.class, WebConfig.class })
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@DatabaseSetup("/Datasets/PerfilControllerTest.xml")
public class PerfilControllerTest {
	private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJURVNURSIsImVtYWlsIjoiZW1haWxKYUNhZGFzdHJhZG9AZ21haWwuY29tIiwiaWF0Ijo2MTQyOTM3NDAwMH0.sp7dNesWHcmNqMdeSja7w7UK9z1m129WPu7rkIm0VMkqAzE_zSPrV-db1fOU7IvNphrBBXV5NuVRCQkK9KjrZQ";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void oEndpointDeveResponderJson() throws Exception {
		mockMvc.perform(get("/perfil/1")).andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Test
	public void casoOCadastroUseUmMetodoDiferenteDeGetLancar405() throws Exception {
		mockMvc.perform(post("/perfil/1")).andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void emCasoDeSucessoReponderComStatus200() throws Exception {
		mockMvc.perform(get("/perfil/1").header("Authorization", TOKEN)).andExpect(status().isOk());
	}

	@Test
	public void aComunicacaoDeveUsarUTF8() throws Exception {
		mockMvc.perform(get("/perfil/1")).andExpect(content().encoding("UTF-8"));
	}

	@Test
	public void aoCadastrarRetornarOUsuarioPeloId() throws Exception {
		mockMvc.perform(get("/perfil/1").header("Authorization", TOKEN)).andExpect(jsonPath("$.id").value(1));
	}

	@Test
	public void retornarOTokenNoJson() throws Exception {
		mockMvc.perform(get("/perfil/1").header("Authorization", TOKEN)).andExpect(jsonPath("$.token").value(TOKEN));
	}

	@Test
	public void retornarOTokenNoHeader() throws Exception {
		mockMvc.perform(get("/perfil/1").header("Authorization", TOKEN))
				.andExpect(header().string("Authorization", notNullValue()));
	}

	@Test
	public void casoOTokenNaoExistaRetornarErroComAMensagemNaoAutorizado() throws Exception {
		mockMvc.perform(get("/perfil/1")).andExpect(jsonPath("$.mensagem").value("Não autorizado"));
	}

	@Test
	public void casoOTokenNaoExistaRetornarErroComStatus403() throws Exception {
		mockMvc.perform(get("/perfil/1")).andExpect(status().isUnauthorized());
	}

	@Test
	public void deveBuscarOUsuarioPeloIdECompararSeOTokenNoModeloEIgualAoTokenPassadoCasoNaoSejaretornarErroComStatus403()
			throws Exception {
		mockMvc.perform(get("/perfil/1").header("Authorization", "NaoEUmToken")).andExpect(status().isUnauthorized());
	}

	@Test
	public void deveBuscarOUsuarioPeloIdECompararSeOTokenNoModeloEIgualAoTokenPassadoCasoNaoSejaretornarErroComMensagemNaoAutorizado()
			throws Exception {
		mockMvc.perform(get("/perfil/1").header("Authorization", "NaoEUmToken"))
				.andExpect(jsonPath("$.mensagem").value("Não autorizado"));
	}
	
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void CasoOUltimoLoginFoiMenosQue30minRetornarErroComStatus403() throws Exception {
		Usuario usuarioAntesDe30Minutos = entityManager.find(Usuario.class, 2L);
		usuarioAntesDe30Minutos.setLastLogin(new Date());
		
		mockMvc.perform(get("/perfil/2").header("Authorization", "zzzzz"))
			   .andExpect(status().isUnauthorized());
	}

	@Test
	public void CasoOUltimoLoginFoiMenosQue30minRetornarErroComComMensagemSessaoInvalida() throws Exception {
		Usuario usuarioAntesDe30Minutos = entityManager.find(Usuario.class, 2L);
		usuarioAntesDe30Minutos.setLastLogin(new Date());
		
		mockMvc.perform(get("/perfil/2").header("Authorization", "zzzzz"))
				.andExpect(jsonPath("$.mensagem").value("Não autorizado"));
	}
}
