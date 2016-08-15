package work.assisjrs.restExemplo.rest;

public class MensagemJson {
	private String mensagem;

	public MensagemJson(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
