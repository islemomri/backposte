package com.project.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.app.dto.DirectionDTO;
import com.project.app.model.Direction;
import com.project.app.model.Site;
import com.project.app.repository.DirectionRepository;
import com.project.app.repository.SiteRepository;





@Service
public class DirectionService implements IDirection {
	
	@Autowired
    private DirectionRepository directionRepository;

	@Autowired
    private SiteRepository siteRepository ;

	@Override
	public Direction ajouterDirection(Direction direction) {
		return directionRepository.save(direction);
	}

	@Override
	public List<Direction> getAllDirectionsnonArchivés() {
		return directionRepository.findByArchiveFalse();
	}
	
	@Override
	public List<Direction> getAllDirectionsArchivés() {
		return directionRepository.findByArchiveTrue();
	}

	@Override
	public Direction archiverDirection(Long id) {
		 Direction direction = directionRepository.findById(id).orElseThrow(() -> new RuntimeException("Direction not found"));
	        direction.archiver(); 
	        return directionRepository.save(direction);
	}
	
	
	public Direction desarchiverDirection(Long id) {
        Direction direction = directionRepository.findById(id).orElseThrow(() -> new RuntimeException("Direction non trouvée"));
        direction.desarchiver();
        return directionRepository.save(direction);
    }
	
	
	@Override
	public Direction updateDirection(Long id, Direction Details) {
		Optional<Direction> directionOpt = directionRepository.findById(id);
        if (directionOpt.isPresent()) {
        	Direction Direction = directionOpt.get();
        	
        	Direction.setNom_direction(Details.getNom_direction()); 
          
            return directionRepository.save(Direction);
        } else {
            return null; 
        }
	}
	
	 public Direction createDirectionWithSites(DirectionDTO directionDTO) {
	        Direction direction = new Direction();
	        direction.setNom_direction(directionDTO.getNom_direction());

	        Set<Site> sites = new HashSet<>();
	        for (Long siteId : directionDTO.getSiteIds()) {
	            Optional<Site> siteOpt = siteRepository.findById(siteId);
	            siteOpt.ifPresent(sites::add);
	        }

	        direction.setSites(sites);
	        return directionRepository.save(direction);
	    }

    // Récupérer les sites associés à une direction
    public Set<Site> getSitesByDirection(Long directionId) {
        return directionRepository.findById(directionId).map(Direction::getSites).orElse(null);
    }
    
    public Direction updateDirection(Long directionId, DirectionDTO directionDTO) {
        Optional<Direction> existingDirectionOpt = directionRepository.findById(directionId);
        
        if (existingDirectionOpt.isPresent()) {
            Direction direction = existingDirectionOpt.get();
            direction.setNom_direction(directionDTO.getNom_direction());

            // Charger les sites à partir des IDs fournis
            Set<Site> sites = new HashSet<>();
            for (Long siteId : directionDTO.getSiteIds()) {
                Optional<Site> siteOpt = siteRepository.findById(siteId);
                siteOpt.ifPresent(sites::add);
            }

            // Mise à jour des sites liés
            direction.setSites(sites);
            return directionRepository.save(direction);
        } else {
            throw new RuntimeException("Direction avec l'ID " + directionId + " non trouvée.");
        }
    }
    
    
    
    

	/*@Override
	 public Set<Direction> getDirectionsBySiteId(Long siteId) {
	        Site site = siteRepository.findById(siteId)
	                .orElseThrow(() -> new RuntimeException("Site not found"));
	        return site.getDirections();
	    }*/
	
	
}
