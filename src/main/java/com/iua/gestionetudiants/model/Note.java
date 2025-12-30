package com.iua.gestionetudiants.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entité JPA représentant une note
 * Couche Persistance - Modèle de données
 */
@Entity
@Table(name = "notes")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matiere", nullable = false, length = 100)
    private String matiere;

    @Column(name = "valeur", nullable = false)
    private Double valeur;

    @Column(name = "coefficient", nullable = false)
    private Double coefficient;

    @Column(name = "date_evaluation")
    @Temporal(TemporalType.DATE)
    private Date dateEvaluation;

    @Column(name = "semestre")
    private Integer semestre;

    @Column(name = "commentaire", length = 500)
    private String commentaire;

    // Relation ManyToOne avec Etudiant
    // Plusieurs notes appartiennent à un étudiant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonIgnore  // Évite la sérialisation circulaire pour REST
    private Etudiant etudiant;

    // Constructeurs
    public Note() {
    }

    public Note(String matiere, Double valeur, Double coefficient) {
        this.matiere = matiere;
        this.valeur = valeur;
        this.coefficient = coefficient;
    }

    public Note(String matiere, Double valeur, Double coefficient, Etudiant etudiant) {
        this.matiere = matiere;
        this.valeur = valeur;
        this.coefficient = coefficient;
        this.etudiant = etudiant;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public Date getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(Date dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    /**
     * Calcule la note pondérée (valeur * coefficient)
     * @return la note pondérée
     */
    public double getNotePonderee() {
        return valeur * coefficient;
    }

    /**
     * Vérifie si la note est une note de passage (>= 10)
     * @return true si la note est >= 10
     */
    public boolean estReussie() {
        return valeur >= 10.0;
    }

    /**
     * Retourne l'appréciation selon la note
     * @return l'appréciation
     */
    public String getAppreciation() {
        if (valeur >= 16.0) {
            return "Très Bien";
        } else if (valeur >= 14.0) {
            return "Bien";
        } else if (valeur >= 12.0) {
            return "Assez Bien";
        } else if (valeur >= 10.0) {
            return "Passable";
        } else {
            return "Insuffisant";
        }
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", matiere='" + matiere + '\'' +
                ", valeur=" + valeur +
                ", coefficient=" + coefficient +
                ", semestre=" + semestre +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return id != null && id.equals(note.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
