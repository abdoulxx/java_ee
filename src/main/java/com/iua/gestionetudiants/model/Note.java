package com.iua.gestionetudiants.model;  // Package pour les entités/modèles

import java.io.Serializable;  // Interface pour la sérialisation
import java.util.Date;  // Type Date pour les dates

import javax.persistence.*;  // Annotations JPA

import com.fasterxml.jackson.annotation.JsonIgnore;  // Annotation pour ignorer lors de la sérialisation JSON

/**
 * Entité JPA représentant une note
 * Couche Persistance - Modèle de données
 * Mappée vers la table "notes" en base de données
 */
@Entity  // Indique que c'est une entité JPA
@Table(name = "notes")  // Nom de la table MySQL correspondante
public class Note implements Serializable {  // Serializable permet de sauvegarder/transférer l'objet

    private static final long serialVersionUID = 1L;  // Identifiant de version pour la sérialisation

    @Id  // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrémentation par MySQL
    private Long id;  // Identifiant unique de la note

    @Column(name = "matiere", nullable = false, length = 100)  // Nom de la matière, obligatoire
    private String matiere;  // Ex: "Mathématiques", "Java EE"

    @Column(name = "valeur", nullable = false)  // Valeur de la note, obligatoire
    private Double valeur;  // Note sur 20 (ex: 15.5)

    @Column(name = "coefficient", nullable = false)  // Coefficient, obligatoire
    private Double coefficient;  // Ex: 2.0, 3.0

    @Column(name = "date_evaluation")  // Date de l'évaluation
    @Temporal(TemporalType.DATE)  // Stocke uniquement la DATE (pas l'heure)
    private Date dateEvaluation;  // Date de l'examen/contrôle

    @Column(name = "semestre")  // Numéro du semestre
    private Integer semestre;  // Ex: 1, 2, 7, 8

    @Column(name = "commentaire", length = 500)  // Commentaire optionnel
    private String commentaire;  // Remarques du professeur

    // Relation ManyToOne avec Etudiant
    // Plusieurs notes appartiennent à un étudiant
    @ManyToOne(fetch = FetchType.LAZY)  // Plusieurs notes → 1 étudiant, chargement lazy
    @JoinColumn(name = "etudiant_id", nullable = false)  // Colonne de jointure (clé étrangère)
    @JsonIgnore  // Évite la sérialisation circulaire pour REST (Note → Etudiant → Notes → ...)
    private Etudiant etudiant;  // L'étudiant qui a obtenu cette note

    // Constructeurs
    public Note() {  // Constructeur vide obligatoire pour JPA
    }

    public Note(String matiere, Double valeur, Double coefficient) {  // Constructeur avec paramètres essentiels
        this.matiere = matiere;  // Initialise la matière
        this.valeur = valeur;  // Initialise la note
        this.coefficient = coefficient;  // Initialise le coefficient
    }

    public Note(String matiere, Double valeur, Double coefficient, Etudiant etudiant) {  // Constructeur complet
        this.matiere = matiere;  // Initialise la matière
        this.valeur = valeur;  // Initialise la note
        this.coefficient = coefficient;  // Initialise le coefficient
        this.etudiant = etudiant;  // Associe l'étudiant
    }

    // Getters et Setters - Permettent d'accéder et modifier les attributs privés
    public Long getId() {  // Récupère l'ID
        return id;
    }

    public void setId(Long id) {  // Modifie l'ID
        this.id = id;
    }

    public String getMatiere() {  // Récupère la matière
        return matiere;
    }

    public void setMatiere(String matiere) {  // Modifie la matière
        this.matiere = matiere;
    }

    public Double getValeur() {  // Récupère la note
        return valeur;
    }

    public void setValeur(Double valeur) {  // Modifie la note
        this.valeur = valeur;
    }

    public Double getCoefficient() {  // Récupère le coefficient
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {  // Modifie le coefficient
        this.coefficient = coefficient;
    }

    public Date getDateEvaluation() {  // Récupère la date d'évaluation
        return dateEvaluation;
    }

    public void setDateEvaluation(Date dateEvaluation) {  // Modifie la date d'évaluation
        this.dateEvaluation = dateEvaluation;
    }

    public Integer getSemestre() {  // Récupère le semestre
        return semestre;
    }

    public void setSemestre(Integer semestre) {  // Modifie le semestre
        this.semestre = semestre;
    }

    public String getCommentaire() {  // Récupère le commentaire
        return commentaire;
    }

    public void setCommentaire(String commentaire) {  // Modifie le commentaire
        this.commentaire = commentaire;
    }

    public Etudiant getEtudiant() {  // Récupère l'étudiant associé
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {  // Associe un étudiant à cette note
        this.etudiant = etudiant;
    }

    /**
     * Calcule la note pondérée (valeur × coefficient)
     * Utilisé pour le calcul de la moyenne générale
     * @return la note pondérée
     */
    public double getNotePonderee() {
        return valeur * coefficient;  // Ex: 15 × 2 = 30
    }

    /**
     * Vérifie si la note est une note de passage (>= 10)
     * @return true si la note est >= 10, false sinon
     */
    public boolean estReussie() {
        return valeur >= 10.0;  // Retourne true si >= 10
    }

    /**
     * Retourne l'appréciation selon la note
     * Barème français standard
     * @return l'appréciation textuelle
     */
    public String getAppreciation() {
        if (valeur >= 16.0) {  // Si >= 16
            return "Très Bien";  // Mention Très Bien
        } else if (valeur >= 14.0) {  // Si >= 14
            return "Bien";  // Mention Bien
        } else if (valeur >= 12.0) {  // Si >= 12
            return "Assez Bien";  // Mention Assez Bien
        } else if (valeur >= 10.0) {  // Si >= 10
            return "Passable";  // Admis
        } else {  // Si < 10
            return "Insuffisant";  // Échec
        }
    }

    @Override  // Redéfinit toString() d'Object
    public String toString() {  // Représentation textuelle
        return "Note{" +
                "id=" + id +
                ", matiere='" + matiere + '\'' +
                ", valeur=" + valeur +
                ", coefficient=" + coefficient +
                ", semestre=" + semestre +
                '}';
    }

    @Override  // Redéfinit equals() d'Object
    public boolean equals(Object o) {  // Compare deux notes
        if (this == o) return true;  // Même référence → égaux
        if (!(o instanceof Note)) return false;  // Pas une Note → différents
        Note note = (Note) o;  // Cast en Note
        return id != null && id.equals(note.getId());  // Compare les IDs
    }

    @Override  // Redéfinit hashCode() d'Object
    public int hashCode() {  // Code de hachage
        return getClass().hashCode();  // Hash de la classe
    }
}
