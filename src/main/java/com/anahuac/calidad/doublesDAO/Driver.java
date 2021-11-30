package com.anahuac.calidad.doublesDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Driver {

	public static void main(String[] args) {
		//conectarnos a la bd e insertar un alumno
		Alumno a = new Alumno("005", "alumno5", "alumno5@hola.com", 20);
		Connection con;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(
					"jdbc:mysql://localhost:33060/pruebas_db","root","secret");
			
			PreparedStatement preparedStatement;
			preparedStatement = con.prepareStatement(
					"insert INTO alumnos_tb1(id, nombre, email, edad) values (?, ?, ?, ?)");
			
			preparedStatement.setString(1, a.getId());
			preparedStatement.setString(2, a.getNombre());
			preparedStatement.setString(3, a.getEmail());
			preparedStatement.setInt(4, a.getEdad());
			
			int result = preparedStatement.executeUpdate();
			System.out.println(">> Return: " + result);
			con.close();
	
		}catch(Exception e) {
			System.out.println(e);
		}
		
		

	}

}
