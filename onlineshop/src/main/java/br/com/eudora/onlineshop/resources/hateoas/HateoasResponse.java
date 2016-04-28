package br.com.eudora.onlineshop.resources.hateoas;

public interface HateoasResponse {
	
	
	public String getSelfLink();
	public String getEditLink();
	public String getDeleteLink();
	public String getListLink();
	
	
}
