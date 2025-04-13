package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ProduitServiceImpl implements IProduitService {

	private final ProduitRepository produitRepository;
	private final StockRepository stockRepository;
	private final CategorieProduitRepository categorieProduitRepository;

	@Autowired
	public ProduitServiceImpl(ProduitRepository produitRepository,
							  StockRepository stockRepository,
							  CategorieProduitRepository categorieProduitRepository) {
		this.produitRepository = produitRepository;
		this.stockRepository = stockRepository;
		this.categorieProduitRepository = categorieProduitRepository;

		log.info("✅ ProduitServiceImpl initialisé !");
	}
	@Override
	public List<Produit> retrieveAllProduits() {
		log.debug("🔍 Débogage : Exécution de la méthode retrieveAllProduits()");
		log.trace("📍 Entrée dans la méthode retrieveAllProduits()");
		log.info("Récupération de tous les produits.");
		List<Produit> produits = (List<Produit>) produitRepository.findAll();
		for (Produit produit : produits) {
			log.info("Produit récupéré : {}", produit);
		}
		return produits;
	}

	@Transactional
	public Produit addProduit(Produit p) {
		log.info("Ajout du produit : {}", p);
		log.info("Ajout du produit : id={}", p.getIdProduit());

		Produit savedProduit = produitRepository.save(p);
		log.info("Produit ajouté avec succès : {}", savedProduit);
		return savedProduit;
	}

	@Override
	public void deleteProduit(Long produitId) {
		log.info("Suppression du produit avec ID : {}", produitId);
		try {
			produitRepository.deleteById(produitId);
			log.info("Produit supprimé avec succès.");
		} catch (Exception e) {
			log.error("Erreur lors de la suppression du produit avec ID : {}", produitId, e);
		}
	}

	@Override
	public Produit updateProduit(Produit p) {
		log.info("Mise à jour du produit : {}", p);
		Produit updatedProduit = produitRepository.save(p);
		log.info("Produit mis à jour avec succès : {}", updatedProduit);
		return updatedProduit;
	}

	@Override
	public Produit retrieveProduit(Long produitId) {
		log.info("Récupération du produit avec ID : {}", produitId);
		Produit produit = produitRepository.findById(produitId).orElse(null);
		if (produit != null) {
			log.info("Produit récupéré : {}", produit);
		} else {
			log.warn("Produit non trouvé avec ID : {}", produitId);
		}
		return produit;
	}

	@Override
	public void assignProduitToStock(Long idProduit, Long idStock) {
		log.info("Assignation du produit ID : {} au stock ID : {}", idProduit, idStock);
		try {
			Produit produit = produitRepository.findById(idProduit).orElse(null);
			Stock stock = stockRepository.findById(idStock).orElse(null);

			if (produit != null && stock != null) {
				produit.setStock(stock);
				produitRepository.save(produit);
				log.info("Produit ID : {} assigné au stock ID : {}", idProduit, idStock);
			} else {
				log.warn("Produit ou stock non trouvé. Produit ID : {} - Stock ID : {}", idProduit, idStock);
			}
		} catch (Exception e) {
			log.error("Erreur lors de l'assignation du produit au stock", e);
		}
	}
}