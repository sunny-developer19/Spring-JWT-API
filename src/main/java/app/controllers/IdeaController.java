package app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.dto.Idea;
import app.dto.UserDetails;
import app.exceptions.IdeaNotFoundException;
import app.services.IdeaService;
import app.services.UserService;

@RestController
public class IdeaController {

	@Autowired
	private IdeaService ideaService;

	@RequestMapping(method = RequestMethod.GET,  params = {"page"}, path = "/ideas")
	public List<Idea> getAllIdeassPaginated( @RequestParam("page") int pageNum)
	{
		int pageNumToFetch = pageNum - 1; // as spring pages start with 0.
		
		if (pageNumToFetch < 0)
		{
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Page num = " + pageNum  + " is not a valid pageNum. Valid Page num are more than 0", 
					new IllegalArgumentException("Invalid page number."));
		}
		
		Pageable pageable = PageRequest.of(pageNum, 10,
	              Sort.by("averageScore").descending());
		
		
		Page<Idea> pages = ideaService.listAllPaginated(pageable);
		
		if (pageNumToFetch > pages.getTotalPages())
		{
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Page num = " + pageNum  + " is not a valid pageNum. Valid Page num are less than ytotal number of pages = " + pages.getTotalPages(), 
					new IllegalArgumentException("Invalid page number."));
		}
		
		return pages.getContent();

	}


	@RequestMapping(method = RequestMethod.POST, path = "/ideas")
	@ResponseStatus(code = HttpStatus.CREATED)
	@Consumes(value="appliction/json")
	@Produces(value="appliction/json")
	public Idea addIdea(@RequestBody Idea idea) {

		return ideaService.save(idea);
	}


	@RequestMapping(method = RequestMethod.PUT, path = "/ideas/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@Consumes(value="appliction/json")
	@Produces(value="appliction/json")
	public Idea updateIdea(@RequestBody Idea idea, @PathVariable(name = "id")  Long id) {

		Idea ideaUpdated = null;

		try {
			ideaUpdated = ideaService.update(idea, id);
		} catch (IdeaNotFoundException e) {
		
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Idea for the given id = " + id  + " was not found", 
					e);
		}

		return ideaUpdated;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/ideas/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteIdea(@PathVariable(name = "id") Long id) {

		try {
			ideaService.delete(id);
		} catch (IdeaNotFoundException e) {
			
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Idea for the given id = " + id  + " was not found", 
					e);
		}
	}

	public IdeaService getIdeaService() {
		return ideaService;
	}

	public void setIdeaService(IdeaService ideaService) {
		this.ideaService = ideaService;
	}

}
