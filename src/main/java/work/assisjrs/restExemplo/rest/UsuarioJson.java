package work.assisjrs.restExemplo.rest;

import java.util.List;

public class UsuarioJson {
	private String name;
	private String email;
	private String password;
	private List<TelefoneJson> phones;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<TelefoneJson> getPhones() {
		return phones;
	}

	public void setPhones(List<TelefoneJson> phones) {
		this.phones = phones;
	}
}
