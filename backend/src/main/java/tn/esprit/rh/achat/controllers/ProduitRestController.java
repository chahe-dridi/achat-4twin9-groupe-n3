package tn.esprit.rh.achat.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.services.IProduitService;

import java.util.List;

@RestController
@CrossOrigin("*")
@Api(tags = "Gestion des produits")
@RequestMapping("/produit")
public class ProduitRestController {

    private final IProduitService produitService;

    @Autowired
    public ProduitRestController(IProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/retrieve-all-produits")
    public List<Produit> getProduits() {
        return produitService.retrieveAllProduits();
    }

    @GetMapping("/retrieve-produit/{produit-id}")
    public Produit retrieveRayon(@PathVariable("produit-id") Long produitId) {
        return produitService.retrieveProduit(produitId);
    }

    @PostMapping("/add-produit")
    public Produit addProduit(@RequestBody Produit p) {
        return produitService.addProduit(p);
    }

    @DeleteMapping("/remove-produit/{produit-id}")
    public void removeProduit(@PathVariable("produit-id") Long produitId) {
        produitService.deleteProduit(produitId);
    }

    @PutMapping("/modify-produit")
    public Produit modifyProduit(@RequestBody Produit p) {
        return produitService.updateProduit(p);
    }

    @PutMapping(value = "/assignProduitToStock/{idProduit}/{idStock}")
    public void assignProduitToStock(@PathVariable("idProduit") Long idProduit, @PathVariable("idStock") Long idStock) {
        produitService.assignProduitToStock(idProduit, idStock);
    }

    // Uncomment and implement later if needed
    /*
    @GetMapping(value = "/getRevenuBrutProduit/{idProduit}/{startDate}/{endDate}")
    public float getRevenuBrutProduit(@PathVariable("idProduit") Long idProduit,
            @PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return produitService.getRevenuBrutProduit(idProduit, startDate, endDate);
    }
    */
}
