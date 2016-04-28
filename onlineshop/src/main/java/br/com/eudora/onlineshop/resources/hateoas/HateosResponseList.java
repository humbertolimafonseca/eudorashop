package br.com.eudora.onlineshop.resources.hateoas;

import java.util.ArrayList;
import java.util.List;

import br.com.eudora.onlineshop.dominio.OnlineShopEntity;

public class HateosResponseList extends ArrayList<DefaultHateoasResponse> {
	
	public HateosResponseList(List<? extends OnlineShopEntity> l, String context) {
		for (OnlineShopEntity e : l) {
			this.add(new DefaultHateoasResponse(e, context));
		}
	}

}
