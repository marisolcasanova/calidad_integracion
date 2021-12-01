package com.anahuac.calidad.dbunit;

import static org.junit.Assert.*;

import java.io.File;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.anahuac.calidad.doublesDAO.Alumno;


public class DAOAlumnoTest extends DBTestCase{
	
	public DAOAlumnoTest()
	{
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/pruebas_db");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");

	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		// TODO Auto-generated method stub
		return new FlatXmlDataSetBuilder().build(new File("src/resources/iniDB.xml"));
	}

	@Before
	public void setUp() throws Exception {
		IDatabaseConnection connection = getConnection();
		// added this line to get rid of the warning
		try {
			
			DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
		} catch(Exception e) {
			fail("Error in setup: " + e.getMessage());
		} finally {
			connection.close();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testadd() {
		Alumno alumno = new Alumno("4", "alumno 1", "hola@gmail.com", 23);
		AlumnoDAOMysql daoMySQL = new AlumnoDAOMysql();
		
		daoMySQL.addAlumno(alumno);
		
		try {
			IDataSet databaseDataSet = getConnection().createDataSet(); //esta es toda la base de datos
			
			ITable actualTable = databaseDataSet.getTable("alumnos_tb1");
			
			//leer el archivo con el resultado esperado
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/insert_result.xml"));
			ITable expectedTable = expectedDataSet.getTable("alumnos_tb1");
			
			Assertion.assertEquals(expectedTable, actualTable);
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}
	}

	@Test
	public void testdelete() {
		Alumno alumno = new Alumno("003", "hola3", "hola@hola.com", 20);
		AlumnoDAOMysql daoMySQL = new AlumnoDAOMysql();
		
		daoMySQL.deleteAlumno(alumno);
		
		try {
			IDataSet databaseDataSet = getConnection().createDataSet(); //esta es toda la base de datos
			
			ITable actualTable = databaseDataSet.getTable("alumnos_tb1");
			
			//leer el archivo con el resultado esperado
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/delete_result.xml"));
			ITable expectedTable = expectedDataSet.getTable("alumnos_tb1");
			
			Assertion.assertEquals(expectedTable, actualTable);
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}
	}

	@Test
	public void testupdate() {
		Alumno alumno = new Alumno("003", "hola3", "marisol@hola.com", 20);
		AlumnoDAOMysql daoMySQL = new AlumnoDAOMysql();
		
		daoMySQL.updateEmail(alumno);
		
		try {
			IDataSet databaseDataSet = getConnection().createDataSet(); //esta es toda la base de datos
			
			ITable actualTable = databaseDataSet.getTable("alumnos_tb1");
			
			//leer el archivo con el resultado esperado
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/update_result.xml"));
			ITable expectedTable = expectedDataSet.getTable("alumnos_tb1");
			
			Assertion.assertEquals(expectedTable, actualTable);
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}
	}


	
	@Test
	public void testRead() {
		AlumnoDAOMysql daoMySQL = new AlumnoDAOMysql();
		
		daoMySQL.consultarAlumno("003");
		
		try {
			IDataSet databaseDataSet = getConnection().createDataSet(); //esta es toda la base de datos
			
			ITable actualTable = databaseDataSet.getTable("alumnos_tb1");
			
			//leer el archivo con el resultado esperado
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/read_result.xml"));
			ITable expectedTable = expectedDataSet.getTable("alumnos_tb1");
			
			Assertion.assertEquals(expectedTable, actualTable);
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}
	}

}
