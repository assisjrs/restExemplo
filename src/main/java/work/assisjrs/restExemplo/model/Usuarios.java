package work.assisjrs.restExemplo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional 
@Repository 
public class Usuarios {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public Usuario salvar(Usuario usuario){
		hibernateTemplate.persist(usuario);
		
		hibernateTemplate.flush();
		
		return usuario;
	}
	
}
