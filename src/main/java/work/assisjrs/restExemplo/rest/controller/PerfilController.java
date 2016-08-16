package work.assisjrs.restExemplo.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import work.assisjrs.restExemplo.rest.json.MensagemJson;

@RestController
public class PerfilController {
	@RequestMapping(value = "/perfil/{id}", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	public ResponseEntity<?> perfil(@PathVariable("id") Long id, HttpServletResponse response,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty())
			return new ResponseEntity<>(new MensagemJson("Não autorizado"), HttpStatus.UNAUTHORIZED);

		response.addHeader("Authorization", token);
		
		return new ResponseEntity<>(new MensagemJson("OK"), HttpStatus.OK);
	}
}
