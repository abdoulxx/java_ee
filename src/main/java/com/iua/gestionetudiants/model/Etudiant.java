package com.iua.gestionetudiants.model; // Déclare le package où se trouve cette classe

import java.io.Serializable;  // Interface pour sérialiser l'objet (convertir en bytes)
import java.util.ArrayList;   // Liste dynamique pour stocker les notes
import java.util.Date;        // Type pour représenter une date
import java.util.List;        // Interface pour manipuler des collections

import javax.persistence.*;   // Import toutes les annotations JPA (Entity, Table, Column, etc.)

/**
 * Entité JPA représentant un étudiant
 * Couche Persistance - Modèle de données
 * Mappée automatiquement vers la table "etudiants" en base de données
 */
@Entity  // Indique que cette classe est une entité JPA (table en base de données)
@Table(name = "etudiants")  // Spécifie le nom de la table MySQL correspondante
public class Etudiant implements Serializable {  // Serializable permet de sauvegarder/transférer l'objet

    private static final long serialVersionUID = 1L;  // Identifiant de version pour la sérialisation

    @Id  // Indique que c'est la clé primaire (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrémentation par MySQL
    private Long id;  // Identifiant unique de l'étudiant

    @Column(name = "nom", nullable = false, length = 100)  // Colonne "nom", obligatoire, max 100 caractères
    private String nom;  // Nom de famille

    @Column(name = "prenom", nullable = false, length = 100)  // Colonne "prenom", obligatoire
    private String prenom;  // Prénom de l'étudiant

    @Column(name = "email", unique = true, length = 150)  // Email unique, pas de doublons
    private String email;  // Adresse email

    @Column(name = "date_naissance")  // Colonne pour la date de naissance
    @Temporal(TemporalType.DATE)  // Stocke uniquement la DATE (sans l'heure)
    private Date dateNaissance;  // Date de naissance

    @Column(name = "numero_etudiant", unique = true, length = 50)  // Numéro unique
    private String numeroEtudiant;  // Matricule (ex: ETU001)

    @Column(name = "filiere", length = 100)  // Filière d'études
    private String filiere;  // GI ou MIAGE

    // Relation OneToMany avec Note
    // Un étudiant peut avoir plusieurs notes
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)  // 1 étudiant → plusieurs notes
    @com.fasterxml.jackson.annotation.JsonIgnore  // Ignore lors de la sérialisation JSON (évite boucle infinie)
    private List<Note> notes = new ArrayList<>();  // Liste des notes de cet étudiant

    // Constructeurs
    public Etudiant() {  // Constructeur vide obligatoire pour JPA
    }

    public Etudiant(String nom, String prenom, String email) {  // Constructeur avec paramètres
        this.nom = nom;  // Initialise le nom
        this.prenom = prenom;  // Initialise le prénom
        this.email = email;  // Initialise l'email
    }

    // Getters et Setters - Permettent d'accéder et modifier les attributs privés
    public Long getId() {  // Récupère l'ID
        return id;
    }

    public void setId(Long id) {  // Modifie l'ID
        this.id = id;
    }

    public String getNom() {  // Récupère le nom
        return nom;
    }

    public void setNom(String nom) {  // Modifie le nom
        this.nom = nom;
    }

    public String getPrenom() {  // Récupère le prénom
        return prenom;
    }

    public void setPrenom(String prenom) {  // Modifie le prénom
        this.prenom = prenom;
    }

    public String getEmail() {  // Récupère l'email
        return email;
    }

    public void setEmail(String email) {  // Modifie l'email
        this.email = email;
    }

    public Date getDateNaissance() {  // Récupère la date de naissance
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {  // Modifie la date de naissance
        this.dateNaissance = dateNaissance;
    }

    public String getNumeroEtudiant() {  // Récupère le numéro étudiant
        return numeroEtudiant;
    }

    public void setNumeroEtudiant(String numeroEtudiant) {  // Modifie le numéro étudiant
        this.numeroEtudiant = numeroEtudiant;
    }

    public String getFiliere() {  // Récupère la filière
        return filiere;
    }

    public void setFiliere(String filiere) {  // Modifie la filière
        this.filiere = filiere;
    }

    public List<Note> getNotes() {  // Récupère la liste des notes
        return notes;
    }

    public void setNotes(List<Note> notes) {  // Remplace toute la liste de notes
        this.notes = notes;
    }

    // Méthodes utilitaires pour gérer la relation bidirectionnelle
    public void addNote(Note note) {  // Ajoute une note à l'étudiant
        notes.add(note);  // Ajoute la note à la liste
        note.setEtudiant(this);  // Définit cet étudiant comme propriétaire
    }

    public void removeNote(Note note) {  // Retire une note de l'étudiant
        notes.remove(note);  // Supprime de la liste
        note.setEtudiant(null);  // Supprime le lien
    }

    /**
     * Calcule la moyenne générale de l'étudiant
     * Formule: Somme(note × coefficient) / Somme(coefficients)
     * @return la moyenne pondérée par les coefficients
     */
    public double calculerMoyenne() {
        if (notes == null || notes.isEmpty()) {  // Si pas de notes
            return 0.0;  // Retourne 0
        }

        double sommeNotes = 0.0;  // Somme des (note × coef)
        double sommeCoefficients = 0.0;  // Somme des coefficients

        for (Note note : notes) {  // Parcourt toutes les notes
            sommeNotes += note.getValeur() * note.getCoefficient();  // Calcule note × coef
            sommeCoefficients += note.getCoefficient();  // Additionne les coefficients
        }

        return sommeCoefficients > 0 ? sommeNotes / sommeCoefficients : 0.0;  // Calcule la moyenne
    }

    /**
     * Retourne le nom complet de l'étudiant
     * @return nom complet (prénom + nom)
     */
    public String getNomComplet() {  // Concatène prénom et nom
        return prenom + " " + nom;  // Ex: "Jean DUPONT"
    }

    @Override  // Redéfinit la méthode toString() héritée d'Object
    public String toString() {  // Représentation textuelle de l'objet
        return "Etudiant{" +  // Retourne une chaîne formatée
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", numeroEtudiant='" + numeroEtudiant + '\'' +
                ", filiere='" + filiere + '\'' +
                '}';
    }

    @Override  // Redéfinit la méthode equals() héritée d'Object
    public boolean equals(Object o) {  // Compare deux objets Etudiant
        if (this == o) return true;  // Même référence mémoire → égaux
        if (!(o instanceof Etudiant)) return false;  // Pas un Etudiant → différents
        Etudiant etudiant = (Etudiant) o;  // Cast en Etudiant
        return id != null && id.equals(etudiant.getId());  // Compare les IDs
    }

    @Override  // Redéfinit la méthode hashCode() héritée d'Object
    public int hashCode() {  // Code de hachage pour collections
        return getClass().hashCode();  // Retourne le hash de la classe
    }
}
