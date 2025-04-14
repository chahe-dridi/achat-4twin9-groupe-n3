package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.SecteurActiviteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecteurActiviteServiceImplTest {

    @Mock
    private SecteurActiviteRepository secteurActiviteRepository;

    @InjectMocks
    private SecteurActiviteServiceImpl secteurActiviteService;

    private SecteurActivite secteur;

    @BeforeEach
    public void setUp() {
        secteur = new SecteurActivite();
        secteur.setIdSecteurActivite(1L);
        secteur.setCodeSecteurActivite("SA001");
        secteur.setLibelleSecteurActivite("Secteur Test");
    }

    @Test
    public void testRetrieveAllSecteurActivite() {
        List<SecteurActivite> secteurs = Arrays.asList(secteur);
        when(secteurActiviteRepository.findAll()).thenReturn(secteurs);

        List<SecteurActivite> result = secteurActiviteService.retrieveAllSecteurActivite();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("SA001", result.get(0).getCodeSecteurActivite());
        verify(secteurActiviteRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveSecteurActivite() {
        when(secteurActiviteRepository.findById(1L)).thenReturn(Optional.of(secteur));

        SecteurActivite result = secteurActiviteService.retrieveSecteurActivite(1L);

        assertNotNull(result);
        assertEquals("Secteur Test", result.getLibelleSecteurActivite());
        verify(secteurActiviteRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveSecteurActiviteNotFound() {
        when(secteurActiviteRepository.findById(1L)).thenReturn(Optional.empty());

        SecteurActivite result = secteurActiviteService.retrieveSecteurActivite(1L);

        assertNull(result);
        verify(secteurActiviteRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddSecteurActivite() {
        when(secteurActiviteRepository.save(secteur)).thenReturn(secteur);

        SecteurActivite result = secteurActiviteService.addSecteurActivite(secteur);

        assertNotNull(result);
        assertEquals("Secteur Test", result.getLibelleSecteurActivite());
        verify(secteurActiviteRepository, times(1)).save(secteur);
    }

    @Test
    public void testUpdateSecteurActivite() {
        when(secteurActiviteRepository.save(secteur)).thenReturn(secteur);

        SecteurActivite result = secteurActiviteService.updateSecteurActivite(secteur);

        assertNotNull(result);
        assertEquals("SA001", result.getCodeSecteurActivite());
        verify(secteurActiviteRepository, times(1)).save(secteur);
    }

    @Test
    public void testDeleteSecteurActivite() {
        doNothing().when(secteurActiviteRepository).deleteById(1L);

        secteurActiviteService.deleteSecteurActivite(1L);

        verify(secteurActiviteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSecteurActiviteWithException() {
        doThrow(new RuntimeException("Delete failed")).when(secteurActiviteRepository).deleteById(1L);

        secteurActiviteService.deleteSecteurActivite(1L); // Should not throw due to try-catch in service

        verify(secteurActiviteRepository, times(1)).deleteById(1L);
    }
}