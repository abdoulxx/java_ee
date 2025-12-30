package com.iua.gestionetudiants.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Classe générique DAO implémentant les opérations CRUD de base
 * Couche Persistance - Accès aux données
 *
 * @param <T> Type de l'entité
 */
public abstract class GenericDAO<T> {

    // Factory pour créer les EntityManager
    private static EntityManagerFactory emf;

    // Classe de l'entité
    private Class<T> entityClass;

    /**
     * Constructeur
     * @param entityClass classe de l'entité
     */
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("GestionEtudiantsPU");
        }
    }

    /**
     * Obtenir un EntityManager
     * @return EntityManager
     */
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Créer une nouvelle entité
     * @param entity l'entité à créer
     */
    public void create(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la création de l'entité", e);
        } finally {
            em.close();
        }
    }

    /**
     * Mettre à jour une entité existante
     * @param entity l'entité à mettre à jour
     * @return l'entité mise à jour
     */
    public T update(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = null;
        T updatedEntity = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            updatedEntity = em.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour de l'entité", e);
        } finally {
            em.close();
        }
        return updatedEntity;
    }

    /**
     * Supprimer une entité par son ID
     * @param id l'identifiant de l'entité
     */
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression de l'entité", e);
        } finally {
            em.close();
        }
    }

    /**
     * Trouver une entité par son ID
     * @param id l'identifiant de l'entité
     * @return l'entité trouvée ou null
     */
    public T findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    /**
     * Récupérer toutes les entités
     * @return liste de toutes les entités
     */
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Compter le nombre total d'entités
     * @return le nombre d'entités
     */
    public long count() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Fermer l'EntityManagerFactory
     * À appeler lors de l'arrêt de l'application
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
