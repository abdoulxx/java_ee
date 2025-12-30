package com.iua.gestionetudiants.controller;  // Package pour les servlets (contrôleurs)

import com.iua.gestionetudiants.model.Etudiant;  // Import de l'entité Etudiant
import com.iua.gestionetudiants.model.Note;  // Import de l'entité Note
import com.iua.gestionetudiants.service.EtudiantService;  // Import du service pour la logique métier des étudiants
import com.iua.gestionetudiants.service.NoteService;  // Import du service pour la logique métier des notes

import javax.servlet.ServletException;  // Exception pour les erreurs servlet
import javax.servlet.http.HttpServlet;  // Classe de base pour les servlets HTTP
import javax.servlet.http.HttpServletRequest;  // Représente la requête HTTP
import javax.servlet.http.HttpServletResponse;  // Représente la réponse HTTP
import java.io.IOException;  // Exception pour les erreurs d'entrée/sortie
import java.text.ParseException;  // Exception pour les erreurs de parsing de date
import java.text.SimpleDateFormat;  // Classe pour formater/parser les dates
import java.util.Date;  // Type Date pour les dates
import java.util.List;  // Interface List pour les collections

/**
 * Servlet contrôleur pour la gestion des notes
 * Couche Contrôleur - Gestion des requêtes HTTP pour les opérations CRUD sur les notes
 * URL mappée: /notes (définie dans web.xml ou avec @WebServlet)
 */
public class NoteServlet extends HttpServlet {  // Hérite de HttpServlet pour gérer les requêtes HTTP

    private static final long serialVersionUID = 1L;  // Identifiant de version pour la sérialisation
    private NoteService noteService;  // Service pour la logique métier des notes
    private EtudiantService etudiantService;  // Service pour accéder aux étudiants
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Format de date: 2024-12-30

    @Override
    public void init() throws ServletException {  // Méthode d'initialisation du servlet (appelée une seule fois au démarrage)
        super.init();  // Appelle l'init parent
        noteService = new NoteService();  // Crée une instance du NoteService
        etudiantService = new EtudiantService();  // Crée une instance de l'EtudiantService
    }

    /**
     * Gestion des requêtes GET (lecture et affichage)
     * Traite les actions: liste, ajouter, modifier, supprimer
     * Pattern: URL?action=xxx (ex: /notes?action=liste)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        String action = request.getParameter("action");  // Récupère le paramètre "action" de l'URL (ex: ?action=liste)

        try {  // Gestion des erreurs avec try-catch
            if (action == null || action.equals("liste")) {  // Si pas d'action OU action=liste
                listerNotes(request, response);  // Affiche la liste des notes
            } else if (action.equals("ajouter")) {  // Si action=ajouter (ex: ?action=ajouter)
                afficherFormulaireAjout(request, response);  // Affiche le formulaire d'ajout
            } else if (action.equals("modifier")) {  // Si action=modifier (ex: ?action=modifier&id=5)
                afficherFormulaireModification(request, response);  // Affiche le formulaire de modification
            } else if (action.equals("supprimer")) {  // Si action=supprimer (ex: ?action=supprimer&id=5)
                supprimerNote(request, response);  // Supprime la note
            } else {  // Si action non reconnue
                listerNotes(request, response);  // Par défaut, affiche la liste
            }
        } catch (Exception e) {  // Si une exception est levée
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur dans la requête
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);  // Redirige vers la page d'erreur
        }
    }

    /**
     * Gestion des requêtes POST (création et modification)
     * Traite les actions: creer, update
     * Les formulaires HTML utilisent method="POST"
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        String action = request.getParameter("action");  // Récupère le paramètre "action" du formulaire (champ hidden)

        try {  // Gestion des erreurs
            if (action != null && action.equals("creer")) {  // Si action=creer (formulaire ajouterNote.jsp)
                creerNote(request, response);  // Crée une nouvelle note
            } else if (action != null && action.equals("update")) {  // Si action=update (formulaire modifierNote.jsp)
                modifierNote(request, response);  // Met à jour la note
            } else {  // Si action non reconnue
                response.sendRedirect(request.getContextPath() + "/notes");  // Redirige vers la liste des notes
            }
        } catch (Exception e) {  // Si une exception est levée
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);  // Redirige vers la page d'erreur
        }
    }

    /**
     * Afficher la liste des notes (toutes ou filtrées par étudiant)
     * URL: /notes ou /notes?etudiantId=5
     * Affiche aussi la moyenne de l'étudiant si filtré
     */
    private void listerNotes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        String etudiantIdStr = request.getParameter("etudiantId");  // Récupère le paramètre etudiantId (optionnel)
        List<Note> notes;  // Déclaration de la liste des notes
        Etudiant etudiant = null;  // Étudiant sélectionné (null par défaut)
        Double moyenne = null;  // Moyenne de l'étudiant (null par défaut)

