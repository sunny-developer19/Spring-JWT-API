package app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import app.dto.Idea;

public interface IdeaRepository extends PagingAndSortingRepository<Idea, Long> {

	
}
