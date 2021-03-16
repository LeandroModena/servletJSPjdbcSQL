package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeansUser;
import connection.SingleConnection;

public class DaoUser {

	private Connection connection;

	public DaoUser() {
		connection = SingleConnection.getConnection();

	}

	public void saveUser(BeansUser user) {
		String sql = "insert into users(login, password, nome, telefone, cep, rua, bairro, cidade, estado, ibge, fotobase64, contenttype) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insert;
		try {
			insert = connection.prepareStatement(sql);
			insert.setString(1, user.getLogin());
			insert.setString(2, user.getPassword());
			insert.setString(3, user.getNome());
			insert.setString(4, user.getTelefone());
			insert.setString(5, user.getCep());
			insert.setString(6, user.getRua());
			insert.setString(7, user.getBairro());
			insert.setString(8, user.getCidade());
			insert.setString(9, user.getUf());
			insert.setString(10, user.getIbge());
			insert.setString(11, user.getFotoBase64());
			insert.setString(12, user.getContentType());
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

	public List<BeansUser> listar() throws SQLException {
		List<BeansUser> listar = new ArrayList<BeansUser>();

		String sql = "select * from users";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeansUser beansJSP = new BeansUser();
			beansJSP.setId(resultSet.getLong("id"));
			beansJSP.setLogin(resultSet.getString("login"));
			beansJSP.setPassword(resultSet.getString("password"));
			beansJSP.setNome(resultSet.getString("nome"));
			beansJSP.setTelefone(resultSet.getString("telefone"));
			beansJSP.setCep(resultSet.getString("cep"));
			beansJSP.setRua(resultSet.getString("rua"));
			beansJSP.setBairro(resultSet.getString("bairro"));
			beansJSP.setCidade(resultSet.getString("cidade"));
			beansJSP.setUf(resultSet.getString("estado"));
			beansJSP.setIbge(resultSet.getString("ibge"));
			beansJSP.setFotoBase64(resultSet.getString("fotobase64"));
			beansJSP.setContentType(resultSet.getString("contenttype"));
			listar.add(beansJSP);

		}

		return listar;
	}

	public void delete(String id) {
		String sql = "delete from users where id= '" + id + "'";

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

	public BeansUser consult(String id) throws Exception {
		String sql = "select * from users where id= '" + id + "'";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			BeansUser beansJSP = new BeansUser();
			beansJSP.setId(resultSet.getLong("id"));
			beansJSP.setLogin(resultSet.getString("login"));
			beansJSP.setPassword(resultSet.getString("password"));
			beansJSP.setNome(resultSet.getString("nome"));
			beansJSP.setTelefone(resultSet.getString("telefone"));
			beansJSP.setCep(resultSet.getString("cep"));
			beansJSP.setRua(resultSet.getString("rua"));
			beansJSP.setBairro(resultSet.getString("bairro"));
			beansJSP.setCidade(resultSet.getString("cidade"));
			beansJSP.setUf(resultSet.getString("estado"));
			beansJSP.setIbge(resultSet.getString("ibge"));
			beansJSP.setFotoBase64(resultSet.getString("fotobase64"));
			beansJSP.setContentType(resultSet.getString("contenttype"));
			return beansJSP;
		}

		return null;
	}
	
	public boolean validate(String login) throws Exception {
		
		String loginLower = login.toLowerCase();
		String sql = "select count(1) as qtd from users where LOWER(login)= '"+loginLower+ "'";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			
			
			return resultSet.getInt("qtd") <= 0;
		}

		return false;
	}
	
	public boolean validateUpdate(String login, String id) throws Exception {
		
		String loginLower = login.toLowerCase();
		String sql = "select count(1) as qtd from users where LOWER(login)= '"+loginLower+"' and id <>" + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			
			
			return resultSet.getInt("qtd") <= 0;
		}

		return false;
	}

	public void update(BeansUser user) {

		String sql = "update users set login = ?, password = ?, nome = ?, telefone = ?, cep = ?,"
				+ "rua = ?, bairro = ?, cidade = ?, estado = ?, ibge = ? WHERE id = " + user.getId();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getNome());
			statement.setString(4, user.getTelefone());
			statement.setString(5, user.getCep());
			statement.setString(6, user.getRua());
			statement.setString(7, user.getBairro());
			statement.setString(8, user.getCidade());
			statement.setString(9, user.getUf());
			statement.setString(10, user.getIbge());
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
