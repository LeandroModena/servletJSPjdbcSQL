package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeansProduct;
import dao.DaoProducts;

@WebServlet("/cadastroProduto")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoProducts daoProducts = new DaoProducts();

	public ProductServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String action = request.getParameter("action");
		String product = request.getParameter("products");

		System.out.println("Essa � sua a��o => " + action + "  Esse � o seu usuario => " + product);

		if (action != null) {
			if (action.equalsIgnoreCase("delete")) {

				try {
					daoProducts.delete(product);
					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("products", daoProducts.listar());
					view.forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (action.equalsIgnoreCase("edit")) {
				try {
					BeansProduct beansProduct = daoProducts.consult(product);
					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("product", beansProduct);
					view.forward(request, response);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else if (action.equalsIgnoreCase("listall")) {

				try {
					System.out.println("==============++=================");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("products", daoProducts.listar());
					view.forward(request, response);
				} catch (SQLException e) {
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
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("products", daoProducts.listar());
				view.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			String id = request.getParameter("id");
			String codigo = request.getParameter("codigo");
			String nome = request.getParameter("nome");
			String qtd = request.getParameter("qtd");
			String preco = request.getParameter("preco");

			BeansProduct beansProduct = new BeansProduct();
			beansProduct.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			beansProduct.setCode(codigo);
			beansProduct.setName(nome);
			beansProduct.setQuantity(qtd);
			beansProduct.setPrice(preco);

			try {
				if (codigo == null || codigo.isEmpty()) {
					request.setAttribute("msg", "C�digo � obrigat�rio");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("product", beansProduct);
					request.setAttribute("products", daoProducts.listar());
					view.forward(request, response);
					return;
				
				} else if(nome == null || nome.isEmpty()) {
					request.setAttribute("msg", "Nome � obrigat�rio");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("product", beansProduct);
					request.setAttribute("products", daoProducts.listar());
					view.forward(request, response);
					return;
				
				} else if (qtd == null || qtd.isEmpty()) {
					request.setAttribute("msg", "Quantidade � obrigat�rio");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("product", beansProduct);
					request.setAttribute("products", daoProducts.listar());
					view.forward(request, response);
					return;

				} else if (preco == null || preco.isEmpty()) {
					request.setAttribute("msg", "Pre�o � obrigat�rio");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("product", beansProduct);
					request.setAttribute("products", daoProducts.listar());
					view.forward(request, response);
					return;
					
				} else if (id == null || id.isEmpty() && !daoProducts.validate(codigo)) {
					request.setAttribute("msg", "C�digo j� existente, por favor escolha outro codigo");
					RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("product", beansProduct);
					request.setAttribute("products", daoProducts.listar());
					view.forward(request, response);
					return;
				}

				if (id == null || id.isEmpty() && daoProducts.validate(codigo)) {
					daoProducts.saveProducts(beansProduct);
				} else if (id != null || !id.isEmpty()) {
					if (!daoProducts.validateUpdate(codigo, id)) {
						request.setAttribute("msg", "C�digo j� em uso por favor escolha outro codigo");
						request.setAttribute("product", beansProduct);
					} else {
						daoProducts.update(beansProduct);
					}
				}

				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("products", daoProducts.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
	