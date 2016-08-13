package work.assisjrs.restExemplo.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {
	@RequestMapping(value = "/ajax")
	public AjaxResponse index() {
		AjaxResponse result = new AjaxResponse();

		result.setMensagem("OK!");
		
		return result;
	}
	
	@RequestMapping(value = "/error")
	public AjaxResponse error(HttpServletResponse response) throws Exception{
		response.setStatus(403);
		
		AjaxResponse result = new AjaxResponse();

		result.setMensagem("mensagem de erro: 403");

		return result;
	}
}
