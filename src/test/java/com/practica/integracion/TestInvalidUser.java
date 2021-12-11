package com.practica.integracion;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.*;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.*;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	GenericDAO mockGenericDao;
	AuthDAO mockAuthDao;
	
	@BeforeEach
	void setUp() {
		mockGenericDao = mock(GenericDAO.class);
		mockAuthDao = mock(AuthDAO.class);
	}
	
	@Test
	public void TC13() throws Exception {
		String userId = "aaa";
		User inValidUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		String validId ="12345";
		when(mockAuthDao.getAuthData(inValidUser.getId())).thenReturn(inValidUser);

		String remoteId = "12345";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(inValidUser, "where id=" + validId)).thenThrow(OperationNotSupportedException.class);
	  
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
	  
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		
		try {
			manager.startRemoteSystem(userId, remoteId);
			fail("Exception not thrown when expected! ");
			
		} catch (SystemManagerException e) {
			ordered.verify(mockAuthDao).getAuthData(inValidUser.getId());
			ordered.verify(mockGenericDao).getSomeData(inValidUser, "where id=" + remoteId);

		}
}
	
	@Test
	public void TC23() throws Exception {
		String userId = "aaa";
		User inValidUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		String validId ="12345";
		when(mockAuthDao.getAuthData(inValidUser.getId())).thenReturn(inValidUser);

		String remoteId = "12345";
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(inValidUser, "where id=" + validId)).thenThrow(OperationNotSupportedException.class);
	  
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
	  
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		
		try {
			manager.stopRemoteSystem(userId, remoteId);
			fail("Exception not thrown when expected! ");
			
		} catch (SystemManagerException e) {
			ordered.verify(mockAuthDao).getAuthData(inValidUser.getId());
			ordered.verify(mockGenericDao).getSomeData(inValidUser, "where id=" + remoteId);

		}
	}
	
	@Test
	void TC33() throws OperationNotSupportedException {
		String userId = "12345";
		
		User validUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String remoteId = "aaa";
		when(mockGenericDao.updateSomeData(validUser, remoteId)).thenReturn(false);
		
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
