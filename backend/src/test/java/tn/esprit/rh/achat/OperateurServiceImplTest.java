package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import org.junit.jupiter.api.extension.ExtendWith;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperateurServiceImplTest {

    @Mock
    private OperateurRepository operateurRepository;

    @InjectMocks
    private OperateurServiceImpl operateurService;

    private Operateur operateur;

    @BeforeEach
    public void setUp() {
        operateur = new Operateur(1L, "John", "Doe", "password", null);
    }

    @Test
    public void testRetrieveAllOperateurs() {
        List<Operateur> operateurs = Arrays.asList(operateur);
        when(operateurRepository.findAll()).thenReturn(operateurs);

        List<Operateur> result = operateurService.retrieveAllOperateurs();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(operateurRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveOperateur() {
        when(operateurRepository.findById(1L)).thenReturn(Optional.of(operateur));

        Operateur result = operateurService.retrieveOperateur(1L);

        assertNotNull(result);
        assertEquals("John", result.getNom());
        verify(operateurRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddOperateur() {
        when(operateurRepository.save(operateur)).thenReturn(operateur);

        Operateur result = operateurService.addOperateur(operateur);

        assertNotNull(result);
        assertEquals("John", result.getNom());
        verify(operateurRepository, times(1)).save(operateur);
    }

    @Test
    public void testDeleteOperateur() {
        doNothing().when(operateurRepository).deleteById(1L);

        operateurService.deleteOperateur(1L);

        verify(operateurRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateOperateur() {
        when(operateurRepository.save(operateur)).thenReturn(operateur);

        Operateur result = operateurService.updateOperateur(operateur);

        assertNotNull(result);
        assertEquals("John", result.getNom());
        verify(operateurRepository, times(1)).save(operateur);
    }
}
