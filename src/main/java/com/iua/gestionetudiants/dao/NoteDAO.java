package com.iua.gestionetudiants.dao;  // Package pour les classes DAO

import com.iua.gestionetudiants.model.Etudiant;  // Import de l'entité Etudiant
import com.iua.gestionetudiants.model.Note;  // Import de l'entité Note

import javax.persistence.EntityManager;  // Gestionnaire d'entités JPA
import javax.persistence.TypedQuery;  // Requête typée JPA
import java.util.List;  // Interface List

/**
 * DAO (Data Access Object) pour la gestion de la persistance des notes
 * Couche Persistance - Accès aux données
 * Contient toutes les méthodes CRUD et requêtes personnalisées pour les notes
 */
public class NoteDAO extends GenericDAO<Note> {  // Hérite de GenericDAO pour avoir les méthodes CRUD de base

    public NoteDAO() {  // Constructeur
        super(Note.class);  // Appelle le constructeur parent avec la classe Note
    }

    /**
     * Récupérer toutes les notes d'un étudiant
     * Triées par date (plus récentes en premier) puis par matière
     * @param etudiantId l'identifiant de l'étudiant
     * @return liste des notes de l'étudiant
     */
    public List<Note> findByEtudiant(Long etudiantId) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId ORDER BY n.dateEvaluation DESC, n.matiere";  // Requête JPQL
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);  // Crée la requête
            query.setParameter("etudiantId", etudiantId);  // Remplace :etudiantId
            return query.getResultList();  // Retourne toutes les notes de l'étudiant
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Récupérer les notes d'un étudiant pour une matière donnée
     * Utile pour voir l'évolution dans une matière
     * @param etudiantId l'identifiant de l'étudiant
     * @param matiere la matière (ex: "Java EE")
     * @return liste des notes de l'étudiant pour cette matière
     */
    public List<Note> findByEtudiantAndMatiere(Long etudiantId, String matiere) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.matiere = :matiere ORDER BY n.dateEvaluation DESC";  // Filtre par étudiant ET matière
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);  // Crée la requête
            query.setParameter("etudiantId", etudiantId);  // Paramètre étudiant
            query.setParameter("matiere", matiere);  // Paramètre matière
            return query.getResultList();  // Retourne les résultats
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Récupérer toutes les notes d'une matière
     * Triées par valeur décroissante (meilleures notes en premier)
     * @param matiere la matière
     * @return liste des notes pour cette matière
     */
    public List<Note> findByMatiere(String matiere) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT n FROM Note n WHERE n.matiere = :matiere ORDER BY n.valeur DESC";  // Tri par note décroissante
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);  // Crée la requête
            query.setParameter("matiere", matiere);  // Remplace :matiere
            return query.getResultList();  // Retourne toutes les notes de cette matière
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Récupérer les notes d'un étudiant pour un semestre donné
     * @param etudiantId l'identifiant de l'étudiant
     * @param semestre le numéro du semestre (1, 2, 7, 8)
     * @return liste des notes du semestre triées par matière
     */
    public List<Note> findByEtudiantAndSemestre(Long etudiantId, Integer semestre) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.semestre = :semestre ORDER BY n.matiere";  // Filtre par semestre
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);  // Crée la requête
            query.setParameter("etudiantId", etudiantId);  // Paramètre étudiant
            query.setParameter("semestre", semestre);  // Paramètre semestre
            return query.getResultList();  // Retourne les notes du semestre
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Récupérer toutes les notes supérieures ou égales à une valeur
     * Utile pour trouver les bonnes notes (ex: >= 14 pour les mentions)
     * @param valeurMin la valeur minimale
     * @return liste des notes >= valeurMin
     */
    public List<Note> findByValeurSuperieure(double valeurMin) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT n FROM Note n WHERE n.valeur >= :valeurMin ORDER BY n.valeur DESC";  // Requête JPQL avec filtre >= et tri décroissant
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);  // Crée la requête typée
            query.setParameter("valeurMin", valeurMin);  // Remplace :valeurMin par la valeur (ex: 14.0)
            return query.getResultList();  // Retourne toutes les notes >= valeurMin
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Récupérer toutes les notes inférieures à une valeur (notes insuffisantes)
     * Utile pour identifier les étudiants en difficulté (ex: < 10)
     * @param valeurMax la valeur maximale
     * @return liste des notes < valeurMax
     */
    public List<Note> findByValeurInferieure(double valeurMax) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT n FROM Note n WHERE n.valeur < :valeurMax ORDER BY n.valeur ASC";  // Requête JPQL avec filtre < et tri croissant (pires notes en premier)
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);  // Crée la requête typée
            query.setParameter("valeurMax", valeurMax);  // Remplace :valeurMax par la valeur (ex: 10.0)
            return query.getResultList();  // Retourne toutes les notes < valeurMax
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Calculer la moyenne d'un étudiant
     * Moyenne pondérée par les coefficients (somme des notes pondérées / somme des coefficients)
     * @param etudiantId l'identifiant de l'étudiant
     * @return la moyenne de l'étudiant
     */
    public double calculerMoyenne(Long etudiantId) {
        List<Note> notes = findByEtudiant(etudiantId);  // Récupère toutes les notes de l'étudiant
        if (notes == null || notes.isEmpty()) {  // Si aucune note trouvée
            return 0.0;  // Retourne 0 (pas de notes = moyenne nulle)
        }

        double sommeNotes = 0.0;  // Variable pour accumuler la somme des notes pondérées
        double sommeCoefficients = 0.0;  // Variable pour accumuler la somme des coefficients

        for (Note note : notes) {  // Parcourt toutes les notes de l'étudiant
            sommeNotes += note.getValeur() * note.getCoefficient();  // Ajoute la note pondérée (ex: 15 × 2 = 30)
            sommeCoefficients += note.getCoefficient();  // Ajoute le coefficient (ex: 2)
        }

        return sommeCoefficients > 0 ? sommeNotes / sommeCoefficients : 0.0;  // Divise somme pondérée par somme coefficients (ex: 30/2 = 15)
    }

    /**
     * Calculer la moyenne d'un étudiant pour un semestre
     * Même calcul que calculerMoyenne() mais filtré par semestre (ex: semestre 1, semestre 2)
     * @param etudiantId l'identifiant de l'étudiant
     * @param semestre le numéro du semestre
     * @return la moyenne du semestre
     */
    public double calculerMoyenneSemestre(Long etudiantId, Integer semestre) {
        List<Note> notes = findByEtudiantAndSemestre(etudiantId, semestre);  // Récupère les notes de l'étudiant pour ce semestre uniquement
        if (notes == null || notes.isEmpty()) {  // Si aucune note pour ce semestre
            return 0.0;  // Retourne 0
        }

        double sommeNotes = 0.0;  // Variable pour accumuler la somme des notes pondérées
        double sommeCoefficients = 0.0;  // Variable pour accumuler la somme des coefficients

        for (Note note : notes) {  // Parcourt toutes les notes du semestre
            sommeNotes += note.getValeur() * note.getCoefficient();  // Ajoute la note pondérée (valeur × coefficient)
            sommeCoefficients += note.getCoefficient();  // Ajoute le coefficient
        }

        return sommeCoefficients > 0 ? sommeNotes / sommeCoefficients : 0.0;  // Calcule la moyenne pondérée du semestre
    }

    /**
     * Récupérer la liste de toutes les matières distinctes
     * Utile pour afficher un menu déroulant des matières disponibles
     * @return liste des matières
     */
    public List<String> findAllMatieres() {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT DISTINCT n.matiere FROM Note n ORDER BY n.matiere";  // DISTINCT élimine les doublons, ORDER BY trie alphabétiquement
            TypedQuery<String> query = em.createQuery(jpql, String.class);  // Requête retournant des String (pas des objets Note)
            return query.getResultList();  // Retourne la liste des matières uniques (ex: ["Java EE", "Mathématiques", "UML"])
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Compter le nombre de notes d'un étudiant
     * Utile pour savoir combien d'évaluations un étudiant a passées
     * @param etudiantId l'identifiant de l'étudiant
     * @return le nombre de notes
     */
    public long countByEtudiant(Long etudiantId) {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT COUNT(n) FROM Note n WHERE n.etudiant.id = :etudiantId";  // Fonction d'agrégation COUNT pour compter
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);  // Requête retournant un Long (le nombre)
            query.setParameter("etudiantId", etudiantId);  // Remplace :etudiantId
            return query.getSingleResult();  // Retourne le résultat unique (ex: 12 notes)
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }

    /**
     * Récupérer toutes les notes avec leurs étudiants (évite le lazy loading)
     * JOIN FETCH charge immédiatement les étudiants avec les notes en une seule requête SQL
     * @return liste de toutes les notes avec les étudiants chargés
     */
    public List<Note> findAllWithEtudiants() {
        EntityManager em = getEntityManager();  // Obtient l'EntityManager
        try {
            String jpql = "SELECT DISTINCT n FROM Note n LEFT JOIN FETCH n.etudiant ORDER BY n.dateEvaluation DESC, n.matiere";  // LEFT JOIN FETCH charge l'étudiant avec la note
            TypedQuery<Note> query = em.createQuery(jpql, Note.class);  // Crée la requête typée
            return query.getResultList();  // Retourne toutes les notes avec étudiants (évite N+1 queries)
        } finally {
            em.close();  // Ferme l'EntityManager
        }
    }
}
