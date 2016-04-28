package br.com.eudora.onlineshop.resources.hateoas;

import br.com.eudora.onlineshop.dominio.OnlineShopEntity;

public class DefaultHateoasResponse implements HateoasResponse {
	
	private OnlineShopEntity entity;
	private String serverAndContext;

	public DefaultHateoasResponse(OnlineShopEntity entity, String serverAndContext) {
		this.entity = entity;
		this.serverAndContext = serverAndContext;
	}

	@Override
	public String getSelfLink() {
		return serverAndContext + "/resources/"+entity.getEntityResourceName() + "/" + entity.getId().toString();
	}

	@Override
	public String getEditLink() {
		return serverAndContext + "/resources/"+entity.getEntityResourceName() + "/edit/" + entity.getId().toString();
	}

	@Override
	public String getDeleteLink() {
		return getSelfLink();
	}

	@Override
	public String getListLink() {
		return serverAndContext + "/resources/"+entity.getEntityResourceName();
	}

	public OnlineShopEntity getEntity() {
		return entity;
	}

}
