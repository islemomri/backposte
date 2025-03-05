package com.project.app.service;

import com.project.app.dto.PosteDTO;
import com.project.app.model.Direction;
import com.project.app.model.Poste;
import com.project.app.repository.DirectionRepository;
import com.project.app.repository.PosteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PosteService implements IPosteService {

    @Autowired
    private PosteRepository posteRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Override
    public Poste ajouterPoste(Poste poste) {
        return posteRepository.save(poste);
    }

    @Override
    public List<Poste> getAllPostes() {
        return posteRepository.findAll();
    }

    @Override
    public Poste getPosteById(Long id) {
        return posteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poste non trouvé avec l'ID : " + id));
    }

    @Override
    public Poste updatePoste(Long id, PosteDTO posteDto) {
        Poste poste = posteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poste introuvable avec l'ID : " + id));

        poste.setTitre(posteDto.getTitre());
        poste.setNiveauExperience(posteDto.getNiveauExperience());
        poste.setDiplomeRequis(posteDto.getDiplomeRequis());
        poste.setCompetencesRequises(posteDto.getCompetencesRequises());
        Set<Direction> directions = posteDto.getDirectionIds() != null
                ? posteDto.getDirectionIds().stream()
                  .map(directionRepository::findById)
                  .filter(java.util.Optional::isPresent)
                  .map(java.util.Optional::get)
                  .collect(Collectors.toSet())
                : new HashSet<>();

        poste.setDirections(directions);

        return posteRepository.save(poste);
    }

    @Override
    public void deletePoste(Long id) {
        Poste poste = posteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poste non trouvé avec l'ID : " + id));
        posteRepository.delete(poste);
    }

	@Override
	public List<Poste> getAllPostesnonArchivés() {
		return posteRepository.findByArchiveFalse();
	}

	@Override
	public Poste archiverPoste(Long id) {
		Poste Poste = posteRepository.findById(id).orElseThrow(() -> new RuntimeException("Direction not found"));
		Poste.archiver(); 
        return posteRepository.save(Poste);
	}

	@Override
	public List<Poste> getAllPostesArchivés() {
		return posteRepository.findByArchiveTrue();
	}

	@Override
	public Poste desarchiverPoste(Long id) {
		Poste Poste = posteRepository.findById(id).orElseThrow(() -> new RuntimeException("Direction non trouvée"));
		Poste.desarchiver();
        return posteRepository.save(Poste);
	}
	
	 public Poste addPosteWithDirections(PosteDTO posteDTO) {
	        Poste poste = new Poste();
	        poste.setTitre(posteDTO.getTitre());
	        poste.setNiveauExperience(posteDTO.getNiveauExperience());
	        poste.setDiplomeRequis(posteDTO.getDiplomeRequis());
	        poste.setCompetencesRequises(posteDTO.getCompetencesRequises());

	        // Récupérer les directions par leurs IDs
	        Set<Direction> directions = new HashSet<>();
	        for (Long directionId : posteDTO.getDirectionIds()) {
	            Direction direction = directionRepository.findById(directionId).orElse(null);
	            if (direction != null) {
	                directions.add(direction);
	            }
	        }

	        poste.setDirections(directions);

	        return posteRepository.save(poste);
	    }

	@Override
	public Optional<Set<Direction>> getDirectionsByPosteId(Long id) {
		return posteRepository.findById(id).map(Poste::getDirections);
	}
	
	
	
}
