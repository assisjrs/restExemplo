package work.assisjrs.restExemplo.rest.json;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import work.assisjrs.restExemplo.model.entity.Usuario;

@Component
public class UsuarioToUsuarioJsonMapping {
	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	public void start() {
		PropertyMap<Usuario, UsuarioJson> usuarioMap = new PropertyMap<Usuario, UsuarioJson>() {
			protected void configure() {}
		};

		modelMapper.addMappings(usuarioMap);
		
		PropertyMap<UsuarioJson, Usuario> usuarioJsonMap = new PropertyMap<UsuarioJson, Usuario>() {
			protected void configure() {}
		};

		modelMapper.addMappings(usuarioJsonMap);
	}
}
