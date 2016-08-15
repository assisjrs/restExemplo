package work.assisjrs.restExemplo.model.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import work.assisjrs.restExemplo.model.entity.Usuario;

@Component
public class Token {
	private static String SECRET_KEY = "restExemploSecretKey";

	public String tokenizer(Usuario usuario, String subject) {
		try {
			return Jwts.builder()
					   .setId(usuario.getId().toString())
					   .setSubject(subject)
					   .claim("email", usuario.getEmail())
					   .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes("UTF-8"))
					   .compact();
		} catch (UnsupportedEncodingException e) {
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
