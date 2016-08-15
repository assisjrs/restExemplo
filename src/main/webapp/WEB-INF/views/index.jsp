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
</style>

<script src="https://code.jquery.com/jquery-3.1.0.min.js"></script>
</head>
<body>

	<section>
		<h1>Cadastro de usuário</h1>
		
		<form id="cadastroForm">
			<div>
				<label>Nome</label>
				<div>
					<input type="text" id="name" name="name" />
				</div>
			</div>
			
			<div>
				<label>Email</label>
				<div>
					<input type="text" id="email" name="email" />
				</div>
			</div>
			
			<div>
				<label>Senha</label>
				<div>
					<input type="text" id="password" name="password" />
				</div>
			</div>

			<br/>

			<div>
				<div>
					<input type="submit" id="enviarBtn"/>
				</div>
			</div>
			
			<br/>
		</form>
	</section>
	
	<div id="feedbackContainer">
		<h4>Ajax Response</h4>
	
		<div id="feedback"></div>
		<div id="trace"></div>
	</div>	

	<footer>
		<p>
			<a href="http://github.com/assisjrs/restExemplo">http://github.com/assisjrs/restExemplo/</a>
		</p>
	</footer>
<script>
	jQuery(document).ready(function($) {
		$("#cadastroForm").submit(function(event) {
			$("#enviarBtn").prop("disabled", false);

			event.preventDefault();

			realizarCadastro();
		});
	});

	function realizarCadastro() {
		var json = {};
		json["name"] = $("#name").val();
		json["email"] = $("#email").val();
		json["password"] = $("#password").val();

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
				
				$("#enviarBtn").prop("disabled", true);
			}
		});
	}

	function display(data, sucesso) {
		$("#feedbackContainer").removeClass("erro");
		$("#feedbackContainer").removeClass("sucesso");
		
		var feedback = "";
		var trace = "";
		
		if(sucesso){
			feedback = "<pre>" + JSON.stringify(data, null, 4) + "</pre>";
			
			$("#feedbackContainer").addClass("sucesso");
		} else {
			feedback = "<pre>" + JSON.stringify(data.responseJSON, null, 4) + "</pre>";
			
			$("#feedbackContainer").addClass("erro");
						
			trace = "<pre>" + JSON.stringify(data, null, 4) + "</pre>";
		}
		
		$('#trace').html(trace);
		$('#feedback').html(feedback);
	}
</script>

</body>
</html><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Desafio REST</title>
</head>
<body>
Esta aplicacao somente utiliza comunicacao REST
</body>
</html>