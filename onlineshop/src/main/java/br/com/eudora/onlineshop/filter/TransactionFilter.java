package br.com.eudora.onlineshop.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import br.com.eudora.onlineshop.dao.TransactionManager;

public class TransactionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try{
			
			TransactionManager.beginTransaction();
			
			chain.doFilter(request, response);
			HttpServletResponse r = (HttpServletResponse) response;
			
			if(r.getStatus()>=500){
				if(TransactionManager.isTransactionActive()){
					TransactionManager.rolllBackTransaction();
				}
			}
			
		}catch (Throwable ex){
			ex.printStackTrace();

			if(TransactionManager.isTransactionActive()){
				TransactionManager.rolllBackTransaction();
			}
		}finally {
			TransactionManager.close();
		}
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
