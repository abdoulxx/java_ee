package com.iua.gestionetudiants.controller;

import com.iua.gestionetudiants.model.Etudiant;
import com.iua.gestionetudiants.model.Note;
import com.iua.gestionetudiants.service.EtudiantService;
import com.iua.gestionetudiants.service.NoteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Servlet contrôleur pour la gestion des notes
 * Couche Contrôleur - Gestion des requêtes HTTP
 */
public class NoteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private NoteService noteService;
    private EtudiantService etudiantService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        super.init();
        noteService = new NoteService();
        etudiantService = new EtudiantService();
    }

    /**
     * Gestion des requêtes GET
     * - Afficher les notes d'un étudiant
     * - Afficher le formulaire d'ajout de note
     * - Afficher le formulaire de modification
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("liste")) {
                listerNotes(request, response);
            } else if (action.equals("ajouter")) {
                afficherFormulaireAjout(request, response);
            } else if (action.equals("modifier")) {
                afficherFormulaireModification(request, response);
            } else if (action.equals("supprimer")) {
                supprimerNote(request, response);
            } else {
                listerNotes(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);
        }
    }

    /**
     * Gestion des requêtes POST
     * - Créer une note
     * - Modifier une note
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action != null && action.equals("creer")) {
                creerNote(request, response);
            } else if (action != null && action.equals("update")) {
                modifierNote(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/notes");
            }
        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);
        }
    }

    /**
     * Afficher les notes (toutes ou filtrées par étudiant)
     */
    private void listerNotes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String etudiantIdStr = request.getParameter("etudiantId");
        List<Note> notes;
        Etudiant etudiant = null;
        Double moyenne = null;

        if (etudiantIdStr != null && !etudiantIdStr.trim().isEmpty()) {
            Long etudiantId = Long.parseLong(etudiantIdStr);
            etudiant = etudiantService.getEtudiantAvecNotes(etudiantId);
            notes = noteService.getNotesParEtudiant(etudiantId);
            moyenne = noteService.calculerMoyenne(etudiantId);
        } else {
            notes = noteService.getToutesLesNotes();
        }

        List<Etudiant> etudiants = etudiantService.getTousLesEtudiants();

        request.setAttribute("notes", notes);
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("etudiantSelectionne", etudiant);
        request.setAttribute("moyenne", moyenne);
        request.getRequestDispatcher("/jsp/notes.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire d'ajout d'une note
     */
    private void afficherFormulaireAjout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Etudiant> etudiants = etudiantService.getTousLesEtudiants();
        List<String> matieres = noteService.getToutesMatieres();

        // Pré-sélectionner un étudiant si fourni
        String etudiantIdStr = request.getParameter("etudiantId");
        Etudiant etudiantSelectionne = null;
        if (etudiantIdStr != null && !etudiantIdStr.trim().isEmpty()) {
            Long etudiantId = Long.parseLong(etudiantIdStr);
            etudiantSelectionne = etudiantService.getEtudiant(etudiantId);
        }

        request.setAttribute("etudiants", etudiants);
        request.setAttribute("matieres", matieres);
        request.setAttribute("etudiantSelectionne", etudiantSelectionne);
        request.getRequestDispatcher("/jsp/ajouterNote.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire de modification d'une note
     */
    private void afficherFormulaireModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Note note = noteService.getNote(id);

        if (note == null) {
            request.setAttribute("erreur", "Note non trouvée");
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);
            return;
        }

        List<Etudiant> etudiants = etudiantService.getTousLesEtudiants();
        List<String> matieres = noteService.getToutesMatieres();

        request.setAttribute("note", note);
        request.setAttribute("etudiants", etudiants);
        request.setAttribute("matieres", matieres);
        request.getRequestDispatcher("/jsp/modifierNote.jsp").forward(request, response);
    }

    /**
     * Créer une nouvelle note
     */
    private void creerNote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long etudiantId = Long.parseLong(request.getParameter("etudiantId"));
            Etudiant etudiant = etudiantService.getEtudiant(etudiantId);

            if (etudiant == null) {
                throw new IllegalArgumentException("Étudiant non trouvé");
            }

            Note note = new Note();
            note.setEtudiant(etudiant);
            note.setMatiere(request.getParameter("matiere"));
            note.setValeur(Double.parseDouble(request.getParameter("valeur")));
            note.setCoefficient(Double.parseDouble(request.getParameter("coefficient")));

            // Semestre (optionnel)
            String semestreStr = request.getParameter("semestre");
            if (semestreStr != null && !semestreStr.trim().isEmpty()) {
                note.setSemestre(Integer.parseInt(semestreStr));
            }

            // Commentaire (optionnel)
            String commentaire = request.getParameter("commentaire");
            if (commentaire != null && !commentaire.trim().isEmpty()) {
                note.setCommentaire(commentaire);
            }

            // Date d'évaluation (optionnelle)
            String dateEvaluationStr = request.getParameter("dateEvaluation");
            if (dateEvaluationStr != null && !dateEvaluationStr.trim().isEmpty()) {
                try {
                    Date dateEvaluation = dateFormat.parse(dateEvaluationStr);
                    note.setDateEvaluation(dateEvaluation);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Format de date invalide");
                }
            } else {
                note.setDateEvaluation(new Date());
            }

            noteService.creerNote(note);

            request.getSession().setAttribute("succes", "Note ajoutée avec succès");
            response.sendRedirect(request.getContextPath() + "/notes?etudiantId=" + etudiantId);

        } catch (IllegalArgumentException e) {
            request.setAttribute("erreur", e.getMessage());
            afficherFormulaireAjout(request, response);
        }
    }

    /**
     * Modifier une note existante
     */
    private void modifierNote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Note note = noteService.getNote(id);

            if (note == null) {
                throw new IllegalArgumentException("Note non trouvée");
            }

            Long etudiantId = Long.parseLong(request.getParameter("etudiantId"));
            Etudiant etudiant = etudiantService.getEtudiant(etudiantId);
            note.setEtudiant(etudiant);

            note.setMatiere(request.getParameter("matiere"));
            note.setValeur(Double.parseDouble(request.getParameter("valeur")));
            note.setCoefficient(Double.parseDouble(request.getParameter("coefficient")));

            // Semestre (optionnel)
            String semestreStr = request.getParameter("semestre");
            if (semestreStr != null && !semestreStr.trim().isEmpty()) {
                note.setSemestre(Integer.parseInt(semestreStr));
            }

            // Commentaire (optionnel)
            String commentaire = request.getParameter("commentaire");
            note.setCommentaire(commentaire);

            // Date d'évaluation
            String dateEvaluationStr = request.getParameter("dateEvaluation");
            if (dateEvaluationStr != null && !dateEvaluationStr.trim().isEmpty()) {
                try {
                    Date dateEvaluation = dateFormat.parse(dateEvaluationStr);
                    note.setDateEvaluation(dateEvaluation);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Format de date invalide");
                }
            }

            noteService.modifierNote(note);

            request.getSession().setAttribute("succes", "Note modifiée avec succès");
            response.sendRedirect(request.getContextPath() + "/notes?etudiantId=" + etudiantId);

        } catch (IllegalArgumentException e) {
            request.setAttribute("erreur", e.getMessage());
            afficherFormulaireModification(request, response);
        }
    }

    /**
     * Supprimer une note
     */
    private void supprimerNote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Note note = noteService.getNote(id);

            if (note == null) {
                throw new IllegalArgumentException("Note non trouvée");
            }

            Long etudiantId = note.getEtudiant().getId();
            noteService.supprimerNote(id);

            request.getSession().setAttribute("succes", "Note supprimée avec succès");
            response.sendRedirect(request.getContextPath() + "/notes?etudiantId=" + etudiantId);

        } catch (IllegalArgumentException e) {
            request.setAttribute("erreur", e.getMessage());
            listerNotes(request, response);
        }
    }
}
