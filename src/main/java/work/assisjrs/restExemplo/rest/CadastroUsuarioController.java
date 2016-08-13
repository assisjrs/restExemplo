package work.assisjrs.restExemplo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import work.assisjrs.restExemplo.model.Usuarios;

@RestController
public class CadastroUsuarioController {
	@Autowired
	private Usuarios usuarios;
	
	@RequestMapping(value = "/cadastro", produces = "application/json;charset=UTF-8",
					method = { RequestMethod.POST })
	public ResponseEntity<?> salvar(@RequestBody UsuarioJson usuario) {
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
}
