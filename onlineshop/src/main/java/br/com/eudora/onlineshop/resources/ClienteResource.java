package br.com.eudora.onlineshop.resources; 

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import br.com.eudora.onlineshop.dominio.Cliente;

@ApplicationPath("/resources")
@Path("clientes")
public class ClienteResource extends Application {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("eudorashop");
   
	@PersistenceContext
    private EntityManager entityManager = factory.createEntityManager();

    private Integer countClientes() {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Cliente p");
        return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    private List<Cliente> buscaClientes(int startPosition, int maxResults, String sortFields, String sortDirections) {
        Query query = entityManager.createQuery("SELECT p FROM Cliente p ORDER BY " + sortFields + " " + sortDirections);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    public PaginatedListWrapper<Cliente> buscaClientes(PaginatedListWrapper<Cliente> wrapper) {
        wrapper.setTotalResults(countClientes());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(buscaClientes(start,
                                    wrapper.getPageSize(),
                                    wrapper.getSortFields(),
                                    wrapper.getSortDirections()));
        return wrapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedListWrapper<Cliente> listClientes(@DefaultValue("1")
                                                    @QueryParam("page")
                                                    Integer page,
                                                    @DefaultValue("id")
                                                    @QueryParam("sortFields")
                                                    String sortFields,
                                                    @DefaultValue("asc")
                                                    @QueryParam("sortDirections")
                                                    String sortDirections) {
        PaginatedListWrapper<Cliente> paginatedListWrapper = new PaginatedListWrapper();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(5);
        return buscaClientes(paginatedListWrapper);
    }
    
    @GET
    @Path("{id}")
    public Cliente getCliente( @PathParam("id") Long id) {
        return entityManager.find(Cliente.class, id);
    }
 
    @POST
    public Cliente saveCliente(Cliente Cliente) {
        if (Cliente.getId() == null) {
            Cliente ClienteToSave = new Cliente();
            ClienteToSave.setNome(Cliente.getNome());
            ClienteToSave.setDtNascimento(Cliente.getDtNascimento());
            entityManager.persist(Cliente);
        } else {
            Cliente ClienteToUpdate = getCliente(Cliente.getId());
            ClienteToUpdate.setNome(Cliente.getNome());
            ClienteToUpdate.setDtNascimento(Cliente.getDtNascimento());
            Cliente = entityManager.merge(ClienteToUpdate);
        }
 
        return Cliente;
    }
 
    @DELETE
    @Path("{id}")
    public void deleteCliente(@PathParam("id") Long id) {
        entityManager.remove(getCliente(id));
    }
}
