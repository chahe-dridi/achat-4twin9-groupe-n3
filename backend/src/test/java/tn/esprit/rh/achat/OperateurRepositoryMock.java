package tn.esprit.rh.achat;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import tn.esprit.rh.achat.repositories.OperateurRepository;

public class OperateurRepositoryMock {

    public OperateurRepository operateurRepository() {
        return Mockito.mock(OperateurRepository.class);
    }
}
