package com.iua.gestionetudiants.service;  // Package pour la couche service (logique métier)

import com.iua.gestionetudiants.dao.EtudiantDAO;  // Import du DAO pour accéder aux étudiants en base de données
import com.iua.gestionetudiants.dao.NoteDAO;  // Import du DAO pour accéder aux notes en base de données
import com.iua.gestionetudiants.model.Etudiant;  // Import de l'entité Etudiant
import com.iua.gestionetudiants.model.Note;  // Import de l'entité Note

import java.util.Date;  // Import de Date pour les dates d'évaluation
import java.util.List;  // Import de List pour les collections

/**
 * Service métier pour la gestion des notes
 * Couche Métier - Logique métier et règles de gestion
 * Valide les données, applique les règles métier, puis appelle le DAO
 */
public class NoteService {

    private NoteDAO noteDAO;  // Instance du DAO pour accéder aux notes en base de données
    private EtudiantDAO etudiantDAO;  // Instance du DAO pour vérifier l'existence des étudiants

    public NoteService() {  // Constructeur
        this.noteDAO = new NoteDAO();  // Crée une instance du NoteDAO
        this.etudiantDAO = new EtudiantDAO();  // Crée une instance de l'EtudiantDAO
    }

    /**
     * Créer une nouvelle note
     * Valide toutes les données avant insertion en base de données
     * @param note la note à créer
     * @throws IllegalArgumentException si les données sont invalides
     */
    public void creerNote(Note note) {
        // Validation 1: Vérifier que l'objet n'est pas null
        if (note == null) {  // Si l'objet note est null
            throw new IllegalArgumentException("La note ne peut pas être null");  // Lance une exception
        }
        // Validation 2: Vérifier que la matière est renseignée
        if (note.getMatiere() == null || note.getMatiere().trim().isEmpty()) {  // Si matière vide ou null
            throw new IllegalArgumentException("La matière est obligatoire");  // Matière obligatoire
        }
        // Validation 3: Vérifier que la valeur est renseignée
        if (note.getValeur() == null) {  // Si valeur null
            throw new IllegalArgumentException("La valeur de la note est obligatoire");  // Valeur obligatoire
        }
        // Validation 4: Vérifier que la note est entre 0 et 20 (système français)
        if (note.getValeur() < 0 || note.getValeur() > 20) {  // Si note < 0 ou > 20
            throw new IllegalArgumentException("La note doit être entre 0 et 20");  // Intervalle [0, 20]
        }
        // Validation 5: Vérifier que le coefficient est positif
        if (note.getCoefficient() == null || note.getCoefficient() <= 0) {  // Si coefficient null ou <= 0
            throw new IllegalArgumentException("Le coefficient doit être supérieur à 0");  // Coefficient > 0
        }
        // Validation 6: Vérifier qu'un étudiant est associé à la note
        if (note.getEtudiant() == null || note.getEtudiant().getId() == null) {  // Si pas d'étudiant ou pas d'ID
            throw new IllegalArgumentException("L'étudiant est obligatoire");  // Étudiant obligatoire
        }

        // Validation 7: Vérifier que l'étudiant existe en base de données
        Etudiant etudiant = etudiantDAO.findById(note.getEtudiant().getId());  // Cherche l'étudiant par ID
        if (etudiant == null) {  // Si étudiant pas trouvé
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + note.getEtudiant().getId());  // Erreur
        }

        // Définir la date d'évaluation à aujourd'hui si non définie
        if (note.getDateEvaluation() == null) {  // Si pas de date
            note.setDateEvaluation(new Date());  // Date actuelle (new Date() = maintenant)
        }

