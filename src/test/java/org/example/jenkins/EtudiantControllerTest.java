package org.example.jenkins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantControllerTest {

    @Mock
    private EtudiantService etudiantService;

    @InjectMocks
    private EtudiantController etudiantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ajouterEtudiant_ShouldReturnCreatedEtudiant() {
        // Arrange
        String nom = "Doe";
        String prenom = "John";
        EtudiantService.Etudiant etudiant = new EtudiantService.Etudiant(1L, nom, prenom);
        when(etudiantService.ajouterEtudiant(nom, prenom)).thenReturn(etudiant);

        // Act
        ResponseEntity<EtudiantService.Etudiant> response = etudiantController.ajouterEtudiant(nom, prenom);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(etudiant, response.getBody());
        verify(etudiantService, times(1)).ajouterEtudiant(nom, prenom);
    }

    @Test
    void supprimerEtudiant_ShouldReturnNoContent() {
        // Arrange
        Long id = 1L;
        doNothing().when(etudiantService).supprimerEtudiant(id);

        // Act
        ResponseEntity<Void> response = etudiantController.supprimerEtudiant(id);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(etudiantService, times(1)).supprimerEtudiant(id);
    }

    @Test
    void getAllEtudiants_ShouldReturnListOfEtudiants() {
        // Arrange
        List<EtudiantService.Etudiant> etudiants = Arrays.asList(
                new EtudiantService.Etudiant(1L, "Doe", "John"),
                new EtudiantService.Etudiant(2L, "Smith", "Jane")
        );
        when(etudiantService.getAllEtudiants()).thenReturn(etudiants);

        // Act
        ResponseEntity<List<EtudiantService.Etudiant>> response = etudiantController.getAllEtudiants();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(etudiants, response.getBody());
        verify(etudiantService, times(1)).getAllEtudiants();
    }
}
