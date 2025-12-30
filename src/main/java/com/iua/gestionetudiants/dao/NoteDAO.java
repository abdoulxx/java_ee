package com.iua.gestionetudiants.dao;

import com.iua.gestionetudiants.model.Etudiant;
import com.iua.gestionetudiants.model.Note;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO pour la gestion de la persistance des notes
 * Couche Persistance - Accès aux données
 */
public class NoteDAO extends GenericDAO<Note> {

    public NoteDAO() {
        super(Note.class);
    }

    /**
     * Récupérer toutes les notes d'un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @return liste des notes de l'étudiant
     */
    public List<Note> findByEtudiant(Long etudiantId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId ORDER BY n.dateEvaluation DESC, n.matiere";
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);
            query.setParameter("etudiantId", etudiantId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer les notes d'un étudiant pour une matière donnée
     * @param etudiantId l'identifiant de l'étudiant
     * @param matiere la matière
     * @return liste des notes de l'étudiant pour cette matière
     */
    public List<Note> findByEtudiantAndMatiere(Long etudiantId, String matiere) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.matiere = :matiere ORDER BY n.dateEvaluation DESC";
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);
            query.setParameter("etudiantId", etudiantId);
            query.setParameter("matiere", matiere);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer toutes les notes d'une matière
     * @param matiere la matière
     * @return liste des notes pour cette matière
     */
    public List<Note> findByMatiere(String matiere) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT n FROM Note n WHERE n.matiere = :matiere ORDER BY n.valeur DESC";
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);
            query.setParameter("matiere", matiere);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer les notes d'un étudiant pour un semestre donné
     * @param etudiantId l'identifiant de l'étudiant
     * @param semestre le numéro du semestre
     * @return liste des notes du semestre
     */
    public List<Note> findByEtudiantAndSemestre(Long etudiantId, Integer semestre) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.semestre = :semestre ORDER BY n.matiere";
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);
            query.setParameter("etudiantId", etudiantId);
            query.setParameter("semestre", semestre);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer toutes les notes supérieures ou égales à une valeur
     * @param valeurMin la valeur minimale
     * @return liste des notes >= valeurMin
     */
    public List<Note> findByValeurSuperieure(double valeurMin) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT n FROM Note n WHERE n.valeur >= :valeurMin ORDER BY n.valeur DESC";
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);
            query.setParameter("valeurMin", valeurMin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer toutes les notes inférieures à une valeur (notes insuffisantes)
     * @param valeurMax la valeur maximale
     * @return liste des notes < valeurMax
     */
    public List<Note> findByValeurInferieure(double valeurMax) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT n FROM Note n WHERE n.valeur < :valeurMax ORDER BY n.valeur ASC";
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);
            query.setParameter("valeurMax", valeurMax);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Calculer la moyenne d'un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @return la moyenne de l'étudiant
     */
    public double calculerMoyenne(Long etudiantId) {
        List<Note> notes = findByEtudiant(etudiantId);
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
     * Calculer la moyenne d'un étudiant pour un semestre
     * @param etudiantId l'identifiant de l'étudiant
     * @param semestre le numéro du semestre
     * @return la moyenne du semestre
     */
    public double calculerMoyenneSemestre(Long etudiantId, Integer semestre) {
        List<Note> notes = findByEtudiantAndSemestre(etudiantId, semestre);
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
     * Récupérer la liste de toutes les matières distinctes
     * @return liste des matières
     */
    public List<String> findAllMatieres() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT DISTINCT n.matiere FROM Note n ORDER BY n.matiere";
            TypedQuery<String> query = em.createQuery(jpql, String.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Compter le nombre de notes d'un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @return le nombre de notes
     */
    public long countByEtudiant(Long etudiantId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(n) FROM Note n WHERE n.etudiant.id = :etudiantId";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("etudiantId", etudiantId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer toutes les notes avec leurs étudiants (évite le lazy loading)
     * @return liste de toutes les notes avec les étudiants chargés
     */
    public List<Note> findAllWithEtudiants() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT DISTINCT n FROM Note n LEFT JOIN FETCH n.etudiant ORDER BY n.dateEvaluation DESC, n.matiere";
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
