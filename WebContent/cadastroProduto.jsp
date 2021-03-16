<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro Produto</title>

<link rel="stylesheet" href="resources/css/estilo2.css">
</head>
<body>
<a href="acessoliberado.jsp">Inicio</a> 
<a href="index.jsp">Sair</a>
	<h1 align="center">Cadastro de Produto</h1>
	<h3 align="center" style="color: red;">${msg}</h3>
	<form action="cadastroProduto" method="post" id="formUser" onsubmit="return validateForm() ? true : false">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Id:</td>
						<td><input readonly="readonly" type="text" id="id" name="id"
							value="${product.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>Código:</td>
						<td><input type="text" id="codigo" name="codigo"
							value="${product.code}" class="field-long"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
							value="${product.name}" class="field-long"></td>
					</tr>
					<tr>
						<td>Qtd:</td>
						<td><input type="number" min="0" step="0.5" value="0"
							id="qtd" name="qtd" value="${product.quantity}"
							class="field-long"></td>
					</tr>
					<tr>
						<td>Preço R$:</td>
						<td><input type="number" min="0" step="0.5" value="0"
							id="preco" name="preco" value="${product.price}"
							class="field-long"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input
							type="submit" value="Cancelar"
							onclick="document.getElementById('formUser').action='cadastroProduto?action=reset'"></td>
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
				<th>Código</th>
				<th>Nome</th>
				<th>QTD</th>
				<th>Preço</th>
				<th>Excluir</th>
				<th>Editar</th>
			</tr>
			<tr>
				<c:forEach items="${products}" var="product">
					<tr>
						<td><c:out value="${product.id}">
							</c:out></td>
						<td><c:out value="${product.code}">
							</c:out></td>
						<td><c:out value="${product.name}">
							</c:out></td>
						<td><c:out value="${product.quantity}">
							</c:out></td>
						<td><c:out value="${product.price}">
							</c:out></td>
						<td align="center"><a
							href="cadastroProduto?action=delete&products=${product.id}"><img
								alt="Excluir" title="Excluir" src="resources/img/excluir.png"
								width="20px" height="20px"></a></td>
						<td class="row100 body" align="center"><a
							href="cadastroProduto?action=edit&products=${product.id}"><img
								alt="Editar" title="Editar" src="resources/img/editar.png"
								width="20px" height="20px"></a></td>
					</tr>
				</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validateForm() {
			if (document.getElementById("codigo").value == '') {
				alert("Informe o Código do produto")
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert("Informe o Nome do produto")
				return false;
			} else if (document.getElementById("qtd").value == '') {
				alert("Informe a Quantidade do produto")
				return false;
			} else if (document.getElementById("preco").value == ''){
				alert("Informe o Preço do produto")
				return false;
			}

			return true;
		}
	</script>
</body>
</html>