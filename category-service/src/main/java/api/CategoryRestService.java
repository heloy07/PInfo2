package api;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import domain.model.Category;
import domain.service.CategoryService;

@ApplicationScoped
@Path("/category")
public class CategoryRestService {
	
	@Inject
	private CategoryService categoryService;
	
	@GET
	@Produces("application/json")
	public List<Category> getAll() {
		return categoryService.getAll();
	}
	
	
	@GET
	@Path("{id}/children")
	@Produces("application/json")
	public List<Category> getSubCategories(@PathParam("id") Long id) {
		return categoryService.getSubCategories(categoryService.get(id));
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Category getCategory(@PathParam("id") Long id) {
		return categoryService.get(id);
	}
	
	@POST
	@Consumes("application/json")
	public Response create(Category category) {
		Long newId = null;
		try {
			newId = categoryService.create(category);
		} catch(IllegalArgumentException i) {
			return Response.status(Status.BAD_REQUEST).build();
		} catch(Exception e) {
			return Response.status(Status.BAD_GATEWAY).build();
		}
		
		return Response.status(Status.CREATED).location(URI.create("/category/" + newId.toString())).build();
	}
	
	
	@DELETE
	@Path("{name}")
	public Response delete(@PathParam("name") Long id) {
		try {
			categoryService.delete(categoryService.get(id));
		} catch(Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok().build();
	}


}
