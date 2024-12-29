package org.example.jenkins;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EtudiantService {
    private final List<Etudiant> etudiants = new CopyOnWriteArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public Etudiant ajouterEtudiant(String nom, String prenom) {
        Etudiant etudiant = new Etudiant(counter.incrementAndGet(), nom, prenom);
        etudiants.add(etudiant);
        return etudiant;
    }

    public void supprimerEtudiant(Long id) {
        boolean removed = etudiants.removeIf(etudiant -> etudiant.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("Etudiant avec l'ID " + id + " n'existe pas.");
        }
    }

    public List<Etudiant> getAllEtudiants() {
        return Collections.unmodifiableList(etudiants); // Empêche les modifications accidentelles.
    }

    public static class Etudiant {
        private final Long id;
        private final String nom;
        private final String prenom;

        public Etudiant(Long id, String nom, String prenom) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
        }

        public Long getId() {
            return id;
        }

        public String getNom() {
            return nom;
        }

        public String getPrenom() {
            return prenom;
        }
    }
}
