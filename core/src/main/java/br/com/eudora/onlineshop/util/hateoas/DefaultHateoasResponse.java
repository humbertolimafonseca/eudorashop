package br.com.eudora.onlineshop.util.hateoas;

import br.com.eudora.onlineshop.dominio.OnlineShopEntity;

public class DefaultHateoasResponse implements HateoasResponse {
	
	private OnlineShopEntity entity;
	private String serverAndContext;

	public DefaultHateoasResponse(OnlineShopEntity entity, String serverAndContext) {
		this.entity = entity;
		this.serverAndContext = serverAndContext;
	}

	public String getSelfLink() {
		return serverAndContext + "/resources/"+entity.getEntityResourceName() + "/" + entity.getId().toString();
	}

	public String getEditLink() {
		return serverAndContext + "/resources/"+entity.getEntityResourceName() + "/edit/" + entity.getId().toString();
	}

	public String getDeleteLink() {
		return getSelfLink();
	}

	public String getListLink() {
		return serverAndContext + "/resources/"+entity.getEntityResourceName();
	}

	public OnlineShopEntity getEntity() {
		return entity;
	}

}
