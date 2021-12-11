package com.practica.integracion;

import static org.junit.jupiter.api.Assertions.*;

import com.practica.integracion.DAO.*;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;


@ExtendWith(MockitoExtension.class)
public class TestValidUser {
	
	GenericDAO mockGenericDao;
	AuthDAO mockAuthDao;
	
	@BeforeEach
	void setUp() {
		mockGenericDao = mock(GenericDAO.class);
		mockAuthDao = mock(AuthDAO.class);
	}
	
	@Test
	public void TC11() throws Exception {
	  User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
	  
	  when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

	  String validId = "12345";
	  
	  ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
	  
	  when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);
	  
	  InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
	  
	  SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
	  
	  Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
	  
	  assertEquals(retorno.toString(), "[uno, dos]");
	  
	  ordered.verify(mockAuthDao).getAuthData(validUser.getId());
	  ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + validId);
	}
	
	
	@Test
	public void TC12() throws Exception {
		String userId = "12345";
		User validUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String remoteId = "aaa";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + remoteId)).thenThrow(OperationNotSupportedException.class);
	  
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
	  
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		
		try {
			manager.startRemoteSystem(userId, remoteId);
			fail("Exception not thrown when expected! ");
			
		} catch (SystemManagerException e) {
			ordered.verify(mockAuthDao).getAuthData(validUser.getId());
			ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + remoteId);

		}
	  
	}
	
	
	@Test
	public void TC21() throws Exception {
	  User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
	  
	  when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

	  String validId = "12345";
	  
	  ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
	  
	  when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);
	  
	  InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
	  
	  SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
	  
	  Collection<Object> retorno = manager.stopRemoteSystem(validUser.getId(), validId);
	  
	  assertEquals(retorno.toString(), "[uno, dos]");
	  
	  ordered.verify(mockAuthDao).getAuthData(validUser.getId());
	  ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + validId);
	}
	
	@Test
	public void TC22() throws Exception {
		String userId = "12345";
		User validUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String remoteId = "aaa";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + remoteId)).thenThrow(OperationNotSupportedException.class);
	  
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
	  
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		
		try {
			manager.stopRemoteSystem(userId, remoteId);
			fail("Exception not thrown when expected! ");
			
		} catch (SystemManagerException e) {
			ordered.verify(mockAuthDao).getAuthData(validUser.getId());
			ordered.verify(mockGenericDao).getSomeData(validUser, "where id=" + remoteId);

		}
	  
	}
	
	
	
    @Test
	void TC31() throws OperationNotSupportedException {
		String userId = "12345";
		
		User validUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String remoteId = "12345";
		when(mockGenericDao.updateSomeData(validUser, remoteId)).thenReturn(true);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		  
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		try {
			manager.addRemoteSystem(userId, remoteId);
			
			ordered.verify(mockAuthDao).getAuthData(validUser.getId());
			ordered.verify(mockGenericDao).updateSomeData(validUser, remoteId);
			
		} catch (SystemManagerException e) {
			fail("Exception Thrown when not expected! "  + e);
		}
	}
	
	@Test
	void TC32() throws OperationNotSupportedException {
		String userId = "12345";
		
		User validUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String remoteId = "aaa";
		when(mockGenericDao.updateSomeData(validUser, remoteId)).thenThrow(OperationNotSupportedException.class);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		try {
			manager.addRemoteSystem(userId, remoteId);
			fail("Exception not thrown when expected! ");
			
		} catch (SystemManagerException e) {
			ordered.verify(mockAuthDao).getAuthData(validUser.getId());
			ordered.verify(mockGenericDao).updateSomeData(validUser, remoteId);

		}
	}
	


}
