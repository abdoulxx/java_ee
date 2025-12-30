package com.iua.gestionetudiants.service;

import com.iua.gestionetudiants.dao.EtudiantDAO;
import com.iua.gestionetudiants.dao.NoteDAO;
import com.iua.gestionetudiants.model.Etudiant;
import com.iua.gestionetudiants.model.Note;

import java.util.Date;
import java.util.List;

/**
 * Service métier pour la gestion des notes
 * Couche Métier - Logique métier et règles de gestion
 */
public class NoteService {

    private NoteDAO noteDAO;
    private EtudiantDAO etudiantDAO;

    public NoteService() {
        this.noteDAO = new NoteDAO();
        this.etudiantDAO = new EtudiantDAO();
    }

    /**
     * Créer une nouvelle note
     * @param note la note à créer
     * @throws IllegalArgumentException si les données sont invalides
     */
    public void creerNote(Note note) {
        // Validation des données
        if (note == null) {
            throw new IllegalArgumentException("La note ne peut pas être null");
        }
        if (note.getMatiere() == null || note.getMatiere().trim().isEmpty()) {
            throw new IllegalArgumentException("La matière est obligatoire");
        }
        if (note.getValeur() == null) {
            throw new IllegalArgumentException("La valeur de la note est obligatoire");
        }
        if (note.getValeur() < 0 || note.getValeur() > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20");
        }
        if (note.getCoefficient() == null || note.getCoefficient() <= 0) {
            throw new IllegalArgumentException("Le coefficient doit être supérieur à 0");
        }
        if (note.getEtudiant() == null || note.getEtudiant().getId() == null) {
            throw new IllegalArgumentException("L'étudiant est obligatoire");
        }

        // Vérifier que l'étudiant existe
        Etudiant etudiant = etudiantDAO.findById(note.getEtudiant().getId());
        if (etudiant == null) {
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + note.getEtudiant().getId());
        }

        // Définir la date d'évaluation si non définie
        if (note.getDateEvaluation() == null) {
            note.setDateEvaluation(new Date());
        }

