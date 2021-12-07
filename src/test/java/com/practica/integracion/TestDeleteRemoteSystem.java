package com.practica.integracion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import com.practica.integracion.DAO.*;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.OperationNotSupportedException;

class TestDeleteRemoteSystem {
	GenericDAO mockGenericDao;
	AuthDAO mockAuthDao;
	
	@BeforeEach
	void setUp() {
		mockGenericDao = mock(GenericDAO.class);
		mockAuthDao = mock(AuthDAO.class);
	}
	
	@Test
	void validUserAndValidRemoteIDTest() throws OperationNotSupportedException {
		String userId = "12345";
		
		User validUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String remoteId = "12345";
		when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(true);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		
		  
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		
		try {
			manager.deleteRemoteSystem(userId, remoteId);
			
			ordered.verify(mockAuthDao).getAuthData(validUser.getId());
			ordered.verify(mockGenericDao).deleteSomeData(validUser, remoteId);
			
		} catch (SystemManagerException e) {
			fail("Exception Thrown when not expected! "  + e);
		}
	}
	
	@Test
	void validUserAndInvalidRemoteIDTest() throws OperationNotSupportedException {
		String userId = "12345";
		
		User validUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String remoteId = "aaa";
		when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenThrow(OperationNotSupportedException.class);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		try {
			manager.deleteRemoteSystem(userId, remoteId);
			fail("Exception not thrown when expected! ");
			
		} catch (SystemManagerException e) {
			ordered.verify(mockAuthDao).getAuthData(validUser.getId());
			ordered.verify(mockGenericDao).deleteSomeData(validUser, remoteId);

		}
	}
	
	@Test
	void invalidUserAndValidRemoteIDTest() throws OperationNotSupportedException {
		String userId = "12345";
		
		User validUser = new User(userId,"Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);
		
		String remoteId = "aaa";
		when(mockGenericDao.deleteSomeData(validUser, remoteId)).thenReturn(false);
		
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);
		try {
			manager.deleteRemoteSystem(userId, remoteId);
			fail("Exception not thrown when expected! ");
		} catch (SystemManagerException e) {
			ordered.verify(mockAuthDao).getAuthData(validUser.getId());
			ordered.verify(mockGenericDao).deleteSomeData(validUser, remoteId);

		}
	}

}
