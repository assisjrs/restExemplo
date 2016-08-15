package work.assisjrs.restExemplo;

import org.hibernate.StatelessSession;

public class InserirDadosBean {
	public StatelessSession statelessSession;

	public void inserirUmUsuarioJaExistenteNoBanco() {
		/*
		BigInteger count = (BigInteger) statelessSession.createSQLQuery("select count(*) from Usuario where email = 'emailJaCadastrado@gmail.com'")
													    .uniqueResult();
		
		if(count.intValue() != 0) return;
		
		int sequence = (int) statelessSession.createSQLQuery("call next value for hibernate_sequence")
											 .uniqueResult();
		
		statelessSession.createSQLQuery("insert into Usuario (created, email, id) values (?, ?, ?)")
						.setParameter(0, new Date())
						.setParameter(1, "emailJaCadastrado@gmail.com")
						.setParameter(2, sequence)
						.executeUpdate();
						*/
	}
}
