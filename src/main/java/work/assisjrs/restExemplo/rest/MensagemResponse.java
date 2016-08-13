package work.assisjrs.restExemplo.rest;

public class MensagemResponse {
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
