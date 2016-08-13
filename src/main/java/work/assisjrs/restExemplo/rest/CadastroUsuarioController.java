package work.assisjrs.restExemplo.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import work.assisjrs.restExemplo.model.EmailJaCadastradoException;
import work.assisjrs.restExemplo.model.Usuario;
import work.assisjrs.restExemplo.model.Usuarios;

@RestController
public class CadastroUsuarioController {
	@Autowired
	private Usuarios usuarios;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/cadastro/", produces = "application/json;charset=UTF-8",
					method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<?> salvar(@RequestBody UsuarioJson usuario) {

		Usuario model = modelMapper.map(usuario, Usuario.class);

		try {
			usuarios.salvar(model);
		} catch (EmailJaCadastradoException e) {
			return new ResponseEntity<>(new MensagemResponse("E-mail já existente"), HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(modelMapper.map(model, UsuarioJson.class), HttpStatus.OK);
	}
}