        if (etudiantIdStr != null && !etudiantIdStr.trim().isEmpty()) {  // Si un étudiant est sélectionné
            Long etudiantId = Long.parseLong(etudiantIdStr);  // Convertit la String en Long
            etudiant = etudiantService.getEtudiantAvecNotes(etudiantId);  // Récupère l'étudiant avec ses notes
            notes = noteService.getNotesParEtudiant(etudiantId);  // Filtre les notes de cet étudiant
            moyenne = noteService.calculerMoyenne(etudiantId);  // Calcule la moyenne de l'étudiant
        } else {  // Si pas d'étudiant sélectionné
            notes = noteService.getToutesLesNotes();  // Récupère toutes les notes de tous les étudiants
        }

        List<Etudiant> etudiants = etudiantService.getTousLesEtudiants();  // Récupère tous les étudiants (pour le menu déroulant)

        request.setAttribute("notes", notes);  // Stocke la liste des notes dans la requête (accessible dans la JSP)
        request.setAttribute("etudiants", etudiants);  // Stocke tous les étudiants (pour le filtre)
        request.setAttribute("etudiantSelectionne", etudiant);  // Stocke l'étudiant sélectionné (pour le titre)
        request.setAttribute("moyenne", moyenne);  // Stocke la moyenne (pour affichage)
        request.getRequestDispatcher("/jsp/notes.jsp").forward(request, response);  // Forward vers notes.jsp pour affichage
    }

    /**
     * Afficher le formulaire d'ajout d'une note
     * URL: /notes?action=ajouter ou /notes?action=ajouter&etudiantId=5
     * Pré-remplit le champ étudiant si etudiantId est fourni
     */
    private void afficherFormulaireAjout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        List<Etudiant> etudiants = etudiantService.getTousLesEtudiants();  // Récupère tous les étudiants (pour le menu déroulant)
        List<String> matieres = noteService.getToutesMatieres();  // Récupère toutes les matières existantes (pour autocomplétion)

        // Pré-sélectionner un étudiant si fourni dans l'URL
        String etudiantIdStr = request.getParameter("etudiantId");  // Récupère etudiantId (paramètre optionnel)
        Etudiant etudiantSelectionne = null;  // Étudiant pré-sélectionné (null par défaut)
        if (etudiantIdStr != null && !etudiantIdStr.trim().isEmpty()) {  // Si etudiantId est fourni
            Long etudiantId = Long.parseLong(etudiantIdStr);  // Convertit String → Long
            etudiantSelectionne = etudiantService.getEtudiant(etudiantId);  // Récupère l'étudiant pour pré-remplir le formulaire
        }

        request.setAttribute("etudiants", etudiants);  // Stocke la liste des étudiants (pour le <select>)
        request.setAttribute("matieres", matieres);  // Stocke les matières (pour le <datalist>)
        request.setAttribute("etudiantSelectionne", etudiantSelectionne);  // Stocke l'étudiant pré-sélectionné
        request.getRequestDispatcher("/jsp/ajouterNote.jsp").forward(request, response);  // Forward vers le formulaire JSP
    }

    /**
     * Afficher le formulaire de modification d'une note
     * URL: /notes?action=modifier&id=5
     * Pré-remplit tous les champs avec les données de la note existante
     */
    private void afficherFormulaireModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        Long id = Long.parseLong(request.getParameter("id"));  // Récupère l'ID de la note à modifier (paramètre obligatoire)
        Note note = noteService.getNote(id);  // Récupère la note en base de données

        if (note == null) {  // Si la note n'existe pas
            request.setAttribute("erreur", "Note non trouvée");  // Stocke le message d'erreur
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);  // Redirige vers la page d'erreur
            return;  // Arrête l'exécution
        }

        List<Etudiant> etudiants = etudiantService.getTousLesEtudiants();  // Récupère tous les étudiants (pour le menu déroulant)
        List<String> matieres = noteService.getToutesMatieres();  // Récupère toutes les matières (pour le <datalist>)

        request.setAttribute("note", note);  // Stocke la note à modifier (pour pré-remplir le formulaire)
        request.setAttribute("etudiants", etudiants);  // Stocke la liste des étudiants
        request.setAttribute("matieres", matieres);  // Stocke les matières
        request.getRequestDispatcher("/jsp/modifierNote.jsp").forward(request, response);  // Forward vers le formulaire de modification
    }

    /**
     * Créer une nouvelle note (INSERT en base de données)
     * Méthode appelée par doPost() lorsque action=creer
     * Récupère les données du formulaire, crée l'objet Note, et l'insère en base
     */
    private void creerNote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        try {  // Gestion des erreurs
            Long etudiantId = Long.parseLong(request.getParameter("etudiantId"));  // Récupère etudiantId du formulaire (champ <select>)
            Etudiant etudiant = etudiantService.getEtudiant(etudiantId);  // Récupère l'étudiant en base de données

            if (etudiant == null) {  // Validation: vérifier que l'étudiant existe
                throw new IllegalArgumentException("Étudiant non trouvé");  // Lance une exception si étudiant introuvable
            }

            Note note = new Note();  // Crée un nouvel objet Note vide
            note.setEtudiant(etudiant);  // Associe l'étudiant à la note (relation ManyToOne)
            note.setMatiere(request.getParameter("matiere"));  // Récupère la matière du formulaire (champ texte)
            note.setValeur(Double.parseDouble(request.getParameter("valeur")));  // Récupère la valeur et convertit String → Double
            note.setCoefficient(Double.parseDouble(request.getParameter("coefficient")));  // Récupère coefficient et convertit String → Double

            // Semestre (optionnel)
            String semestreStr = request.getParameter("semestre");  // Récupère le semestre (peut être vide)
            if (semestreStr != null && !semestreStr.trim().isEmpty()) {  // Si le semestre est renseigné
                note.setSemestre(Integer.parseInt(semestreStr));  // Convertit String → Integer et stocke
            }

            // Commentaire (optionnel)
            String commentaire = request.getParameter("commentaire");  // Récupère le commentaire (peut être vide)
            if (commentaire != null && !commentaire.trim().isEmpty()) {  // Si le commentaire est renseigné
                note.setCommentaire(commentaire);  // Stocke le commentaire
            }

            // Date d'évaluation (optionnelle)
            String dateEvaluationStr = request.getParameter("dateEvaluation");  // Récupère la date (format yyyy-MM-dd)
            if (dateEvaluationStr != null && !dateEvaluationStr.trim().isEmpty()) {  // Si une date est fournie
                try {
                    Date dateEvaluation = dateFormat.parse(dateEvaluationStr);  // Parse la String en Date (ex: "2024-12-30" → Date)
                    note.setDateEvaluation(dateEvaluation);  // Stocke la date
                } catch (ParseException e) {  // Si le format de date est invalide
                    throw new IllegalArgumentException("Format de date invalide");  // Lance une exception
                }
            } else {  // Si pas de date fournie
                note.setDateEvaluation(new Date());  // Utilise la date du jour (new Date() = maintenant)
            }

            noteService.creerNote(note);  // Appelle le service qui valide et insère en base de données

            request.getSession().setAttribute("succes", "Note ajoutée avec succès");  // Stocke un message de succès dans la session
            response.sendRedirect(request.getContextPath() + "/notes?etudiantId=" + etudiantId);  // Redirige vers les notes de l'étudiant (pattern POST-Redirect-GET)

        } catch (IllegalArgumentException e) {  // Si une erreur de validation survient
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur dans la requête
            afficherFormulaireAjout(request, response);  // Ré-affiche le formulaire avec le message d'erreur
        }
    }

    /**
     * Modifier une note existante (UPDATE en base de données)
     * Méthode appelée par doPost() lorsque action=update
     * Récupère la note existante, met à jour ses propriétés, et sauvegarde en base
     */
    private void modifierNote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        try {  // Gestion des erreurs
            Long id = Long.parseLong(request.getParameter("id"));  // Récupère l'ID de la note à modifier (champ hidden)
            Note note = noteService.getNote(id);  // Récupère la note existante en base de données

            if (note == null) {  // Validation: vérifier que la note existe
                throw new IllegalArgumentException("Note non trouvée");  // Lance une exception si note introuvable
            }

            Long etudiantId = Long.parseLong(request.getParameter("etudiantId"));  // Récupère etudiantId (peut avoir changé)
            Etudiant etudiant = etudiantService.getEtudiant(etudiantId);  // Récupère l'étudiant
            note.setEtudiant(etudiant);  // Met à jour l'étudiant associé (même si changé dans le formulaire)

            note.setMatiere(request.getParameter("matiere"));  // Met à jour la matière
            note.setValeur(Double.parseDouble(request.getParameter("valeur")));  // Met à jour la valeur (String → Double)
            note.setCoefficient(Double.parseDouble(request.getParameter("coefficient")));  // Met à jour le coefficient (String → Double)

            // Semestre (optionnel)
            String semestreStr = request.getParameter("semestre");  // Récupère le semestre
            if (semestreStr != null && !semestreStr.trim().isEmpty()) {  // Si un semestre est fourni
                note.setSemestre(Integer.parseInt(semestreStr));  // Met à jour le semestre
            }

            // Commentaire (optionnel)
            String commentaire = request.getParameter("commentaire");  // Récupère le commentaire
            note.setCommentaire(commentaire);  // Met à jour le commentaire (peut être vide ou null)

            // Date d'évaluation
            String dateEvaluationStr = request.getParameter("dateEvaluation");  // Récupère la date
            if (dateEvaluationStr != null && !dateEvaluationStr.trim().isEmpty()) {  // Si une date est fournie
                try {
                    Date dateEvaluation = dateFormat.parse(dateEvaluationStr);  // Parse la date (yyyy-MM-dd → Date)
                    note.setDateEvaluation(dateEvaluation);  // Met à jour la date d'évaluation
                } catch (ParseException e) {  // Si le format de date est invalide
                    throw new IllegalArgumentException("Format de date invalide");  // Lance une exception
                }
            }

            noteService.modifierNote(note);  // Appelle le service qui valide et met à jour en base (UPDATE)

            request.getSession().setAttribute("succes", "Note modifiée avec succès");  // Message de succès dans la session
            response.sendRedirect(request.getContextPath() + "/notes?etudiantId=" + etudiantId);  // Redirige vers les notes de l'étudiant (POST-Redirect-GET)

        } catch (IllegalArgumentException e) {  // Si une erreur de validation survient
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur
            afficherFormulaireModification(request, response);  // Ré-affiche le formulaire avec le message d'erreur
        }
    }

    /**
     * Supprimer une note (DELETE en base de données)
     * Méthode appelée par doGet() lorsque action=supprimer&id=5
     * Récupère l'étudiant associé avant suppression pour rediriger vers ses notes
     */
    private void supprimerNote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        try {  // Gestion des erreurs
            Long id = Long.parseLong(request.getParameter("id"));  // Récupère l'ID de la note à supprimer
            Note note = noteService.getNote(id);  // Récupère la note en base de données

            if (note == null) {  // Validation: vérifier que la note existe
                throw new IllegalArgumentException("Note non trouvée");  // Lance une exception si note introuvable
            }

            Long etudiantId = note.getEtudiant().getId();  // Récupère l'ID de l'étudiant AVANT suppression (pour redirection)
            noteService.supprimerNote(id);  // Appelle le service qui supprime la note (DELETE)

            request.getSession().setAttribute("succes", "Note supprimée avec succès");  // Message de succès dans la session
            response.sendRedirect(request.getContextPath() + "/notes?etudiantId=" + etudiantId);  // Redirige vers les notes de l'étudiant

        } catch (IllegalArgumentException e) {  // Si une erreur survient
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur
            listerNotes(request, response);  // Ré-affiche la liste des notes avec le message d'erreur
        }
    }
}
