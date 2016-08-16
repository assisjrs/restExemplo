package work.assisjrs.restExemplo.model.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;
import work.assisjrs.restExemplo.model.entity.Usuario;

@Component
public class Token {
	static String SECRET_KEY = "restExemploSecretKey";

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

	public boolean isValid(String token) {
		try {
			return Jwts.parser()
					   .setSigningKey(SECRET_KEY.getBytes("UTF-8"))
					   .isSigned(token);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
