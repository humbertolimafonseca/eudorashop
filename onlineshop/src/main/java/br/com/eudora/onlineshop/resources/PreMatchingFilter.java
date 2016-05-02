package br.com.eudora.onlineshop.resources;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.eudora.onlineshop.dao.TransactionManager;


@Provider
@PreMatching
public class PreMatchingFilter implements ContainerResponseFilter {
 
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		try{
    		if(responseContext.getStatus()>=500){
				if(TransactionManager.isTransactionActive()){
					TransactionManager.rolllBackTransaction();
				}
			}
    		
    		if(TransactionManager.isTransactionActive()){
    			TransactionManager.commitTransaction();
			}
    		
    	}catch (Exception e){
    		e.printStackTrace();
    		responseContext.setStatus(500); ;
    		responseContext.setEntity("Ocorreu um erro.");
    	}
	}
}
