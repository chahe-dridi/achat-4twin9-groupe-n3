package tn.esprit.rh.achat;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.OperateurServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class OperateurServiceImplMockTest {

    @Mock
    private OperateurRepository operateurRepository;  // Mock du repository d'Operateur

    @InjectMocks
    private OperateurServiceImpl operateurService;   // OperateurServiceImpl avec le mock injecté

    private Operateur operateur;

    @BeforeEach
    public void setUp() {
        // Création d'un operateur de test
        operateur = new Operateur();
        operateur.setIdOperateur(1L);
        operateur.setNom("Test");
        operateur.setPrenom("Operateur");
        operateur.setPassword("password123");
    }

    @Test
    @Order(1)
    public void testRetrieveAllOperateurs() {
        List<Operateur> operateurs = Arrays.asList(operateur);
        when(operateurRepository.findAll()).thenReturn(operateurs);  // Lorsque findAll() est appelé, retourner la liste d'operateurs

        List<Operateur> result = operateurService.retrieveAllOperateurs();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getNom());
        verify(operateurRepository, times(1)).findAll();  // Vérifier que findAll() a bien été appelé une fois
    }

    @Test
    @Order(2)
    public void testRetrieveOperateur() {
        when(operateurRepository.findById(1L)).thenReturn(Optional.of(operateur));  // Lorsque findById() est appelé avec l'id 1L, retourner un operateur

        Operateur result = operateurService.retrieveOperateur(1L);

        assertNotNull(result);
        assertEquals("Test", result.getNom());
        verify(operateurRepository, times(1)).findById(1L);  // Vérifier que findById() a bien été appelé une fois
    }

    @Test
    @Order(3)
    public void testAddOperateur() {
        when(operateurRepository.save(operateur)).thenReturn(operateur);  // Lorsque save() est appelé, retourner l'operateur

        Operateur result = operateurService.addOperateur(operateur);

        assertNotNull(result);
        assertEquals("Test", result.getNom());
        verify(operateurRepository, times(1)).save(operateur);  // Vérifier que save() a bien été appelé une fois
    }

    @Test
    @Order(4)
    public void testDeleteOperateur() {
        doNothing().when(operateurRepository).deleteById(1L);  // Lorsque deleteById() est appelé, ne rien faire

        operateurService.deleteOperateur(1L);

        verify(operateurRepository, times(1)).deleteById(1L);  // Vérifier que deleteById() a bien été appelé une fois
    }

    @Test
    @Order(5)
    public void testUpdateOperateur() {
        when(operateurRepository.save(operateur)).thenReturn(operateur);  // Lorsque save() est appelé, retourner l'operateur

        Operateur result = operateurService.updateOperateur(operateur);

        assertNotNull(result);
        assertEquals("Test", result.getNom());
        verify(operateurRepository, times(1)).save(operateur);  // Vérifier que save() a bien été appelé une fois
    }
}
