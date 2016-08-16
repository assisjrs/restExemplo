<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Cliente de teste dos endpoints do RestExemplo</title>

<style>
	.sucesso {
		border-style: solid;
		border-color:#00FF00;
	}
	
	.erro {
		border-style: solid;
		border-color:#FF0000;
	}
	
	table {
		border-color: #000;
		border-style: solid;
	}
	
	section {
		margin-bottom: 10px;
	}
	
	td {
		margin-right: 10px;
		border-right-style: dashed;
	}
</style>

<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>
</head>
<body>
	<section>
	
	<table>
	<tr>
		<td>
		<h1>Cadastro (/cadastro/)</h1>
		
		<form id="cadastroForm">
			<div>
				<label>Nome</label>
				<div>
					<input type="text" id="cadastroName" name="name" required/>
				</div>
			</div>
			
			<div>
				<label>Email</label>
				<div>
					<input id="cadastroEmail" type="email" name="email" required/>
				</div>
			</div>
			
			<div>
				<label>Senha</label>
				<div>
					<input type="password" id="cadastroPassword" name="password" required/>
				</div>
			</div>

			<br/>

			<div>
				<div>
					<input type="submit" id="cadastroEnviarBtn"/>
				</div>
			</div>
			
			<br/>
		</form>
		</td>
		
		<td>
		<h1>Login (/login/)</h1>
		
		<form id="loginForm">
			<div>
				<label>Email</label>
				<div>
					<input id="loginEmail" type="email" name="email" required/>
				</div>
			</div>
			
			<div>
				<label>Senha</label>
				<div>
					<input type="password" id="loginPassword" name="password" required/>
				</div>
			</div>

			<br/>

			<div>
				<div>
					<input type="submit" id="loginEnviarBtn"/>
				</div>
			</div>
			
			<br/>
		</form>
		</td>
		<td>
		
		<h1>Perfil (/perfil/{id})</h1>
		
		<form id="perfilForm">
			<div>
				<label>Id</label>
				<div>
					<input type="text" id="perfilId" name="id" required />
				</div>
			</div>
			
			<br/>

			<div>
				<div>
					<input type="submit" id="perfilEnviarBtn"/>
				</div>
			</div>
			
			<br/>
		</form>
		</td>
		<td>
			<h4> Dados Atuais </h4>
			<div class="dadosAtuais">
				<p>id: <span id="dadosAtuaisId"></span></p>
				<p>Email: <span id="dadosAtuaisEmail"></span></p>
				<p>Token: <span id="dadosAtuaisToken"></span></p>
				<p>Token Anterior: <span id="dadosAtuaisTokenAnterior"></span></p>
				<p>Ultima Operação: <span id="dadosAtuaisUltimaOperacao">GET /</span></p>
			</div>
		</td>
	</tr>
	</table>		
	</section>
	
	<section>
		<div id="feedbackContainer">
			<h4>Resposta do Ajax <span id="dataRequisicao"></span></h4>
		
			<div><pre id="feedback"></pre></div>
			<div><pre id="trace"></pre></div>
		</div>	
	</section>
	
	<footer>
		<p>
			<a href="http://github.com/assisjrs/restExemplo">http://github.com/assisjrs/restExemplo/</a>
		</p>
	</footer>
<script>
	jQuery.support.cors = true;

	jQuery(document).ready(function($) {
		$("#cadastroForm").submit(function(event) {
			$("#cadastroEnviarBtn").prop("disabled", false);

			event.preventDefault();

			realizarCadastro();
		});
		
		$("#loginForm").submit(function(event) {
			$("#loginEnviarBtn").prop("disabled", false);

			event.preventDefault();

			realizarLogin();
		});
		
		$("#perfilForm").submit(function(event) {
			$("#perfilEnviarBtn").prop("disabled", false);

			event.preventDefault();

			realizarPerfil();
		});
	});

	function realizarCadastro() {
		var json = {};
		json["name"] = $("#cadastroName").val();
		json["email"] = $("#cadastroEmail").val();
		json["password"] = $("#cadastroPassword").val();
		
		$('#dadosAtuaisTokenAnterior').html($("#dadosAtuaisToken").html());
		$('#dadosAtuaisUltimaOperacao').html('/cadastro/');

		$.ajax({
			type : "POST",
			contentType : "application/json;charset=UTF-8",
			url : "/cadastro/",
			data : JSON.stringify(json),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				
				display(data, true);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				
				display(e, false);
			},
			done : function(e) {
				console.log("DONE");
				
				$("#cadastroEnviarBtn").prop("disabled", true);
			}
		});
	}
	
	function realizarLogin() {
		var json = {};
		json["email"] = $("#loginEmail").val();
		json["password"] = $("#loginPassword").val();
		
		$('#dadosAtuaisTokenAnterior').html($("#dadosAtuaisToken").html());
		$('#dadosAtuaisUltimaOperacao').html('/login/');

		$.ajax({
			type : "POST",
			contentType : "application/json;charset=UTF-8",
			url : "/login/",
			data : JSON.stringify(json),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				
				display(data, true);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				
				display(e, false);
			},
			done : function(e) {
				console.log("DONE");
				
				$("#loginEnviarBtn").prop("disabled", true);
			}
		});
	}
	
	function realizarPerfil() {
		$('#dadosAtuaisTokenAnterior').html($("#dadosAtuaisToken").html());
		$('#dadosAtuaisUltimaOperacao').html('/perfil/' + json["id"]);
		
		$.ajax({
			type : "GET",
			contentType : "application/json;charset=UTF-8",
			url : "/perfil/" + $("#perfilId").val(),
			timeout : 100000,
			//TODO: Inserir o token no cabeçalho http
			success : function(data) {
				console.log("SUCCESS: ", data);
				
				display(data, true);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				
				display(e, false);
			},
			done : function(e) {
				console.log("DONE");
				
				$("#perfilEnviarBtn").prop("disabled", true);
			}
		});
	}

	function display(data, sucesso) {
		$("#feedbackContainer").removeClass("erro");
		$("#feedbackContainer").removeClass("sucesso");
		
		var feedback = "";
		var trace = "";
		
		if(sucesso){
			feedback = JSON.stringify(data, null, 4);
			
			$("#feedbackContainer").addClass("sucesso");
			
			$('#dadosAtuaisId').html(data.id);
			$('#dadosAtuaisEmail').html(data.email);
			$('#dadosAtuaisToken').html(data.token);
		} else {
			feedback = JSON.stringify(data.responseJSON, null, 4);
			
			$("#feedbackContainer").addClass("erro");
						
			trace = JSON.stringify(data, null, 4);
		}
		
		$('#trace').html(trace);
		$('#feedback').html(feedback);
		
		$('#dataRequisicao').html(new Date().toString());
	}
</script>

</body>
</html>
