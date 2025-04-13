package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;

import java.util.List;

@Service
@Slf4j
public class OperateurServiceImpl implements IOperateurService {

	@Autowired
	OperateurRepository operateurRepository;

	@Override
	public List<Operateur> retrieveAllOperateurs() {
		log.info("Récupération de tous les opérateurs.");
		List<Operateur> operateurs = (List<Operateur>) operateurRepository.findAll();
		log.debug("Liste des opérateurs récupérés : {}", operateurs);
		return operateurs;
	}

	@Override
	public Operateur addOperateur(Operateur o) {
		log.info("Ajout de l'opérateur : {}", o);
		Operateur savedOperateur = operateurRepository.save(o);
		log.info("Opérateur ajouté avec succès : {}", savedOperateur);
		return savedOperateur;
	}

	@Override
	public void deleteOperateur(Long id) {
		log.info("Suppression de l'opérateur avec ID : {}", id);
		try {
			operateurRepository.deleteById(id);
			log.info("Opérateur supprimé avec succès.");
		} catch (Exception e) {
			log.error("Erreur lors de la suppression de l'opérateur avec ID : {}", id, e);
		}
	}

	@Override
	public Operateur updateOperateur(Operateur o) {
		log.info("Mise à jour de l'opérateur : {}", o);
		Operateur updatedOperateur = operateurRepository.save(o);
		log.info("Opérateur mis à jour avec succès : {}", updatedOperateur);
		return updatedOperateur;
	}

	@Override
	public Operateur retrieveOperateur(Long id) {
		log.info("Récupération de l'opérateur avec ID : {}", id);
		Operateur operateur = operateurRepository.findById(id).orElse(null);
		if (operateur != null) {
			log.info("Opérateur récupéré : {}", operateur);
		} else {
			log.warn("Opérateur avec ID {} non trouvé.", id);
		}
		return operateur;
	}
}
