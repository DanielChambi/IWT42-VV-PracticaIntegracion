package com.practica.integracion;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	GenericDAO mockGenericDao = mock(GenericDAO.class);
	AuthDAO mockAuthDao = mock(AuthDAO.class);
	
	
	@Test
	public void testStartRemoteSystemWithValidUserAndSystem() throws Exception {
		System.out.println("Hola");
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

	
}
