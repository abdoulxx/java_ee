package com.iua.gestionetudiants.controller;

import com.iua.gestionetudiants.model.Etudiant;
import com.iua.gestionetudiants.service.EtudiantService;

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
 * Servlet contrôleur pour la gestion des étudiants
 * Couche Contrôleur - Gestion des requêtes HTTP
 */
public class EtudiantServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EtudiantService etudiantService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        super.init();
        etudiantService = new EtudiantService();
    }

    /**
     * Gestion des requêtes GET
     * - Afficher la liste des étudiants
     * - Afficher le formulaire d'ajout
     * - Afficher le formulaire de modification
     * - Afficher les détails d'un étudiant
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("liste")) {
                listerEtudiants(request, response);
            } else if (action.equals("ajouter")) {
                afficherFormulaireAjout(request, response);
            } else if (action.equals("modifier")) {
                afficherFormulaireModification(request, response);
            } else if (action.equals("details")) {
                afficherDetailsEtudiant(request, response);
            } else if (action.equals("supprimer")) {
                supprimerEtudiant(request, response);
            } else {
                listerEtudiants(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);
        }
    }

    /**
     * Gestion des requêtes POST
     * - Créer un étudiant
     * - Modifier un étudiant
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action != null && action.equals("creer")) {
                creerEtudiant(request, response);
            } else if (action != null && action.equals("update")) {
                modifierEtudiant(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/etudiants");
            }
        } catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);
        }
    }

    /**
     * Afficher la liste de tous les étudiants
     */
    private void listerEtudiants(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String recherche = request.getParameter("recherche");
        List<Etudiant> etudiants;

        if (recherche != null && !recherche.trim().isEmpty()) {
            etudiants = etudiantService.rechercherParNom(recherche);
        } else {
            etudiants = etudiantService.getTousLesEtudiants();
        }

        request.setAttribute("etudiants", etudiants);
        request.setAttribute("recherche", recherche);
        request.getRequestDispatcher("/jsp/etudiants.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire d'ajout d'un étudiant
     */
    private void afficherFormulaireAjout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> filieres = etudiantService.getToutesFilieres();
        request.setAttribute("filieres", filieres);
        request.getRequestDispatcher("/jsp/ajouterEtudiant.jsp").forward(request, response);
    }

    /**
     * Afficher le formulaire de modification d'un étudiant
     */
    private void afficherFormulaireModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Etudiant etudiant = etudiantService.getEtudiant(id);

        if (etudiant == null) {
            request.setAttribute("erreur", "Étudiant non trouvé");
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);
            return;
        }

        List<String> filieres = etudiantService.getToutesFilieres();
        request.setAttribute("etudiant", etudiant);
        request.setAttribute("filieres", filieres);
        request.getRequestDispatcher("/jsp/modifierEtudiant.jsp").forward(request, response);
    }

    /**
     * Afficher les détails d'un étudiant avec ses notes
     */
    private void afficherDetailsEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Etudiant etudiant = etudiantService.getEtudiantAvecNotes(id);

        if (etudiant == null) {
            request.setAttribute("erreur", "Étudiant non trouvé");
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);
            return;
        }

        double moyenne = etudiantService.calculerMoyenne(id);
        request.setAttribute("etudiant", etudiant);
        request.setAttribute("moyenne", moyenne);
        request.getRequestDispatcher("/jsp/detailsEtudiant.jsp").forward(request, response);
    }

    /**
     * Créer un nouvel étudiant
     */
    private void creerEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Etudiant etudiant = new Etudiant();
            etudiant.setNom(request.getParameter("nom"));
            etudiant.setPrenom(request.getParameter("prenom"));
            etudiant.setEmail(request.getParameter("email"));
            etudiant.setNumeroEtudiant(request.getParameter("numeroEtudiant"));
            etudiant.setFiliere(request.getParameter("filiere"));

            // Conversion de la date
            String dateNaissanceStr = request.getParameter("dateNaissance");
            if (dateNaissanceStr != null && !dateNaissanceStr.trim().isEmpty()) {
                try {
                    Date dateNaissance = dateFormat.parse(dateNaissanceStr);
                    etudiant.setDateNaissance(dateNaissance);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Format de date invalide");
                }
            }

            etudiantService.creerEtudiant(etudiant);

            request.getSession().setAttribute("succes", "Étudiant créé avec succès");
            response.sendRedirect(request.getContextPath() + "/etudiants");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erreur", e.getMessage());
            afficherFormulaireAjout(request, response);
        }
    }

    /**
     * Modifier un étudiant existant
     */
    private void modifierEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Etudiant etudiant = etudiantService.getEtudiant(id);

            if (etudiant == null) {
                throw new IllegalArgumentException("Étudiant non trouvé");
            }

            etudiant.setNom(request.getParameter("nom"));
            etudiant.setPrenom(request.getParameter("prenom"));
            etudiant.setEmail(request.getParameter("email"));
            etudiant.setNumeroEtudiant(request.getParameter("numeroEtudiant"));
            etudiant.setFiliere(request.getParameter("filiere"));

            // Conversion de la date
            String dateNaissanceStr = request.getParameter("dateNaissance");
            if (dateNaissanceStr != null && !dateNaissanceStr.trim().isEmpty()) {
                try {
                    Date dateNaissance = dateFormat.parse(dateNaissanceStr);
                    etudiant.setDateNaissance(dateNaissance);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Format de date invalide");
                }
            }

            etudiantService.modifierEtudiant(etudiant);

            request.getSession().setAttribute("succes", "Étudiant modifié avec succès");
            response.sendRedirect(request.getContextPath() + "/etudiants");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erreur", e.getMessage());
            afficherFormulaireModification(request, response);
        }
    }

    /**
     * Supprimer un étudiant
     */
    private void supprimerEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            etudiantService.supprimerEtudiant(id);

            request.getSession().setAttribute("succes", "Étudiant supprimé avec succès");
            response.sendRedirect(request.getContextPath() + "/etudiants");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erreur", e.getMessage());
            listerEtudiants(request, response);
        }
    }
}
