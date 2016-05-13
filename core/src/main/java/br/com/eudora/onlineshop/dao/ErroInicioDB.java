package br.com.eudora.onlineshop.dao;

public class ErroInicioDB extends RuntimeException {

	public ErroInicioDB(Exception e) {
		super(e);
	}

}
