package work.assisjrs.restExemplo.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MensagemResponse {
	@JsonProperty("mensagem")
	private String mensagem;

	public MensagemResponse(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
