package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StockServiceImpl implements IStockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;

        
    }

    @Override
    public List<Stock> retrieveAllStocks() {
        log.debug("🔍 Débogage : Exécution de la méthode retrieveAllStocks()");
        log.trace("📍 Entrée dans retrieveAllStocks()");
        log.info("➡️ Récupération de tous les stocks...");

        List<Stock> stocks = stockRepository.findAll();

		for (Stock stock : stocks) {
			log.info("Stock récupéré : {}", stock);
		}
        log.info("⬅️ Fin de retrieveAllStocks()");
        return stocks;
    }

    @Override
    public Stock addStock(Stock s) {
        log.info("➕ Ajout d’un nouveau stock : {}", s);
        Stock saved = stockRepository.save(s);
        log.info("✅ Stock ajouté avec succès : {}", saved);
        return saved;
    }

    @Override
    public void deleteStock(Long stockId) {
        log.info("🗑️ Suppression du stock avec ID : {}", stockId);
        if (stockRepository.existsById(stockId)) {
            stockRepository.deleteById(stockId);
            log.info("✅ Stock supprimé.");
        } else {
            log.warn("⚠️ Aucun stock trouvé avec l’ID : {}", stockId);
        }
    }

    @Override
    public Stock updateStock(Stock s) {
        log.info("✏️ Mise à jour du stock : {}", s);
        if (s.getIdStock() != null && stockRepository.existsById(s.getIdStock())) {
            Stock updated = stockRepository.save(s);
            log.info("✅ Stock mis à jour : {}", updated);
            return updated;
        } else {
            log.warn("⚠️ Impossible de mettre à jour : ID stock inexistant ou nul");
            return null;
        }
    }

    @Override
    public Stock retrieveStock(Long stockId) {
        long start = System.currentTimeMillis();
        log.info("🔍 Récupération du stock avec ID : {}", stockId);

        Stock stock = stockRepository.findById(stockId).orElse(null);

        if (stock != null) {
            log.info("✅ Stock récupéré : {}", stock);
        } else {
            log.warn("⚠️ Stock non trouvé pour ID : {}", stockId);
        }

        long elapsedTime = System.currentTimeMillis() - start;
        log.debug("⏱️ Temps d'exécution : {} ms", elapsedTime);

        return stock;
    }

    @Override
    public String retrieveStatusStock() {
        StringBuilder finalMessage = new StringBuilder();
        String newLine = System.lineSeparator();
        String msgDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

        log.info("🔔 Vérification des stocks en état critique...");

        List<Stock> stocksEnRouge = stockRepository.retrieveStatusStock();

        for (Stock stock : stocksEnRouge) {
            String msg = String.format(
                "%s : le stock %s a une quantité de %d inférieur à la quantité minimale %d",
                msgDate, stock.getLibelleStock(), stock.getQte(), stock.getQteMin()
            );
            finalMessage.append(newLine).append(msg);
            log.warn("🚨 {}", msg);
        }

        return finalMessage.toString();
    }
}