        noteDAO.create(note);
    }

    /**
     * Ajouter une note à un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @param matiere la matière
     * @param valeur la valeur de la note
     * @param coefficient le coefficient
     * @throws IllegalArgumentException si les données sont invalides
     */
    public void ajouterNote(Long etudiantId, String matiere, Double valeur, Double coefficient) {
        // Récupérer l'étudiant
        Etudiant etudiant = etudiantDAO.findById(etudiantId);
        if (etudiant == null) {
            throw new IllegalArgumentException("Étudiant non trouvé avec l'ID: " + etudiantId);
        }

        // Créer la note
        Note note = new Note(matiere, valeur, coefficient);
        note.setEtudiant(etudiant);
        note.setDateEvaluation(new Date());

        // Valider et créer
        creerNote(note);
    }

    /**
     * Mettre à jour une note existante
     * @param note la note à mettre à jour
     * @return la note mise à jour
     * @throws IllegalArgumentException si les données sont invalides
     */
    public Note modifierNote(Note note) {
        // Validation
        if (note == null || note.getId() == null) {
            throw new IllegalArgumentException("La note ou son ID ne peut pas être null");
        }
        if (note.getMatiere() == null || note.getMatiere().trim().isEmpty()) {
            throw new IllegalArgumentException("La matière est obligatoire");
        }
        if (note.getValeur() == null || note.getValeur() < 0 || note.getValeur() > 20) {
            throw new IllegalArgumentException("La note doit être entre 0 et 20");
        }
        if (note.getCoefficient() == null || note.getCoefficient() <= 0) {
            throw new IllegalArgumentException("Le coefficient doit être supérieur à 0");
        }

        // Vérifier que la note existe
        Note existante = noteDAO.findById(note.getId());
        if (existante == null) {
            throw new IllegalArgumentException("Note non trouvée avec l'ID: " + note.getId());
        }

        return noteDAO.update(note);
    }

    /**
     * Supprimer une note
     * @param id l'identifiant de la note à supprimer
     * @throws IllegalArgumentException si la note n'existe pas
     */
    public void supprimerNote(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        Note note = noteDAO.findById(id);
        if (note == null) {
            throw new IllegalArgumentException("Note non trouvée avec l'ID: " + id);
        }

        noteDAO.delete(id);
    }

    /**
     * Récupérer une note par son ID
     * @param id l'identifiant de la note
     * @return la note ou null si non trouvée
     */
    public Note getNote(Long id) {
        return noteDAO.findById(id);
    }

    /**
     * Récupérer toutes les notes
     * @return liste de toutes les notes
     */
    public List<Note> getToutesLesNotes() {
        return noteDAO.findAllWithEtudiants();
    }

    /**
     * Récupérer les notes d'un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @return liste des notes de l'étudiant
     */
    public List<Note> getNotesParEtudiant(Long etudiantId) {
        return noteDAO.findByEtudiant(etudiantId);
    }

    /**
     * Récupérer les notes d'un étudiant pour une matière
     * @param etudiantId l'identifiant de l'étudiant
     * @param matiere la matière
     * @return liste des notes
     */
    public List<Note> getNotesParEtudiantEtMatiere(Long etudiantId, String matiere) {
        return noteDAO.findByEtudiantAndMatiere(etudiantId, matiere);
    }

    /**
     * Récupérer les notes d'un étudiant pour un semestre
     * @param etudiantId l'identifiant de l'étudiant
     * @param semestre le numéro du semestre
     * @return liste des notes du semestre
     */
    public List<Note> getNotesParSemestre(Long etudiantId, Integer semestre) {
        return noteDAO.findByEtudiantAndSemestre(etudiantId, semestre);
    }

    /**
     * Calculer la moyenne générale d'un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @return la moyenne de l'étudiant
     */
    public double calculerMoyenne(Long etudiantId) {
        return noteDAO.calculerMoyenne(etudiantId);
    }

    /**
     * Calculer la moyenne d'un étudiant pour un semestre
     * @param etudiantId l'identifiant de l'étudiant
     * @param semestre le numéro du semestre
     * @return la moyenne du semestre
     */
    public double calculerMoyenneSemestre(Long etudiantId, Integer semestre) {
        return noteDAO.calculerMoyenneSemestre(etudiantId, semestre);
    }

    /**
     * Obtenir les statistiques d'un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @return un tableau de statistiques [moyenne, note min, note max, nombre de notes]
     */
    public double[] getStatistiquesEtudiant(Long etudiantId) {
        List<Note> notes = noteDAO.findByEtudiant(etudiantId);

        if (notes == null || notes.isEmpty()) {
            return new double[]{0.0, 0.0, 0.0, 0.0};
        }

        double moyenne = calculerMoyenne(etudiantId);
        double min = notes.stream().mapToDouble(Note::getValeur).min().orElse(0.0);
        double max = notes.stream().mapToDouble(Note::getValeur).max().orElse(0.0);
        double count = notes.size();

        return new double[]{moyenne, min, max, count};
    }

    /**
     * Récupérer toutes les matières
     * @return liste des matières
     */
    public List<String> getToutesMatieres() {
        return noteDAO.findAllMatieres();
    }

    /**
     * Compter le nombre de notes d'un étudiant
     * @param etudiantId l'identifiant de l'étudiant
     * @return le nombre de notes
     */
    public long compterNotesEtudiant(Long etudiantId) {
        return noteDAO.countByEtudiant(etudiantId);
    }

    /**
     * Vérifier si un étudiant a réussi (moyenne >= 10)
     * @param etudiantId l'identifiant de l'étudiant
     * @return true si l'étudiant a réussi
     */
    public boolean etudiantAReussi(Long etudiantId) {
        double moyenne = calculerMoyenne(etudiantId);
        return moyenne >= 10.0;
    }

    /**
     * Obtenir l'appréciation selon la moyenne
     * @param moyenne la moyenne
     * @return l'appréciation
     */
    public String getAppreciation(double moyenne) {
        if (moyenne >= 16.0) {
            return "Très Bien";
        } else if (moyenne >= 14.0) {
            return "Bien";
        } else if (moyenne >= 12.0) {
            return "Assez Bien";
        } else if (moyenne >= 10.0) {
            return "Passable";
        } else {
            return "Insuffisant";
        }
    }
}
