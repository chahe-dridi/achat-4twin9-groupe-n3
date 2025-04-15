package tn.esprit.rh.achat;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import tn.esprit.rh.achat.repositories.ProduitRepository;

public class ProduitRepositoryMock {
    @Bean
    public ProduitRepository produitRepository() {
        return Mockito.mock(ProduitRepository.class);
    }
}