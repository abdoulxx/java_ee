package com.iua.gestionetudiants.controller;  // Package pour les contrôleurs (Servlets)

import com.iua.gestionetudiants.model.Etudiant;  // Import de l'entité Etudiant
import com.iua.gestionetudiants.service.EtudiantService;  // Import du service métier

import javax.servlet.ServletException;  // Exception servlet
import javax.servlet.http.HttpServlet;  // Classe de base pour les servlets HTTP
import javax.servlet.http.HttpServletRequest;  // Représente la requête HTTP
import javax.servlet.http.HttpServletResponse;  // Représente la réponse HTTP
import java.io.IOException;  // Exception d'entrée/sortie
import java.text.ParseException;  // Exception de parsing de date
import java.text.SimpleDateFormat;  // Formateur de dates
import java.util.Date;  // Type Date
import java.util.List;  // Type List

/**
 * Servlet contrôleur pour la gestion des étudiants
 * Couche Contrôleur (MVC) - Gestion des requêtes HTTP
 * URL mappée dans web.xml: /etudiants
 */
public class EtudiantServlet extends HttpServlet {  // Hérite de HttpServlet

    private static final long serialVersionUID = 1L;  // Identifiant de version
    private EtudiantService etudiantService;  // Service pour la logique métier
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Format de date HTML5

    @Override
    public void init() throws ServletException {  // Méthode d'initialisation du servlet
        super.init();  // Appelle l'init parent
        etudiantService = new EtudiantService();  // Crée une instance du service
    }

    /**
     * Gestion des requêtes GET (affichage)
     * URLs possibles:
     * - /etudiants → liste
     * - /etudiants?action=ajouter → formulaire d'ajout
     * - /etudiants?action=modifier&id=1 → formulaire de modification
     * - /etudiants?action=details&id=1 → détails d'un étudiant
     * - /etudiants?action=supprimer&id=1 → suppression
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");  // Récupère le paramètre action de l'URL

        try {
            if (action == null || action.equals("liste")) {  // Si pas d'action ou action=liste
                listerEtudiants(request, response);  // Affiche la liste
            } else if (action.equals("ajouter")) {  // Si action=ajouter
                afficherFormulaireAjout(request, response);  // Affiche formulaire d'ajout
            } else if (action.equals("modifier")) {  // Si action=modifier
                afficherFormulaireModification(request, response);  // Affiche formulaire de modification
            } else if (action.equals("details")) {  // Si action=details
                afficherDetailsEtudiant(request, response);  // Affiche les détails
            } else if (action.equals("supprimer")) {  // Si action=supprimer
                supprimerEtudiant(request, response);  // Supprime l'étudiant
            } else {  // Action inconnue
                listerEtudiants(request, response);  // Affiche la liste par défaut
            }
        } catch (Exception e) {  // Si une erreur se produit
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);  // Affiche la page d'erreur
        }
    }

    /**
     * Gestion des requêtes POST (soumission de formulaire)
     * - POST /etudiants?action=creer → Créer un étudiant
     * - POST /etudiants?action=update → Modifier un étudiant
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");  // Récupère le paramètre action

        try {
            if (action != null && action.equals("creer")) {  // Si action=creer
                creerEtudiant(request, response);  // Crée un nouvel étudiant
            } else if (action != null && action.equals("update")) {  // Si action=update
                modifierEtudiant(request, response);  // Modifie l'étudiant existant
            } else {  // Action inconnue
                response.sendRedirect(request.getContextPath() + "/etudiants");  // Redirige vers la liste
            }
        } catch (Exception e) {  // Si une erreur se produit
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);  // Affiche la page d'erreur
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
