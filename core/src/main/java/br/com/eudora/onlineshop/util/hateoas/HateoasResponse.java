package br.com.eudora.onlineshop.util.hateoas;

public interface HateoasResponse {
	
	
	public String getSelfLink();
	public String getEditLink();
	public String getDeleteLink();
	public String getListLink();
	
	
}
