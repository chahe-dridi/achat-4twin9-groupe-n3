package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.ProduitServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ProduitServiceImplTest {
    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProduitServiceImpl produitService;

    private Produit produit;
    private Stock stock;

    @BeforeEach
    public void setUp() {
        produit = new Produit();
        produit.setIdProduit(1L);
        produit.setCodeProduit("P001");
        produit.setLibelleProduit("Produit Test");
        produit.setPrix(100.0f);
        produit.setDateCreation(new Date());
        produit.setDateDerniereModification(new Date());

        stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("Stock Test");
    }

    @Test
    public void testRetrieveAllProduits() {
        List<Produit> produits = Arrays.asList(produit);
        when(produitRepository.findAll()).thenReturn(produits);

        List<Produit> result = produitService.retrieveAllProduits();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(produitRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveProduit() {
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));

        Produit result = produitService.retrieveProduit(1L);

        assertNotNull(result);
        assertEquals("Produit Test", result.getLibelleProduit());
        verify(produitRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddProduit() {
        when(produitRepository.save(produit)).thenReturn(produit);

        Produit result = produitService.addProduit(produit);

        assertNotNull(result);
        assertEquals("Produit Test", result.getLibelleProduit());
        verify(produitRepository, times(1)).save(produit);
    }

    @Test
    public void testDeleteProduit() {
        doNothing().when(produitRepository).deleteById(1L);

        produitService.deleteProduit(1L);

        verify(produitRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateProduit() {
        when(produitRepository.save(produit)).thenReturn(produit);

        Produit result = produitService.updateProduit(produit);

        assertNotNull(result);
        assertEquals("Produit Test", result.getLibelleProduit());
        verify(produitRepository, times(1)).save(produit);
    }


}