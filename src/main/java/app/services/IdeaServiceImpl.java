package app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import app.dto.Idea;
import app.exceptions.IdeaNotFoundException;
import app.repositories.IdeaRepository;

@Service
public class IdeaServiceImpl implements IdeaService {

	private IdeaRepository ideaRepository;

	@Autowired
	public IdeaServiceImpl(IdeaRepository ideaRepository) {
		this.ideaRepository = ideaRepository;
	}

	@Override
	public List<Idea> listAll() {
		List<Idea> ideas = new ArrayList<Idea>();
		ideaRepository.findAll().forEach(ideas::add); 

		return ideas;
	}

	@Override
	public Idea getById(Long ideaId) throws IdeaNotFoundException {
		// TODO Auto-generated method stub
		Idea idea =  null;

		try {
			 idea =  ideaRepository.findById(ideaId).get();
		}
		catch(NoSuchElementException ex)
		{
			throw new IdeaNotFoundException("Idea for idea id = " + ideaId + " was not found!");
		}
		
		return idea;
	}

	@Override
	public Idea save(Idea idea) {
		// TODO Auto-generated method stub
		Idea savedIdea = ideaRepository.save(idea);

		return savedIdea;
	}



	@Override
	public void delete(Long ideaId) throws IdeaNotFoundException {

		Idea ideaToDelete = getById(ideaId);
		
		ideaRepository.deleteById(ideaId);

	}
	
	

	@Override
	public Idea update(Idea idea, Long id) throws IdeaNotFoundException {
		// TODO Auto-generated method stub
		Idea ideaToUpdate = getById(id);
		ideaToUpdate.setContent(idea.getContent());
		ideaToUpdate.setEase(idea.getEase());
		ideaToUpdate.setImpact(idea.getImpact());
		ideaToUpdate.setConfidence(idea.getConfidence());
		ideaToUpdate.setAverage_score((idea.getConfidence() + idea.getEase() + idea.getImpact()) / 3);




		return ideaRepository.save(ideaToUpdate);
	}

	@Override
	public Page<Idea> listAllPaginated(Pageable pageable) {
		
		return ideaRepository.findAll(pageable);
	}

	
	


}
