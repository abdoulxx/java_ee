package com.iua.gestionetudiants.service;  // Package pour la couche service (logique métier)

import com.iua.gestionetudiants.dao.EtudiantDAO;  // Import du DAO pour accéder à la base de données
import com.iua.gestionetudiants.model.Etudiant;  // Import de l'entité Etudiant
import com.iua.gestionetudiants.model.Note;  // Import de l'entité Note

import java.util.List;  // Import de List pour les collections

/**
 * Service métier pour la gestion des étudiants
 * Couche Métier - Logique métier et règles de gestion
 * Valide les données, applique les règles métier, puis appelle le DAO
 */
public class EtudiantService {

    private EtudiantDAO etudiantDAO;  // Instance du DAO pour accéder aux données

    public EtudiantService() {  // Constructeur
        this.etudiantDAO = new EtudiantDAO();  // Crée une instance du DAO
    }

    /**
     * Créer un nouvel étudiant
     * Valide toutes les données avant insertion
     * @param etudiant l'étudiant à créer
     * @throws IllegalArgumentException si les données sont invalides
     */
    public void creerEtudiant(Etudiant etudiant) {
        // Validation 1: Vérifier que l'objet n'est pas null
        if (etudiant == null) {
            throw new IllegalArgumentException("L'étudiant ne peut pas être null");  // Lance une exception
        }
        // Validation 2: Vérifier que le nom est renseigné
        if (etudiant.getNom() == null || etudiant.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");  // Nom obligatoire
        }
        // Validation 3: Vérifier que le prénom est renseigné
        if (etudiant.getPrenom() == null || etudiant.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");  // Prénom obligatoire
        }

        // Validation 4: Vérifier que l'email n'existe pas déjà
        if (etudiant.getEmail() != null && !etudiant.getEmail().trim().isEmpty()) {
            Etudiant existant = etudiantDAO.findByEmail(etudiant.getEmail());  // Cherche dans la BD
            if (existant != null) {  // Si un étudiant avec cet email existe déjà
                throw new IllegalArgumentException("Un étudiant avec cet email existe déjà");  // Erreur doublon
            }
        }

        // Validation 5: Vérifier que le numéro d'étudiant n'existe pas déjà
        if (etudiant.getNumeroEtudiant() != null && !etudiant.getNumeroEtudiant().trim().isEmpty()) {
            Etudiant existant = etudiantDAO.findByNumeroEtudiant(etudiant.getNumeroEtudiant());  // Cherche dans la BD
            if (existant != null) {  // Si le numéro existe déjà
                throw new IllegalArgumentException("Un étudiant avec ce numéro existe déjà");  // Erreur doublon
            }
        }

        etudiantDAO.create(etudiant);  // Toutes les validations OK → Sauvegarde en base de données
    }

    /**
     * Mettre à jour un étudiant existant (modification)
     * @param etudiant l'étudiant à mettre à jour
     * @return l'étudiant mis à jour
     * @throws IllegalArgumentException si les données sont invalides
     */
    public Etudiant modifierEtudiant(Etudiant etudiant) {
        // Validation 1: Vérifier que l'objet et son ID existent
        if (etudiant == null || etudiant.getId() == null) {
            throw new IllegalArgumentException("L'étudiant ou son ID ne peut pas être null");  // ID nécessaire pour UPDATE
        }
        // Validation 2: Nom obligatoire
        if (etudiant.getNom() == null || etudiant.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        // Validation 3: Prénom obligatoire
        if (etudiant.getPrenom() == null || etudiant.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");
        }

        // Validation 4: Vérifier que l'étudiant existe en base
        Etudiant existant = etudiantDAO.findById(etudiant.getId());  // Cherche par ID
        if (existant == null) {  // Si pas trouvé
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + etudiant.getId());
        }

        // Validation 5: Vérifier que l'email n'est pas utilisé par un AUTRE étudiant
        if (etudiant.getEmail() != null && !etudiant.getEmail().trim().isEmpty()) {
            Etudiant autreEtudiant = etudiantDAO.findByEmail(etudiant.getEmail());  // Cherche par email
            if (autreEtudiant != null && !autreEtudiant.getId().equals(etudiant.getId())) {  // Si c'est un autre étudiant
                throw new IllegalArgumentException("Un autre étudiant utilise déjà cet email");  // Erreur doublon
            }
        }

        return etudiantDAO.update(etudiant);  // Met à jour en base de données
    }

