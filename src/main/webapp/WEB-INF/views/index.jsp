<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Cadastro Usuário</title>

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
		<h1>Cadastro</h1>
		
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
		<h1>Login</h1>
		
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
		
		<h1>Perfil</h1>
		
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
	</tr>
	</table>		
	</section>
	
	<section>
		<div id="feedbackContainer">
			<h4>Resposta Ajax <span id="dataRequisicao"></span></h4>
		
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
		var json = {};
		json["id"] = $("#perfilId").val();
		//json["token"] = $("#loginPassword").val();

		$.ajax({
			type : "POST",
			contentType : "application/json;charset=UTF-8",
			url : "/perfil/",
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
