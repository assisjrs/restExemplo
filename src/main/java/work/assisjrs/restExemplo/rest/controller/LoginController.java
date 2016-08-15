package work.assisjrs.restExemplo.rest.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import work.assisjrs.restExemplo.model.entity.Usuario;
import work.assisjrs.restExemplo.model.service.Authentication;
import work.assisjrs.restExemplo.model.service.UsuarioESenhaInvalidosException;
import work.assisjrs.restExemplo.model.service.UsuarioInexistenteException;
import work.assisjrs.restExemplo.rest.json.MensagemJson;
import work.assisjrs.restExemplo.rest.json.UsuarioJson;

@RestController
public class LoginController {
	@Autowired
	private Authentication authentication;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/login/", produces = "application/json;charset=UTF-8",
					method = { RequestMethod.POST }, consumes = "application/json;charset=UTF-8")
	public ResponseEntity<?> login(@RequestBody UsuarioJson usuario) {

		Usuario model = new Usuario();
		try {
			model = authentication.authenticate(usuario.getEmail(), usuario.getPassword());
		} catch (UsuarioInexistenteException e) {
			return new ResponseEntity<>(new MensagemJson("Usuário e/ou senha inválidos"), HttpStatus.UNAUTHORIZED);
		} catch (UsuarioESenhaInvalidosException e) {
			return new ResponseEntity<>(new MensagemJson("Usuário e/ou senha inválidos"), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MensagemJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(modelMapper.map(model, UsuarioJson.class), HttpStatus.OK);
	}
}