        noteDAO.create(note);  // Toutes les validations OK → Sauvegarde en base de données
    }

    /**
     * Ajouter une note à un étudiant (méthode pratique avec paramètres individuels)
     * Simplifie l'ajout d'une note sans créer manuellement l'objet Note
     * @param etudiantId l'identifiant de l'étudiant
     * @param matiere la matière (ex: "Java EE")
     * @param valeur la valeur de la note (ex: 15.5)
     * @param coefficient le coefficient (ex: 2.0)
     * @throws IllegalArgumentException si les données sont invalides
     */
    public void ajouterNote(Long etudiantId, String matiere, Double valeur, Double coefficient) {
        // Récupérer l'étudiant de la base de données
        Etudiant etudiant = etudiantDAO.findById(etudiantId);  // Cherche l'étudiant par ID
        if (etudiant == null) {  // Si pas trouvé
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + etudiantId);  // Erreur
        }

        // Créer un objet Note avec les paramètres fournis
        Note note = new Note(matiere, valeur, coefficient);  // Constructeur avec 3 paramètres
        note.setEtudiant(etudiant);  // Associe l'étudiant à la note
        note.setDateEvaluation(new Date());  // Date d'évaluation = aujourd'hui

        // Valider et créer en appelant la méthode creerNote()
        creerNote(note);  // Appelle creerNote() qui fait toutes les validations et sauvegarde
    }

    /**
     * Mettre à jour une note existante (modification)
     * Valide les données avant de faire l'UPDATE en base de données
     * @param note la note à mettre à jour
     * @return la note mise à jour
     * @throws IllegalArgumentException si les données sont invalides
     */
    public Note modifierNote(Note note) {
        // Validation 1: Vérifier que l'objet et son ID existent
        if (note == null || note.getId() == null) {  // Si note null ou ID null
            throw new IllegalArgumentException("La note ou son ID ne peut pas être null");  // ID nécessaire pour UPDATE
        }
        // Validation 2: Matière obligatoire
        if (note.getMatiere() == null || note.getMatiere().trim().isEmpty()) {  // Si matière vide
            throw new IllegalArgumentException("La matière est obligatoire");  // Erreur
        }
        // Validation 3: Note entre 0 et 20
        if (note.getValeur() == null || note.getValeur() < 0 || note.getValeur() > 20) {  // Si hors intervalle [0, 20]
            throw new IllegalArgumentException("La note doit être entre 0 et 20");  // Erreur
        }
        // Validation 4: Coefficient positif
        if (note.getCoefficient() == null || note.getCoefficient() <= 0) {  // Si coefficient <= 0
            throw new IllegalArgumentException("Le coefficient doit être supérieur à 0");  // Erreur
        }

        // Validation 5: Vérifier que la note existe en base
        Note existante = noteDAO.findById(note.getId());  // Cherche la note par ID
        if (existante == null) {  // Si pas trouvée
            throw new IllegalArgumentException("Note non trouvée avec l'ID: " + note.getId());  // Erreur
        }

        return noteDAO.update(note);  // Met à jour en base de données et retourne la note modifiée
    }

    /**
     * Supprimer une note (DELETE)
     * Vérifie l'existence de la note avant suppression
     * @param id l'identifiant de la note à supprimer
     * @throws IllegalArgumentException si la note n'existe pas
     */
    public void supprimerNote(Long id) {
        if (id == null) {  // Vérifier que l'ID existe
            throw new IllegalArgumentException("L'ID ne peut pas être null");  // Erreur si ID null
        }

        Note note = noteDAO.findById(id);  // Cherche la note en base de données
        if (note == null) {  // Si note pas trouvée
            throw new IllegalArgumentException("Note non trouvée avec l'ID: " + id);  // Erreur
        }

        noteDAO.delete(id);  // Supprime la note de la base de données
    }

    /**
     * Récupérer une note par son ID (READ)
     * Délègue la recherche au DAO
     * @param id l'identifiant de la note
     * @return la note ou null si non trouvée
     */
    public Note getNote(Long id) {
        return noteDAO.findById(id);  // Délègue au DAO pour récupérer la note
    }

    /**
     * Récupérer toutes les notes avec leurs étudiants (READ ALL)
     * Utilise JOIN FETCH pour charger les étudiants immédiatement
     * @return liste de toutes les notes avec étudiants
     */
    public List<Note> getToutesLesNotes() {
        return noteDAO.findAllWithEtudiants();  // Récupère toutes les notes avec JOIN FETCH pour éviter lazy loading
    }

    /**
     * Récupérer les notes d'un étudiant (filtre par étudiant)
     * Utile pour afficher le bulletin d'un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @return liste des notes de l'étudiant triées par date décroissante
     */
    public List<Note> getNotesParEtudiant(Long etudiantId) {
        return noteDAO.findByEtudiant(etudiantId);  // Délègue au DAO avec filtre WHERE etudiant.id = :etudiantId
    }

    /**
     * Récupérer les notes d'un étudiant pour une matière donnée
     * Utile pour voir l'évolution d'un étudiant dans une matière
     * @param etudiantId l'identifiant de l'étudiant
     * @param matiere la matière (ex: "Java EE", "Mathématiques")
     * @return liste des notes filtrées par étudiant ET matière
     */
    public List<Note> getNotesParEtudiantEtMatiere(Long etudiantId, String matiere) {
        return noteDAO.findByEtudiantAndMatiere(etudiantId, matiere);  // Double filtre: étudiant + matière
    }

    /**
     * Récupérer les notes d'un étudiant pour un semestre donné
     * Utile pour calculer la moyenne du semestre ou afficher le bulletin semestriel
     * @param etudiantId l'identifiant de l'étudiant
     * @param semestre le numéro du semestre (ex: 1, 2, 7, 8)
     * @return liste des notes du semestre triées par matière
     */
    public List<Note> getNotesParSemestre(Long etudiantId, Integer semestre) {
        return noteDAO.findByEtudiantAndSemestre(etudiantId, semestre);  // Filtre WHERE etudiant.id = :id AND semestre = :semestre
    }

    /**
     * Calculer la moyenne générale d'un étudiant (toutes matières confondues)
     * Moyenne pondérée: (somme des notes × coefficient) / (somme des coefficients)
     * @param etudiantId l'identifiant de l'étudiant
     * @return la moyenne générale de l'étudiant (ex: 14.5)
     */
    public double calculerMoyenne(Long etudiantId) {
        return noteDAO.calculerMoyenne(etudiantId);  // Délègue au DAO qui calcule la moyenne pondérée
    }

    /**
     * Calculer la moyenne d'un étudiant pour un semestre spécifique
     * Utile pour les bulletins semestriels et passage d'année
     * @param etudiantId l'identifiant de l'étudiant
     * @param semestre le numéro du semestre (ex: 1, 2)
     * @return la moyenne du semestre (ex: 12.75)
     */
    public double calculerMoyenneSemestre(Long etudiantId, Integer semestre) {
        return noteDAO.calculerMoyenneSemestre(etudiantId, semestre);  // Calcule la moyenne pondérée pour ce semestre uniquement
    }

    /**
     * Obtenir les statistiques complètes d'un étudiant
     * Calcule moyenne, note minimale, note maximale et nombre total de notes
     * @param etudiantId l'identifiant de l'étudiant
     * @return un tableau de statistiques [moyenne, note min, note max, nombre de notes]
     */
    public double[] getStatistiquesEtudiant(Long etudiantId) {
        List<Note> notes = noteDAO.findByEtudiant(etudiantId);  // Récupère toutes les notes de l'étudiant

        if (notes == null || notes.isEmpty()) {  // Si aucune note
            return new double[]{0.0, 0.0, 0.0, 0.0};  // Retourne des zéros [moyenne=0, min=0, max=0, count=0]
        }

        double moyenne = calculerMoyenne(etudiantId);  // Calcule la moyenne pondérée
        double min = notes.stream().mapToDouble(Note::getValeur).min().orElse(0.0);  // Trouve la note minimale (Stream API)
        double max = notes.stream().mapToDouble(Note::getValeur).max().orElse(0.0);  // Trouve la note maximale (Stream API)
        double count = notes.size();  // Compte le nombre de notes

        return new double[]{moyenne, min, max, count};  // Retourne le tableau [moyenne, min, max, count]
    }

    /**
     * Récupérer toutes les matières distinctes
     * Utile pour afficher un menu déroulant des matières disponibles
     * @return liste des matières uniques (ex: ["Java EE", "Mathématiques", "UML"])
     */
    public List<String> getToutesMatieres() {
        return noteDAO.findAllMatieres();  // Délègue au DAO qui fait SELECT DISTINCT matiere
    }

    /**
     * Compter le nombre de notes d'un étudiant
     * Utile pour savoir combien d'évaluations un étudiant a passées
     * @param etudiantId l'identifiant de l'étudiant
     * @return le nombre de notes (ex: 12)
     */
    public long compterNotesEtudiant(Long etudiantId) {
        return noteDAO.countByEtudiant(etudiantId);  // Délègue au DAO qui fait SELECT COUNT(*)
    }

    /**
     * Vérifier si un étudiant a réussi son année
     * Un étudiant réussit si sa moyenne générale est >= 10
     * @param etudiantId l'identifiant de l'étudiant
     * @return true si l'étudiant a réussi (moyenne >= 10), false sinon
     */
    public boolean etudiantAReussi(Long etudiantId) {
        double moyenne = calculerMoyenne(etudiantId);  // Calcule la moyenne générale
        return moyenne >= 10.0;  // Retourne true si >= 10 (seuil de réussite en France)
    }

    /**
     * Obtenir l'appréciation textuelle selon la moyenne
     * Barème français standard pour les mentions
     * @param moyenne la moyenne (0-20)
     * @return l'appréciation ("Très Bien", "Bien", "Assez Bien", "Passable" ou "Insuffisant")
     */
    public String getAppreciation(double moyenne) {
        if (moyenne >= 16.0) {  // Si moyenne >= 16
            return "Très Bien";  // Mention Très Bien
        } else if (moyenne >= 14.0) {  // Si moyenne >= 14
            return "Bien";  // Mention Bien
        } else if (moyenne >= 12.0) {  // Si moyenne >= 12
            return "Assez Bien";  // Mention Assez Bien
        } else if (moyenne >= 10.0) {  // Si moyenne >= 10
            return "Passable";  // Admis sans mention
        } else {  // Si moyenne < 10
            return "Insuffisant";  // Échec
        }
    }
}
