package work.assisjrs.restExemplo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CadastroUsuarioController {
	@RequestMapping(value = "/cadastro", produces = { MediaType.APPLICATION_JSON_VALUE },
					method = { RequestMethod.POST })
	public ResponseEntity<?> salvar() {
		return new ResponseEntity<>(new AjaxResponse(), HttpStatus.OK);
	}
}
