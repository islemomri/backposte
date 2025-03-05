package com.project.app.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.app.model.Site;
import com.project.app.repository.DirectionRepository;
import com.project.app.repository.PosteRepository;
import com.project.app.repository.SiteRepository;


@Service
public class SiteService implements SiteImpl {
	@Autowired
    private SiteRepository siteRepository;
	@Autowired
    private DirectionRepository directionRepository;

	@Autowired
    private PosteRepository posteRepository;

	@Override
	public Site ajouterSite(Site site) {
		 return siteRepository.save(site);
	}


	@Override
	public List<Site> getAllSites() {
		return siteRepository.findAll();
	}


	
	/*@Override
	public Site updateSite(Long id, Site siteDetails) {
		Optional<Site> siteOpt = siteRepository.findById(id);
        if (siteOpt.isPresent()) {
            Site site = siteOpt.get();
            site.setNom_site(siteDetails.getNom_site());  
            return siteRepository.save(site);
        } else {
            return null; 
        }
	}
*/
	  /*@Override
	public Site updateSite(Long id, SiteRequest updatedSiteDTO) throws Exception {
        // Recherche du site par son ID
      Site site = siteRepository.findById(id).orElseThrow(() -> new RuntimeException("Site non trouvé"));
        
        // Mise à jour du nom du site
        site.setNom_site(updatedSiteDTO.getNom_site());

        // Récupération des directions par leurs IDs
        Set<Long> directionIds = updatedSiteDTO.getDirectionIds();
        Set<Direction> directions = new HashSet<>(directionRepository.findAllById(directionIds));

        // Associer les directions récupérées au site
        site.setDirections(directions);
        
        
        
        Set<Long> posteIds = updatedSiteDTO.getPostesIds();
        Set<Poste> postes = new HashSet<>(posteRepository.findAllById(posteIds));
        site.setPostes(postes);
        
        // Sauvegarde et renvoi du site mis à jour
        return siteRepository.save(site);
    }*/
	@Override
	public boolean deleteSite(Long id) {
		 Optional<Site> siteOpt = siteRepository.findById(id);
	        if (siteOpt.isPresent()) {
	            siteRepository.deleteById(id);
	            return true;
	        } else {
	            return false;
	        }
	}


	@Override
	public Site archiverSite(Long id) {
		 Site site = siteRepository.findById(id).orElseThrow(() -> new RuntimeException("Site not found"));
	        site.archiver(); 
	        return siteRepository.save(site);
	}


	@Override
	public List<Site> getAllSitesArchivés() {
		return siteRepository.findByArchiveTrue();
	}


	@Override
	public List<Site> getAllSitesnonArchivés() {
		return siteRepository.findByArchiveFalse();
	}


	@Override
	public Site desarchiverSite(Long id) {
		Site site = siteRepository.findById(id).orElseThrow(() -> new RuntimeException("site non trouvée"));
    site.desarchiver();
    return siteRepository.save(site);
	}
	
	/*@Override
	@Transactional
    public Site addSiteWithDirectionsAndPostes(Site site, Set<Long> directionIds, Set<Long> posteIds) {
        Set<Direction> directions = new HashSet<>(directionRepository.findAllById(directionIds));
        site.setDirections(directions);
        
        Set<Poste> postes = new HashSet<>(posteRepository.findAllById(posteIds));
        site.setPostes(postes);
        return siteRepository.save(site);
    }
	
	@Override
	public List<Poste> getPostesBySiteId(Long siteId) {
        Optional<Site> siteOpt = siteRepository.findById(siteId);
        if (siteOpt.isPresent()) {
            Site site = siteOpt.get();
            return List.copyOf(site.getPostes());
        }
        return null;  // Retourner null ou gérer une exception si le site n'est pas trouvé
    }*/
	
	

}
