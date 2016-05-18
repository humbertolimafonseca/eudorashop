package br.com.eudora.onlineshop.manager;

import javax.enterprise.inject.Default;

import br.com.eudora.onlineshop.dao.TagDao;
import br.com.eudora.onlineshop.dominio.Tag;

@Default
public class TagManager extends Manager<TagDao, Tag, String>{
	
	public TagManager() {
	}
	
}
