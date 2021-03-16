package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeansProduct;
import connection.SingleConnection;

public class DaoProducts {
	
	private Connection connection;
	
	public DaoProducts() {
		connection = SingleConnection.getConnection();
	}
	
	public void saveProducts(BeansProduct product) {
		String sql = "insert into products(codigo, nome, qtd, preco) values (?, ?, ?, ?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, product.getCode());
			insert.setString(2, product.getName());
			insert.setString(3, product.getQuantity());
			insert.setString(4, product.getPrice());
			insert.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public List<BeansProduct> listar() throws SQLException {
		List<BeansProduct> listar = new ArrayList<BeansProduct>();

		String sql = "select * from products";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeansProduct beans = new BeansProduct();
			beans.setId(resultSet.getLong("id"));
			beans.setCode(resultSet.getString("codigo"));
			beans.setName(resultSet.getString("nome"));
			beans.setPrice(resultSet.getString("preco"));
			beans.setQuantity(resultSet.getString("qtd"));
			listar.add(beans);

		}

		return listar;
	}

	public void delete(String id) {
		String sql = "delete from products where id= '" +id+"'";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public BeansProduct consult(String id) throws Exception {
		String sql = "select * from products where id= '" + id + "'";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			BeansProduct beans = new BeansProduct();
			beans.setId(resultSet.getLong("id"));
			beans.setCode(resultSet.getString("codigo"));
			beans.setName(resultSet.getString("nome"));
			beans.setPrice(resultSet.getString("preco"));
			beans.setQuantity(resultSet.getString("qtd"));
			return beans;
		}

		return null;
	}
	
	public boolean validate(String name) throws Exception {
		
		String nameLower = name.toLowerCase();
		String sql = "select count(1) as qtd from products where LOWER(codigo)= '"+nameLower+ "'";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			
			
			return resultSet.getInt("qtd") <= 0;
		}

		return false;
	}
	
	public boolean validateUpdate(String name, String id) throws Exception {
		
		String nameLower = name.toLowerCase();
		String sql = "select count(1) as qtd from products where LOWER(codigo)= '"+nameLower+"' and id <>" + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			
			
			return resultSet.getInt("qtd") <= 0;
		}

		return false;
	}

	public void update(BeansProduct product) {

		String sql = "update products set codigo = ?, nome = ?, qtd = ?, preco = ? WHERE id = " + product.getId();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, product.getCode());
			statement.setString(2, product.getName());
			statement.setString(3, product.getQuantity());
			statement.setString(4, product.getPrice());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

}
