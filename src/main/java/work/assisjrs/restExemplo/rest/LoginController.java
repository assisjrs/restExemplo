package work.assisjrs.restExemplo.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import work.assisjrs.restExemplo.model.Usuario;
import work.assisjrs.restExemplo.model.UsuarioESenhaInvalidosException;
import work.assisjrs.restExemplo.model.UsuarioInexistenteException;
import work.assisjrs.restExemplo.model.Usuarios;

@RestController
public class LoginController {
	@Autowired
	private Usuarios usuarios;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/login/", produces = "application/json;charset=UTF-8",
					method = { RequestMethod.POST }, consumes = "application/json;charset=UTF-8")
	public ResponseEntity<?> login(@RequestBody UsuarioJson usuario) {

		Usuario model = new Usuario();
		try {
			model = usuarios.usuarioPorEmailESenha(usuario.getEmail(), usuario.getPassword());
		} catch (UsuarioInexistenteException e) {
			return new ResponseEntity<>(new MensagemResponse("Usu�rio e/ou senha inv�lidos"), HttpStatus.UNAUTHORIZED);
		} catch (UsuarioESenhaInvalidosException e) {
			return new ResponseEntity<>(new MensagemResponse("Usu�rio e/ou senha inv�lidos"), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MensagemResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(modelMapper.map(model, UsuarioJson.class), HttpStatus.OK);
	}
}
