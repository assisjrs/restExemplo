package work.assisjrs.restExemplo.model;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

@Component
public class Hash {
	public String encode(String s) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		m.reset();
		m.update(s.getBytes(Charset.forName("UTF8")));

		return new String(Hex.encodeHex(m.digest()));
	}
}
