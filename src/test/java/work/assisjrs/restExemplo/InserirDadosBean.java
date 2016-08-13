package work.assisjrs.restExemplo;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InserirDadosBean {
	@Autowired
	private StatelessSession statelessSession;

	@PostConstruct
	public void inserirUmUsuarioJaExistenteNoBanco() {
		int sequence = (int) statelessSession.createSQLQuery("call next value for hibernate_sequence")
											 .uniqueResult();
		
		statelessSession.createSQLQuery("insert into Usuario (created, email, id) values (?, ?, ?)")
						.setParameter(0, new Date())
						.setParameter(1, "emailJaCadastrado@gmail.com")
						.setParameter(2, sequence)
						.executeUpdate();
	}
}
