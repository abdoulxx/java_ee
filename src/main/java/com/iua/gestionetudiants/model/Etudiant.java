package com.iua.gestionetudiants.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entité JPA représentant un étudiant
 * Couche Persistance - Modèle de données
 */
@Entity
@Table(name = "etudiants")
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "email", unique = true, length = 150)
    private String email;

    @Column(name = "date_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column(name = "numero_etudiant", unique = true, length = 50)
    private String numeroEtudiant;

    @Column(name = "filiere", length = 100)
    private String filiere;

    // Relation OneToMany avec Note
    // Un étudiant peut avoir plusieurs notes
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Note> notes = new ArrayList<>();

    // Constructeurs
    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public void setNumeroEtudiant(String numeroEtudiant) {
        this.numeroEtudiant = numeroEtudiant;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    // Méthodes utilitaires pour gérer la relation bidirectionnelle
    public void addNote(Note note) {
        notes.add(note);
        note.setEtudiant(this);
    }

    public void removeNote(Note note) {
        notes.remove(note);
        note.setEtudiant(null);
    }

    /**
     * Calcule la moyenne générale de l'étudiant
     * @return la moyenne pondérée par les coefficients
     */
    public double calculerMoyenne() {
        if (notes == null || notes.isEmpty()) {
            return 0.0;
        }

        double sommeNotes = 0.0;
        double sommeCoefficients = 0.0;

        for (Note note : notes) {
            sommeNotes += note.getValeur() * note.getCoefficient();
            sommeCoefficients += note.getCoefficient();
        }

        return sommeCoefficients > 0 ? sommeNotes / sommeCoefficients : 0.0;
    }

    /**
     * Retourne le nom complet de l'étudiant
     * @return nom complet (prénom + nom)
     */
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", numeroEtudiant='" + numeroEtudiant + '\'' +
                ", filiere='" + filiere + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Etudiant)) return false;
        Etudiant etudiant = (Etudiant) o;
        return id != null && id.equals(etudiant.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
