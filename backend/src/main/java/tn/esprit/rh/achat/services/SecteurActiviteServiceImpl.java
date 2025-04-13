package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class SecteurActiviteServiceImpl implements ISecteurActiviteService {

	private final SecteurActiviteRepository secteurActiviteRepository;

	@Autowired
	public SecteurActiviteServiceImpl(SecteurActiviteRepository secteurActiviteRepository) {
		this.secteurActiviteRepository = secteurActiviteRepository;
		log.info("✅ SecteurActiviteServiceImpl initialized!");
	}

	@Override
	public List<SecteurActivite> retrieveAllSecteurActivite() {
		log.debug("🔍 Starting retrieveAllSecteurActivite");
		log.info("Retrieving all SecteurActivite entities");
		List<SecteurActivite> secteursActivite = (List<SecteurActivite>) secteurActiviteRepository.findAll();
		log.info("Retrieved {} SecteurActivite entities", secteursActivite.size());
		secteursActivite.forEach(secteur -> log.info("SecteurActivite: {}", secteur));
		log.debug("🔍 Finished retrieveAllSecteurActivite");
		return secteursActivite;
	}

	@Override
	@Transactional
	public SecteurActivite addSecteurActivite(SecteurActivite sa) {
		log.info("Adding new SecteurActivite: {}", sa);
		SecteurActivite saved = secteurActiviteRepository.save(sa);
		log.info("SecteurActivite added successfully: {}", saved);
		return saved;
	}

	@Override
	public void deleteSecteurActivite(Long id) {
		log.info("Deleting SecteurActivite with ID: {}", id);
		try {
			secteurActiviteRepository.deleteById(id);
			log.info("SecteurActivite with ID {} deleted successfully", id);
		} catch (Exception e) {
			log.error("Error deleting SecteurActivite with ID: {}", id, e);
		}
	}

	@Override
	@Transactional
	public SecteurActivite updateSecteurActivite(SecteurActivite sa) {
		log.info("Updating SecteurActivite: {}", sa);
		SecteurActivite updated = secteurActiviteRepository.save(sa);
		log.info("SecteurActivite updated successfully: {}", updated);
		return updated;
	}

	@Override
	public SecteurActivite retrieveSecteurActivite(Long id) {
		log.info("Retrieving SecteurActivite with ID: {}", id);
		SecteurActivite secteurActivite = secteurActiviteRepository.findById(id).orElse(null);
		if (secteurActivite != null) {
			log.info("SecteurActivite retrieved: {}", secteurActivite);
		} else {
			log.warn("SecteurActivite with ID {} not found", id);
		}
		return secteurActivite;
	}
}