    /**
     * Supprimer un étudiant (DELETE)
     * @param id l'identifiant de l'étudiant à supprimer
     * @throws IllegalArgumentException si l'étudiant n'existe pas
     */
    public void supprimerEtudiant(Long id) {
        if (id == null) {  // Vérifier que l'ID existe
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Etudiant etudiant = etudiantDAO.findById(id);  // Cherche l'étudiant
        if (etudiant == null) {  // Si pas trouvé
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + id);
        }

        etudiantDAO.delete(id);  // Supprime de la base de données
    }

    /**
     * Récupérer un étudiant par son ID (READ)
     * @param id l'identifiant de l'étudiant
     * @return l'étudiant ou null si non trouvé
     */
    public Etudiant getEtudiant(Long id) {
        return etudiantDAO.findById(id);  // Délègue au DAO
    }

    /**
     * Récupérer un étudiant avec ses notes (optimisé)
     * @param id l'identifiant de l'étudiant
     * @return l'étudiant avec ses notes chargées
     */
    public Etudiant getEtudiantAvecNotes(Long id) {
        return etudiantDAO.findByIdWithNotes(id);  // JOIN FETCH pour charger les notes
    }

    /**
     * Récupérer tous les étudiants (READ ALL)
     * @return liste de tous les étudiants
     */
    public List<Etudiant> getTousLesEtudiants() {
        return etudiantDAO.findAll();  // Récupère tous les enregistrements
    }

    /**
     * Récupérer tous les étudiants avec leurs notes
     * @return liste de tous les étudiants avec leurs notes
     */
    public List<Etudiant> getTousLesEtudiantsAvecNotes() {
        return etudiantDAO.findAllWithNotes();  // Charge tout avec JOIN FETCH
    }

    /**
     * Rechercher des étudiants par nom
     * @param nom le nom à rechercher
     * @return liste des étudiants correspondants
     */
    public List<Etudiant> rechercherParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {  // Si recherche vide
            return getTousLesEtudiants();  // Retourne tous les étudiants
        }
        return etudiantDAO.findByNom(nom);  // Recherche par nom
    }

    /**
     * Rechercher des étudiants par email
     * @param email l'email à rechercher
     * @return l'étudiant trouvé ou null
     */
    public Etudiant rechercherParEmail(String email) {
        return etudiantDAO.findByEmail(email);  // Délègue au DAO
    }

    /**
     * Rechercher des étudiants par filière
     * @param filiere la filière (GI, MIAGE)
     * @return liste des étudiants de cette filière
     */
    public List<Etudiant> rechercherParFiliere(String filiere) {
        return etudiantDAO.findByFiliere(filiere);  // Filtre par filière
    }

    /**
     * Calculer la moyenne d'un étudiant
     * @param id l'identifiant de l'étudiant
     * @return la moyenne de l'étudiant (0-20)
     */
    public double calculerMoyenne(Long id) {
        Etudiant etudiant = etudiantDAO.findByIdWithNotes(id);  // Charge avec notes
        if (etudiant == null) {  // Si étudiant pas trouvé
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + id);
        }
        return etudiant.calculerMoyenne();  // Appelle la méthode du modèle
    }

    /**
     * Ajouter une note à un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @param note la note à ajouter
     */
    public void ajouterNote(Long etudiantId, Note note) {
        Etudiant etudiant = etudiantDAO.findByIdWithNotes(etudiantId);  // Charge l'étudiant
        if (etudiant == null) {  // Validation
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + etudiantId);
        }

        etudiant.addNote(note);  // Ajoute la note (relation bidirectionnelle)
        etudiantDAO.update(etudiant);  // Sauvegarde en base
    }

    /**
     * Récupérer toutes les filières disponibles
     * @return liste des filières (ex: [GI, MIAGE])
     */
    public List<String> getToutesFilieres() {
        return etudiantDAO.findAllFilieres();  // Liste unique des filières
    }

    /**
     * Compter le nombre total d'étudiants
     * @return le nombre d'étudiants en base
     */
    public long compterEtudiants() {
        return etudiantDAO.count();  // COUNT(*) en SQL
    }

    /**
     * Vérifier si un email est déjà utilisé
     * @param email l'email à vérifier
     * @return true si l'email existe déjà
     */
    public boolean emailExiste(String email) {
        return etudiantDAO.findByEmail(email) != null;  // Retourne true si trouvé
    }

    /**
     * Vérifier si un numéro d'étudiant est déjà utilisé
     * @param numeroEtudiant le numéro à vérifier
     * @return true si le numéro existe déjà
     */
    public boolean numeroEtudiantExiste(String numeroEtudiant) {
        return etudiantDAO.findByNumeroEtudiant(numeroEtudiant) != null;  // Retourne true si trouvé
    }
}
