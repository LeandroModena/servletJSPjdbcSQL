<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de usuário</title>

<link rel="stylesheet" href="resources/css/estilo2.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>

</head>
<body>
	<a href="acessoliberado.jsp">Inicio</a>
	<a href="index.jsp">Sair</a>

	<h1 align="center">Cadastro de usuário</h1>
	<h3 align="center" style="color: red;">${msg}</h3>

	<form action="saveUser" method="post" id="formUser"
		onsubmit="return validateForm() ? true : false"
		enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Id:</td>
						<td><input readonly="readonly" type="text" id="id" name="id"
							value="${user.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login"
							value="${user.login}" class="field-long" placeholder="Login"></td>
					</tr>
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="password" name="password"
							value="${user.password}" class="field-long" placeholder="Senha"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
							value="${user.nome}" class="field-long" placeholder="Nome"></td>
					</tr>
					<tr>
						<td>Fone:</td>
						<td><input type="text" id="telefone" name="telefone"
							value="${user.telefone}" class="field-long"
							placeholder="Telefone"></td>
					</tr>
					<tr>
						<td>Cep:</td>
						<td><input type="text" id="cep" name="cep"
							value="${user.cep}" class="field-long"
							placeholder="Informe seu Cep"></td>
					</tr>
					<tr>
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua"
							value="${user.rua}" class="field-long"></td>
					</tr>
					<tr>
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro"
							value="${user.bairro}" class="field-long"></td>
					</tr>
					<tr>
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade"
							value="${user.cidade}" class="field-long"></td>
					</tr>
					<tr>
						<td>Estado:</td>
						<td><input type="text" id="uf" name="uf" value="${user.uf}"
							class="field-long"></td>
					</tr>
					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge"
							value="${user.ibge}" class="field-long"></td>
					</tr>
					<tr>
						<td>Foto:</td>
						<td><input type="file" name="foto"></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input
							type="submit" value="Cancelar"
							onclick="document.getElementById('formUser').action='saveUser?action=reset'"></td>
					</tr>



				</table>
			</li>
		</ul>
	</form>
	<div class="container">
		<table class="responsive-table" border="1">
			<caption>Usuários Cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Login</th>
				<th>Foto</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>Cep</th>
				<th>Excluir</th>
				<th>Editar</th>
			</tr>
			<tr>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><c:out value="${user.id}">
							</c:out></td>
						<td><c:out value="${user.login}">
						</c:out></td>
						<td><a href="saveUser?action=download&users=${user.id}"><img src='<c:out value="${user.tempFotoUser}"></c:out>' width="32px" height="32px"></</a></td>
						<td><c:out value="${user.nome}">
							</c:out></td>
						<td><c:out value="${user.telefone}">
							</c:out></td>
						<td><c:out value="${user.cep}">
							</c:out></td>
						<td align="center"><a
							href="saveUser?action=delete&users=${user.id}"><img
								alt="Excluir" title="Excluir" src="resources/img/excluir.png"
								width="20px" height="20px"></a></td>
						<td align="center" class="row100 body"><a
							href="saveUser?action=edit&users=${user.id}"><img
								alt="Editar" title="Editar" src="resources/img/editar.png"
								width="20px" height="20px"></a></td>
						<td align="center" class="row100 body"><a
							href="saveTelefone?users=${user.id}"><img alt="Editar"
								title="telefone" src="resources/img/telefone.png" width="20px"
								height="20px"></a></td>
					</tr>
				</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validateForm() {
			if (document.getElementById("login").value == '') {
				alert("Informe seu login")
				return false;
			} else if (document.getElementById("password").value == '') {
				alert("Informe sua Senha")
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert("Informe seu Nome")
				return false;
			}

			return true;
		}

		$(document)
				.ready(
						function() {

							function limpa_formulário_cep() {
								// Limpa valores do formulário de cep.
								$("#rua").val("");
								$("#bairro").val("");
								$("#cidade").val("");
								$("#uf").val("");
								$("#ibge").val("");
							}

							//Quando o campo cep perde o foco.
							$("#cep")
									.blur(
											function() {

												//Nova variável "cep" somente com dígitos.
												var cep = $(this).val()
														.replace(/\D/g, '');

												//Verifica se campo cep possui valor informado.
												if (cep != "") {

													//Expressão regular para validar o CEP.
													var validacep = /^[0-9]{8}$/;

													//Valida o formato do CEP.
													if (validacep.test(cep)) {

														//Preenche os campos com "..." enquanto consulta webservice.
														$("#rua").val("...");
														$("#bairro").val("...");
														$("#cidade").val("...");
														$("#uf").val("...");
														$("#ibge").val("...");

														//Consulta o webservice viacep.com.br/
														$
																.getJSON(
																		"https://viacep.com.br/ws/"
																				+ cep
																				+ "/json/?callback=?",
																		function(
																				dados) {

																			if (!("erro" in dados)) {
																				//Atualiza os campos com os valores da consulta.
																				$(
																						"#rua")
																						.val(
																								dados.logradouro);
																				$(
																						"#bairro")
																						.val(
																								dados.bairro);
																				$(
																						"#cidade")
																						.val(
																								dados.localidade);
																				$(
																						"#uf")
																						.val(
																								dados.uf);
																				$(
																						"#ibge")
																						.val(
																								dados.ibge);
																			} //end if.
																			else {
																				//CEP pesquisado não foi encontrado.
																				limpa_formulário_cep();
																				alert("CEP não encontrado.");
																			}
																		});
													} //end if.
													else {
														//cep é inválido.
														limpa_formulário_cep();
														alert("Formato de CEP inválido.");
													}
												} //end if.
												else {
													//cep sem valor, limpa formulário.
													limpa_formulário_cep();
												}
											});
						});
	</script>
</body>

</html>