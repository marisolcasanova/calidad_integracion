package com.anahuac.calidad.doublesDAO;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class FakeOracleAlumnoDAOTest {
	
	private FakeOracleDAO dao;
	private HashMap<String, Alumno> alumnos;
	Alumno alumno1;
	

	@Before
	public void setUp() throws Exception {
		dao = Mockito.mock(FakeOracleDAO.class);
		alumnos = new HashMap<String, Alumno>();
		alumno1 = new Alumno("001", "nombre", "micorreo2@hola.com", 25);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addAlumnoTest() {
		
		int cuantosAntes = alumnos.size();
		System.out.println("Add Alumno Mock");
		System.out.println("Size antes=" + cuantosAntes);
		
		when(dao.addAlumno(any(Alumno.class))).thenAnswer(
				new Answer<Boolean>() {
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						Alumno arg = (Alumno) invocation.getArguments()[0];
						alumnos.put("001", arg);
					
						return true;
					}
				}
				);
		dao.addAlumno(alumno1);
		int cuantosDesp = alumnos.size();
		assertThat(cuantosAntes+1,is(cuantosDesp));
	}
		/*
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Alumno arg = (Alumno) invocation.getArguments()[0];
				alumnos.put(anyString(), arg);	
				return null;
			}})
		.when(dao).addAlumno(any(Alumno.class));
		dao.addAlumno(alumno1);
		*/
		
	@Test
	public void readAlumnoTest() {
		alumnos.put("001", alumno1);
		int cuantosAntes = alumnos.size();
		System.out.println("Read Alumno Mock");
		System.out.println("Size antes= " + cuantosAntes);
		
		when(dao.consultarAlumno(any(String.class))).thenAnswer(new Answer<Alumno>() {
			public Alumno answer(InvocationOnMock invocation) throws Throwable{
				String arg = (String) invocation.getArguments()[0];
				System.out.println("Leer alumno " + alumnos.get(arg).getNombre());
				return alumnos.get(arg);
			}
		}
	);
		dao.consultarAlumno("001");
		int cuantosDesp = alumnos.size();
		assertThat(cuantosAntes,is(cuantosDesp));
		
	}
	
	@Test
	public void deleteAlumno() {
		alumnos.put("001", alumno1);
		int cuantosAntes = alumnos.size();
		System.out.println("Delete Alumno Mock");
		System.out.println("Size antes= " + cuantosAntes);

		when(dao.deleteAlumno(any(Alumno.class))).thenAnswer(new Answer<Boolean>() {
			public Boolean answer(InvocationOnMock invocation) throws Throwable{
				Alumno arg = (Alumno) invocation.getArguments()[0];
				alumnos.remove(arg.getId(), arg);
				System.out.println("Size despues=" + alumnos.size());
				return true;
			}
		}
	);
		dao.deleteAlumno(alumno1);
		int cuantosDesp = alumnos.size();
		assertThat(cuantosAntes-1,is(cuantosDesp));
		//assertThat(cuantosAntes+1,is(not(cuantosDesp)));
	}
	
	@Test
	public void updateEmail() {
		alumnos.put(alumno1.getId(), alumno1);
		String correoAntes = alumno1.getEmail();
		System.out.println("Correo antes: " + correoAntes);
		alumno1 = new Alumno("001", "Nombre", "micorreo255@hola.com", 20);
		
		doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Alumno arg = (Alumno) invocation.getArguments()[0];
				alumnos.replace(arg.getId(),arg);	
				return null;
			}})
		.when(dao).updateEmail(any(Alumno.class));
		
		dao.updateEmail(alumno1);
		String correoDesp = alumnos.get(alumno1.getId()).getEmail();
		System.out.println("Correo despues: " + correoDesp);
		assertThat(correoAntes,is(not(correoDesp)));
	}
	
}
