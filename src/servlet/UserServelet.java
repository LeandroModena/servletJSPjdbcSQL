package servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeansUser;
import dao.DaoUser;

@WebServlet("/saveUser")
@MultipartConfig
public class UserServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUser daoUser = new DaoUser();

	public UserServelet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		String user = request.getParameter("users");

		// System.out.println("Essa é sua ação => " + action + " Esse é o seu usuario =>
		// " + user);

		if (action != null) {
			if (action.equalsIgnoreCase("delete")) {

				try {
					daoUser.delete(user);
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("users", daoUser.listar());
					view.forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				try {
					BeansUser beansJSP = daoUser.consult(user);
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("user", beansJSP);
					view.forward(request, response);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if (action.equalsIgnoreCase("listall")) {

				try {
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("users", daoUser.listar());
					view.forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (action.equalsIgnoreCase("download")) {
				try {
					BeansUser user2 = daoUser.consult(user);
					if (user2 != null) {
						response.setHeader("Content-Disposition", "attachment;filename=arquivo." + user2.getContentType().split("\\/")[1]);
						
						/* Converte a base64 da imagem do banco para byte*/
						byte[] imageFotoBytes = new Base64().decodeBase64(user2.getFotoBase64());
						
						/* Coloca os bytes em um objeto de entrada para processar*/
						InputStream inputStream = new ByteArrayInputStream(imageFotoBytes);
						
						/* Inicio da resposta para o  navegador*/
						
						int read = 0;
						byte[] bytes = new byte[1024];
						OutputStream outputStream = response.getOutputStream();
						
						while ((read = inputStream.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
						outputStream.flush();
						outputStream.close();
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

		String action = request.getParameter("action");

		if (action != null && action.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("users", daoUser.listar());
				view.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			String nome = request.getParameter("nome");
			String telefone = request.getParameter("telefone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("uf");
			String ibge = request.getParameter("ibge");

			BeansUser user = new BeansUser();
			user.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			user.setLogin(login);
			user.setPassword(password);
			user.setNome(nome);
			user.setTelefone(telefone);
			user.setCep(cep);
			user.setRua(rua);
			user.setBairro(bairro);
			user.setCidade(cidade);
			user.setUf(estado);
			user.setIbge(ibge);

			try {

				/* Inicio de upload de imagem */

				if (ServletFileUpload.isMultipartContent(request)) {
					Part imagemFoto = request.getPart("foto");
					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
						String fotoBase64 = Base64
								.encodeBase64String(converteStremParaByte(imagemFoto.getInputStream()));
						user.setFotoBase64(fotoBase64);
						user.setContentType(imagemFoto.getContentType());
						//user.setFotoBase64(criarMiniaturaImagem(fotoBase64));
					}

				}

				/* Fim de upload de imagem */

				if (login == null || login.isEmpty()) {
					request.setAttribute("msg", "Login é obrigatório");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("user", user);
					request.setAttribute("users", daoUser.listar());
					view.forward(request, response);
					return;

				} else if (password == null || password.isEmpty()) {
					request.setAttribute("msg", "Senha é obrigatório");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("user", user);
					request.setAttribute("users", daoUser.listar());
					view.forward(request, response);
					return;

				} else if (nome == null || nome.isEmpty()) {
					request.setAttribute("msg", "Nome é obrigatório");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("user", user);
					request.setAttribute("users", daoUser.listar());
					view.forward(request, response);
					return;

				} else if (id == null || id.isEmpty() && !daoUser.validate(login)) {
					request.setAttribute("msg", "Usuário já existente, por favor escolha outro");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("user", user);
					request.setAttribute("users", daoUser.listar());
					view.forward(request, response);
					return;
				}

				if (id == null || id.isEmpty() && daoUser.validate(login)) {
					daoUser.saveUser(user);

				} else if (id != null || !id.isEmpty()) {
					if (!daoUser.validateUpdate(login, id)) {
						request.setAttribute("msg", "Login já em uso");
						request.setAttribute("user", user);
					} else {
						daoUser.update(user);
					}
				}

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("users", daoUser.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	

	private byte[] converteStremParaByte(InputStream imagem) throws Exception {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while(reads != -1) {
			baos.write(reads);
			reads = imagem.read();
			
			
		}
		
		return baos.toByteArray();
	}

}
