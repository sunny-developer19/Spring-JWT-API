package app.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import app.dto.Idea;
import app.exceptions.IdeaNotFoundException;

public interface IdeaService {

	List<Idea> listAll();

	Idea getById(Long ideaId) throws IdeaNotFoundException;

	Idea save(Idea idea);
	Idea update(Idea idea, Long id) throws IdeaNotFoundException;

	void delete(Long ideaId) throws IdeaNotFoundException;

	Page<Idea> listAllPaginated(Pageable pageable);
}
