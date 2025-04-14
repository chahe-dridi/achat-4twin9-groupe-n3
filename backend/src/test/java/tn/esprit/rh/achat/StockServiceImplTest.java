package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private Stock stock;

    @BeforeEach
    public void setUp() {
        stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("Stock Test");
        stock.setQte(100);
        stock.setQteMin(10);
    }

    @Test
    public void testRetrieveAllStocks() {
        List<Stock> stocks = Arrays.asList(stock);
        when(stockRepository.findAll()).thenReturn(stocks);
        List<Stock> result = stockService.retrieveAllStocks();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    public void testAddStock() {
        when(stockRepository.save(stock)).thenReturn(stock);
        Stock result = stockService.addStock(stock);
        assertNotNull(result);
        assertEquals("Stock Test", result.getLibelleStock());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void testDeleteStock() {
        when(stockRepository.existsById(1L)).thenReturn(true);
        doNothing().when(stockRepository).deleteById(1L);
        stockService.deleteStock(1L);
        verify(stockRepository, times(1)).deleteById(1L);
    }
    

    @Test
    public void testUpdateStock() {
        when(stockRepository.existsById(1L)).thenReturn(true);
        when(stockRepository.save(stock)).thenReturn(stock);   
        Stock result = stockService.updateStock(stock);   
        assertNotNull(result);  // Vérifie que le résultat n'est pas nul
        assertEquals("Stock Test", result.getLibelleStock());  // Vérifie les valeurs
        verify(stockRepository, times(1)).save(stock);
    }
    

    @Test
    public void testRetrieveStock() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        Stock result = stockService.retrieveStock(1L);
        assertNotNull(result);
        assertEquals("Stock Test", result.getLibelleStock());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveStatusStock() {
        List<Stock> stocks = Arrays.asList(stock);
        when(stockRepository.retrieveStatusStock()).thenReturn(stocks);
        String result = stockService.retrieveStatusStock();
        assertNotNull(result);
        assertTrue(result.contains("Stock Test"));
        verify(stockRepository, times(1)).retrieveStatusStock();
    }
}
