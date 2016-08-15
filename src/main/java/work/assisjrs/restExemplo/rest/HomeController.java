package work.assisjrs.restExemplo.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/cadastro.html", method = RequestMethod.GET)
	public String cadastro() {
		return "cadastro";
	}
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/perfil.html", method = RequestMethod.GET)
	public String perfil() {
		return "perfil";
	}
}
