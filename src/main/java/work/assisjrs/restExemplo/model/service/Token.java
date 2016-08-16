package work.assisjrs.restExemplo.model.service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;
import work.assisjrs.restExemplo.model.Usuarios;
import work.assisjrs.restExemplo.model.entity.Usuario;

@Component
public class Token {
	static String SECRET_KEY = "restExemploSecretKey";

	@Autowired
	private Usuarios usuarios;
	
	public String tokenizer(Usuario usuario, String subject) {
		try {
			return Jwts.builder()
					   .setId(usuario.getId().toString())
					   .setSubject(subject)
					   .claim("email", usuario.getEmail())
					   .setIssuedAt(new Date())
					   .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes("UTF-8"))
					   .compact();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public DefaultClaims getBody(String token){
		try {
			return (DefaultClaims) Jwts.parser()
					 				   .setSigningKey(Token.SECRET_KEY.getBytes("UTF-8"))
					 				   .parse(token)
					 				   .getBody();
		} catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException
				| UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public Usuario getSigned(Long id, String token) throws TokenInvalidoException, LoginMenorQue30MinutosException {
		Usuario usuario = usuarios.byId(id);
		
		if(!usuario.getToken().equals(token))
			throw new TokenInvalidoException(id);
		
		if(seLogouEmMenosDe30Min(usuario))
			throw new LoginMenorQue30MinutosException(usuario.getEmail());
		
		return usuario;
	}

	private boolean seLogouEmMenosDe30Min(Usuario usuario) {
		return usuario.getLastLogin().toInstant().plus(30, ChronoUnit.MINUTES).isAfter(Instant.now());
	}
}
