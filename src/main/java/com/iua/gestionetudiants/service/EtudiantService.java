package com.iua.gestionetudiants.service;

import com.iua.gestionetudiants.dao.EtudiantDAO;
import com.iua.gestionetudiants.model.Etudiant;
import com.iua.gestionetudiants.model.Note;

import java.util.List;

/**
 * Service métier pour la gestion des étudiants
 * Couche Métier - Logique métier et règles de gestion
 */
public class EtudiantService {

    private EtudiantDAO etudiantDAO;

    public EtudiantService() {
        this.etudiantDAO = new EtudiantDAO();
    }

    /**
     * Créer un nouvel étudiant
     * @param etudiant l'étudiant à créer
     * @throws IllegalArgumentException si les données sont invalides
     */
    public void creerEtudiant(Etudiant etudiant) {
        // Validation des données
        if (etudiant == null) {
            throw new IllegalArgumentException("L'étudiant ne peut pas être null");
        }
        if (etudiant.getNom() == null || etudiant.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (etudiant.getPrenom() == null || etudiant.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");
        }

        // Vérifier que l'email n'existe pas déjà
        if (etudiant.getEmail() != null && !etudiant.getEmail().trim().isEmpty()) {
            Etudiant existant = etudiantDAO.findByEmail(etudiant.getEmail());
            if (existant != null) {
                throw new IllegalArgumentException("Un étudiant avec cet email existe déjà");
            }
        }

        // Vérifier que le numéro d'étudiant n'existe pas déjà
        if (etudiant.getNumeroEtudiant() != null && !etudiant.getNumeroEtudiant().trim().isEmpty()) {
            Etudiant existant = etudiantDAO.findByNumeroEtudiant(etudiant.getNumeroEtudiant());
            if (existant != null) {
                throw new IllegalArgumentException("Un étudiant avec ce numéro existe déjà");
            }
        }

        etudiantDAO.create(etudiant);
    }

    /**
     * Mettre à jour un étudiant existant
     * @param etudiant l'étudiant à mettre à jour
     * @return l'étudiant mis à jour
     * @throws IllegalArgumentException si les données sont invalides
     */
    public Etudiant modifierEtudiant(Etudiant etudiant) {
        // Validation des données
        if (etudiant == null || etudiant.getId() == null) {
            throw new IllegalArgumentException("L'étudiant ou son ID ne peut pas être null");
        }
        if (etudiant.getNom() == null || etudiant.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (etudiant.getPrenom() == null || etudiant.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");
        }

        // Vérifier que l'étudiant existe
        Etudiant existant = etudiantDAO.findById(etudiant.getId());
        if (existant == null) {
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + etudiant.getId());
        }

        // Vérifier que l'email n'est pas déjà utilisé par un autre étudiant
        if (etudiant.getEmail() != null && !etudiant.getEmail().trim().isEmpty()) {
            Etudiant autreEtudiant = etudiantDAO.findByEmail(etudiant.getEmail());
            if (autreEtudiant != null && !autreEtudiant.getId().equals(etudiant.getId())) {
                throw new IllegalArgumentException("Un autre étudiant utilise déjà cet email");
            }
        }

        return etudiantDAO.update(etudiant);
    }

    /**
     * Supprimer un étudiant
     * @param id l'identifiant de l'étudiant à supprimer
     * @throws IllegalArgumentException si l'étudiant n'existe pas
     */
    public void supprimerEtudiant(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Etudiant etudiant = etudiantDAO.findById(id);
        if (etudiant == null) {
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + id);
        }

        etudiantDAO.delete(id);
    }

    /**
     * Récupérer un étudiant par son ID
     * @param id l'identifiant de l'étudiant
     * @return l'étudiant ou null si non trouvé
     */
    public Etudiant getEtudiant(Long id) {
        return etudiantDAO.findById(id);
    }

    /**
     * Récupérer un étudiant avec ses notes
     * @param id l'identifiant de l'étudiant
     * @return l'étudiant avec ses notes
     */
    public Etudiant getEtudiantAvecNotes(Long id) {
        return etudiantDAO.findByIdWithNotes(id);
    }

    /**
     * Récupérer tous les étudiants
     * @return liste de tous les étudiants
     */
    public List<Etudiant> getTousLesEtudiants() {
        return etudiantDAO.findAll();
    }

    /**
     * Récupérer tous les étudiants avec leurs notes
     * @return liste de tous les étudiants avec leurs notes
     */
    public List<Etudiant> getTousLesEtudiantsAvecNotes() {
        return etudiantDAO.findAllWithNotes();
    }

    /**
     * Rechercher des étudiants par nom
     * @param nom le nom à rechercher
     * @return liste des étudiants correspondants
     */
    public List<Etudiant> rechercherParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return getTousLesEtudiants();
        }
        return etudiantDAO.findByNom(nom);
    }

    /**
     * Rechercher des étudiants par email
     * @param email l'email à rechercher
     * @return l'étudiant trouvé ou null
     */
    public Etudiant rechercherParEmail(String email) {
        return etudiantDAO.findByEmail(email);
    }

    /**
     * Rechercher des étudiants par filière
     * @param filiere la filière
     * @return liste des étudiants de cette filière
     */
    public List<Etudiant> rechercherParFiliere(String filiere) {
        return etudiantDAO.findByFiliere(filiere);
    }

    /**
     * Calculer la moyenne d'un étudiant
     * @param id l'identifiant de l'étudiant
     * @return la moyenne de l'étudiant
     */
    public double calculerMoyenne(Long id) {
        Etudiant etudiant = etudiantDAO.findByIdWithNotes(id);
        if (etudiant == null) {
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + id);
        }
        return etudiant.calculerMoyenne();
    }

    /**
     * Ajouter une note à un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @param note la note à ajouter
     */
    public void ajouterNote(Long etudiantId, Note note) {
        Etudiant etudiant = etudiantDAO.findByIdWithNotes(etudiantId);
        if (etudiant == null) {
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + etudiantId);
        }

        etudiant.addNote(note);
        etudiantDAO.update(etudiant);
    }

    /**
     * Récupérer toutes les filières
     * @return liste des filières
     */
    public List<String> getToutesFilieres() {
        return etudiantDAO.findAllFilieres();
    }

    /**
     * Compter le nombre total d'étudiants
     * @return le nombre d'étudiants
     */
    public long compterEtudiants() {
        return etudiantDAO.count();
    }

    /**
     * Vérifier si un email est déjà utilisé
     * @param email l'email à vérifier
     * @return true si l'email existe déjà
     */
    public boolean emailExiste(String email) {
        return etudiantDAO.findByEmail(email) != null;
    }

    /**
     * Vérifier si un numéro d'étudiant est déjà utilisé
     * @param numeroEtudiant le numéro à vérifier
     * @return true si le numéro existe déjà
     */
    public boolean numeroEtudiantExiste(String numeroEtudiant) {
        return etudiantDAO.findByNumeroEtudiant(numeroEtudiant) != null;
    }
}
