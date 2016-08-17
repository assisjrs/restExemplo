package work.assisjrs.restExemplo.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import work.assisjrs.restExemplo.model.entity.Usuario;
import work.assisjrs.restExemplo.model.service.LoginMenorQue30MinutosException;
import work.assisjrs.restExemplo.model.service.Token;
import work.assisjrs.restExemplo.model.service.TokenInvalidoException;
import work.assisjrs.restExemplo.rest.json.MensagemJson;
import work.assisjrs.restExemplo.rest.json.UsuarioJson;

@RestController
public class PerfilController {
	@Autowired
	private Token token;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/perfil/{id}", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	public ResponseEntity<?> perfil(@PathVariable("id") Long id, HttpServletResponse response,
			HttpServletRequest request) {
		String headerToken = request.getHeader("Authorization");

		if (headerToken == null || headerToken.isEmpty())
			return new ResponseEntity<>(new MensagemJson("Não autorizado"), HttpStatus.UNAUTHORIZED);

		try {
			Usuario usuarioLogado = token.getSigned(id, headerToken);
			
			response.addHeader("Authorization", headerToken);

			return new ResponseEntity<>(modelMapper.map(usuarioLogado, UsuarioJson.class), HttpStatus.OK);
		} catch (LoginMenorQue30MinutosException | TokenInvalidoException e) {
			return new ResponseEntity<>(new MensagemJson("Não autorizado"), HttpStatus.UNAUTHORIZED);
		}
	}
}
