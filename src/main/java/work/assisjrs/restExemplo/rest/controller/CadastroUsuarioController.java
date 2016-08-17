package work.assisjrs.restExemplo.rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import work.assisjrs.restExemplo.model.entity.Usuario;
import work.assisjrs.restExemplo.model.service.EmailJaCadastradoException;
import work.assisjrs.restExemplo.model.service.Registration;
import work.assisjrs.restExemplo.rest.json.MensagemJson;
import work.assisjrs.restExemplo.rest.json.UsuarioJson;

@RestController
public class CadastroUsuarioController {
	@Autowired
	private Registration registration;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/cadastro/", produces = "application/json;charset=UTF-8",
					method = { RequestMethod.POST }, consumes = "application/json;charset=UTF-8")
	public ResponseEntity<?> salvar(@RequestBody UsuarioJson usuario, HttpServletResponse response) {

		Usuario model = modelMapper.map(usuario, Usuario.class);

		try {
			Usuario usuarioCadastrado = registration.register(model);

			response.addHeader("Authorization", usuarioCadastrado.getToken());
			
			return new ResponseEntity<>(modelMapper.map(usuarioCadastrado, UsuarioJson.class), HttpStatus.OK);
		} catch (EmailJaCadastradoException e) {
			return new ResponseEntity<>(new MensagemJson("E-mail já existente"), HttpStatus.CONFLICT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MensagemJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
