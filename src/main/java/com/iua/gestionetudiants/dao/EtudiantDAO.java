package com.iua.gestionetudiants.dao;

import com.iua.gestionetudiants.model.Etudiant;
import com.iua.gestionetudiants.model.Note;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO pour la gestion de la persistance des étudiants
 * Couche Persistance - Accès aux données
 */
public class EtudiantDAO extends GenericDAO<Etudiant> {

    public EtudiantDAO() {
        super(Etudiant.class);
    }

    /**
     * Rechercher des étudiants par nom (recherche partielle, insensible à la casse)
     * @param nom le nom à rechercher
     * @return liste des étudiants correspondants
     */
    public List<Etudiant> findByNom(String nom) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE LOWER(e.nom) LIKE LOWER(:nom)";
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);
            query.setParameter("nom", "%" + nom + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Rechercher des étudiants par prénom (recherche partielle, insensible à la casse)
     * @param prenom le prénom à rechercher
     * @return liste des étudiants correspondants
     */
    public List<Etudiant> findByPrenom(String prenom) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE LOWER(e.prenom) LIKE LOWER(:prenom)";
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);
            query.setParameter("prenom", "%" + prenom + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Rechercher un étudiant par email
     * @param email l'email à rechercher
     * @return l'étudiant trouvé ou null
     */
    public Etudiant findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE e.email = :email";
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);
            query.setParameter("email", email);
            List<Etudiant> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Rechercher un étudiant par numéro d'étudiant
     * @param numeroEtudiant le numéro d'étudiant
     * @return l'étudiant trouvé ou null
     */
    public Etudiant findByNumeroEtudiant(String numeroEtudiant) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE e.numeroEtudiant = :numero";
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);
            query.setParameter("numero", numeroEtudiant);
            List<Etudiant> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Rechercher des étudiants par filière
     * @param filiere la filière
     * @return liste des étudiants de cette filière
     */
    public List<Etudiant> findByFiliere(String filiere) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE e.filiere = :filiere ORDER BY e.nom, e.prenom";
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);
            query.setParameter("filiere", filiere);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer un étudiant avec toutes ses notes (évite le lazy loading)
     * @param id l'identifiant de l'étudiant
     * @return l'étudiant avec ses notes
     */
    public Etudiant findByIdWithNotes(Long id) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT DISTINCT e FROM Etudiant e LEFT JOIN FETCH e.notes WHERE e.id = :id";
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);
            query.setParameter("id", id);
            List<Etudiant> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer tous les étudiants avec leurs notes
     * @return liste de tous les étudiants avec leurs notes
     */
    public List<Etudiant> findAllWithNotes() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT DISTINCT e FROM Etudiant e LEFT JOIN FETCH e.notes ORDER BY e.nom, e.prenom";
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Rechercher les étudiants dont la moyenne est supérieure à un seuil
     * Note: Calcul effectué en Java car calcul de moyenne complexe
     * @param moyenneMin la moyenne minimale
     * @return liste des étudiants ayant une moyenne >= moyenneMin
     */
    public List<Etudiant> findByMoyenneSuperieure(double moyenneMin) {
        List<Etudiant> etudiants = findAllWithNotes();
        etudiants.removeIf(e -> e.calculerMoyenne() < moyenneMin);
        return etudiants;
    }

    /**
     * Récupérer la liste de toutes les filières distinctes
     * @return liste des filières
     */
    public List<String> findAllFilieres() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT DISTINCT e.filiere FROM Etudiant e WHERE e.filiere IS NOT NULL ORDER BY e.filiere";
            TypedQuery<String> query = em.createQuery(jpql, String.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
