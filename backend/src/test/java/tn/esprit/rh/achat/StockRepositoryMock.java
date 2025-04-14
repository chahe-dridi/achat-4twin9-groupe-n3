package tn.esprit.rh.achat;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import tn.esprit.rh.achat.repositories.StockRepository;

public class StockRepositoryMock {
    @Bean
    public StockRepository stockRepository() {
        return Mockito.mock(StockRepository.class);
    }
}
