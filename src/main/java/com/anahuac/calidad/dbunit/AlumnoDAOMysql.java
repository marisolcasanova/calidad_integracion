package com.anahuac.calidad.dbunit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.anahuac.calidad.doublesDAO.Alumno;
import com.anahuac.calidad.doublesDAO.AlumnoDAO;

public class AlumnoDAOMysql implements AlumnoDAO {
	public Connection getConnectionMySQL() {
		Connection con=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:33060/pruebas_db","root","secret");
		}catch(Exception e) {System.out.println(e);}
		return con;
	}

	
	public boolean addAlumno(Alumno a) {
		// TODO Auto-generated method stub
		
		Connection connection = getConnectionMySQL();
		PreparedStatement preparedStatement;
		boolean result = false;
		try {
			preparedStatement = connection.prepareStatement(
					"insert INTO alumnos_tb1(id, nombre, email, edad) values (?, ?, ?, ?)");
			preparedStatement.setString(1, a.getId());
			preparedStatement.setString(2, a.getNombre());
			preparedStatement.setString(3, a.getEmail());
			preparedStatement.setInt(4, a.getEdad());
			if(preparedStatement.executeUpdate() >= 1)
				result = true;
			
			connection.close();
				
					
		} catch (SQLException e) {
			System.out.println(e);
		}
		return result;
	}

	
	public boolean deleteAlumno(Alumno a) {
		// TODO Auto-generated method stub
		Connection connection = getConnectionMySQL();
		PreparedStatement preparedStatement;
		boolean result = false;
		
		try {
			preparedStatement = connection.prepareStatement("Delete from alumnos_tb1 WHERE id = ?");
			preparedStatement.setString(1, a.getId());
			
			if (preparedStatement.executeUpdate() >= 1)
				result = true;
			
			
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return result;
	}

	@Override
	public boolean updateEmail(Alumno a) {
		Connection connection = getConnectionMySQL();
		PreparedStatement preparedStatement;
		boolean result = false;
		
		try {
			preparedStatement = connection.prepareStatement("UPDATE alumnos_tb1 set email = ? WHERE id = ?");
			
			preparedStatement.setString(1, a.getEmail());
			preparedStatement.setString(2, a.getId());
			if (preparedStatement.executeUpdate() >= 1)
				result = true;
			
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return result;
	}

	@Override
	public Alumno consultarAlumno(String id) {
		// TODO Auto-generated method stub
		Connection connection = getConnectionMySQL();
		PreparedStatement preparedStatement;
		ResultSet rs;
		
		Alumno retrieved = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * from alumnos_tb1 WHERE id =  ?");
			preparedStatement.setString(1, id);
			rs = preparedStatement.executeQuery();
			
			rs.next();
			String retrievedId = rs.getString(1);
			String retrievedName = rs.getString(2);
			String retrievedEmail = rs.getString(3);
			int retrievedAge = rs.getInt(4);
			
			retrieved = new Alumno(retrievedId, retrievedName, retrievedEmail, retrievedAge);
			
			rs.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return retrieved;
	}



}
