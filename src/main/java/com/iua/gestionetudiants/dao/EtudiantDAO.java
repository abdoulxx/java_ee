package com.iua.gestionetudiants.dao;  // Package pour les classes d'accès aux données

import com.iua.gestionetudiants.model.Etudiant;  // Import de l'entité Etudiant
import com.iua.gestionetudiants.model.Note;  // Import de l'entité Note

import javax.persistence.EntityManager;  // Gestionnaire d'entités JPA
import javax.persistence.TypedQuery;  // Requête typée JPA
import java.util.List;  // Interface List pour les collections

/**
 * DAO (Data Access Object) pour la gestion de la persistance des étudiants
 * Couche Persistance - Accès aux données
 * Contient toutes les méthodes CRUD et requêtes personnalisées
 */
public class EtudiantDAO extends GenericDAO<Etudiant> {  // Hérite de GenericDAO pour avoir les méthodes CRUD de base

    public EtudiantDAO() {  // Constructeur
        super(Etudiant.class);  // Appelle le constructeur parent avec la classe Etudiant
    }

    /**
     * Rechercher des étudiants par nom (recherche partielle, insensible à la casse)
     * Ex: "Dup" trouve "Dupont", "DUPUIS", "dupond"
     * @param nom le nom à rechercher
     * @return liste des étudiants correspondants
     */
    public List<Etudiant> findByNom(String nom) {
        EntityManager em = getEntityManager();  // Obtient le gestionnaire d'entités JPA
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE LOWER(e.nom) LIKE LOWER(:nom)";  // Requête JPQL (pas SQL!)
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);  // Crée une requête typée
            query.setParameter("nom", "%" + nom + "%");  // Remplace :nom par %valeur% (recherche partielle)
            return query.getResultList();  // Exécute la requête et retourne la liste
        } finally {
            em.close();  // Ferme l'EntityManager pour libérer les ressources
        }
    }

    /**
     * Rechercher des étudiants par prénom (recherche partielle, insensible à la casse)
     * @param prenom le prénom à rechercher
     * @return liste des étudiants correspondants
     */
    public List<Etudiant> findByPrenom(String prenom) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE LOWER(e.prenom) LIKE LOWER(:prenom)";  // Requête JPQL
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);  // Crée la requête
            query.setParameter("prenom", "%" + prenom + "%");  // Paramètre avec % pour recherche partielle
            return query.getResultList();  // Retourne tous les résultats
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Rechercher un étudiant par email (email unique)
     * @param email l'email à rechercher
     * @return l'étudiant trouvé ou null si pas trouvé
     */
    public Etudiant findByEmail(String email) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE e.email = :email";  // Recherche exacte sur email
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);  // Crée la requête
            query.setParameter("email", email);  // Paramètre exact (pas de %)
            List<Etudiant> results = query.getResultList();  // Obtient la liste des résultats
            return results.isEmpty() ? null : results.get(0);  // Retourne le 1er ou null si vide
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Rechercher un étudiant par numéro d'étudiant (unique)
     * @param numeroEtudiant le numéro d'étudiant
     * @return l'étudiant trouvé ou null si pas trouvé
     */
    public Etudiant findByNumeroEtudiant(String numeroEtudiant) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE e.numeroEtudiant = :numero";  // Recherche exacte
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);  // Crée la requête
            query.setParameter("numero", numeroEtudiant);  // Remplace :numero
            List<Etudiant> results = query.getResultList();  // Exécute la requête
            return results.isEmpty() ? null : results.get(0);  // Retourne le résultat ou null
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Rechercher des étudiants par filière (GI, MIAGE, etc.)
     * @param filiere la filière
     * @return liste des étudiants de cette filière triés par nom
     */
    public List<Etudiant> findByFiliere(String filiere) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT e FROM Etudiant e WHERE e.filiere = :filiere ORDER BY e.nom, e.prenom";  // Tri par nom
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);  // Crée la requête
            query.setParameter("filiere", filiere);  // Remplace :filiere
            return query.getResultList();  // Retourne la liste triée
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Récupérer un étudiant avec toutes ses notes (évite le lazy loading)
     * JOIN FETCH charge immédiatement les notes avec l'étudiant
     * @param id l'identifiant de l'étudiant
     * @return l'étudiant avec ses notes chargées
     */
    public Etudiant findByIdWithNotes(Long id) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT DISTINCT e FROM Etudiant e LEFT JOIN FETCH e.notes WHERE e.id = :id";  // JOIN FETCH pour charger les notes
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);  // Crée la requête
            query.setParameter("id", id);  // Remplace :id
            List<Etudiant> results = query.getResultList();  // Exécute la requête
            return results.isEmpty() ? null : results.get(0);  // Retourne l'étudiant ou null
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Récupérer tous les étudiants avec leurs notes
     * Utile pour afficher la liste complète avec moyennes
     * @return liste de tous les étudiants avec leurs notes chargées
     */
    public List<Etudiant> findAllWithNotes() {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT DISTINCT e FROM Etudiant e LEFT JOIN FETCH e.notes ORDER BY e.nom, e.prenom";  // Charge tout
            TypedQuery<Etudiant> query = em.createQuery(jpql, Etudiant.class);  // Crée la requête
            return query.getResultList();  // Retourne tous les étudiants avec notes
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Rechercher les étudiants dont la moyenne est supérieure à un seuil
     * Note: Calcul effectué en Java car formule de moyenne pondérée complexe
     * @param moyenneMin la moyenne minimale (ex: 10.0)
     * @return liste des étudiants ayant une moyenne >= moyenneMin
     */
    public List<Etudiant> findByMoyenneSuperieure(double moyenneMin) {
        List<Etudiant> etudiants = findAllWithNotes();  // Récupère tous les étudiants avec notes
        etudiants.removeIf(e -> e.calculerMoyenne() < moyenneMin);  // Supprime ceux < seuil
        return etudiants;  // Retourne la liste filtrée
    }

    /**
     * Récupérer la liste de toutes les filières distinctes
     * Utile pour afficher un menu déroulant des filières
     * @return liste des filières (ex: [GI, MIAGE])
     */
    public List<String> findAllFilieres() {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT DISTINCT e.filiere FROM Etudiant e WHERE e.filiere IS NOT NULL ORDER BY e.filiere";  // Filières uniques
            TypedQuery<String> query = em.createQuery(jpql, String.class);  // Requête retournant des String
            return query.getResultList();  // Retourne la liste des filières
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }
}
