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
     * Afficher la liste de tous les étudiants (avec recherche optionnelle)
     * URL: /etudiants ou /etudiants?recherche=Dupont
     * Filtre par nom si le paramètre recherche est fourni
     */
    private void listerEtudiants(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        String recherche = request.getParameter("recherche");  // Récupère le paramètre recherche (optionnel)
        List<Etudiant> etudiants;  // Déclaration de la liste des étudiants

        if (recherche != null && !recherche.trim().isEmpty()) {  // Si une recherche est fournie
            etudiants = etudiantService.rechercherParNom(recherche);  // Filtre par nom (LIKE %recherche%)
        } else {  // Si pas de recherche
            etudiants = etudiantService.getTousLesEtudiants();  // Récupère tous les étudiants
        }

        request.setAttribute("etudiants", etudiants);  // Stocke la liste des étudiants dans la requête (pour la JSP)
        request.setAttribute("recherche", recherche);  // Stocke le terme de recherche (pour pré-remplir le champ)
        request.getRequestDispatcher("/jsp/etudiants.jsp").forward(request, response);  // Forward vers etudiants.jsp pour affichage
    }

    /**
     * Afficher le formulaire d'ajout d'un étudiant
     * URL: /etudiants?action=ajouter
     * Charge les filières disponibles pour le menu déroulant
     */
    private void afficherFormulaireAjout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        List<String> filieres = etudiantService.getToutesFilieres();  // Récupère toutes les filières distinctes (ex: [GI, MIAGE])
        request.setAttribute("filieres", filieres);  // Stocke les filières pour le <select> du formulaire
        request.getRequestDispatcher("/jsp/ajouterEtudiant.jsp").forward(request, response);  // Forward vers le formulaire d'ajout
    }

    /**
     * Afficher le formulaire de modification d'un étudiant
     * URL: /etudiants?action=modifier&id=5
     * Pré-remplit le formulaire avec les données existantes de l'étudiant
     */
    private void afficherFormulaireModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        Long id = Long.parseLong(request.getParameter("id"));  // Récupère l'ID de l'étudiant à modifier (paramètre obligatoire)
        Etudiant etudiant = etudiantService.getEtudiant(id);  // Récupère l'étudiant en base de données

        if (etudiant == null) {  // Si l'étudiant n'existe pas
            request.setAttribute("erreur", "Étudiant non trouvé");  // Stocke le message d'erreur
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);  // Redirige vers la page d'erreur
            return;  // Arrête l'exécution
        }

        List<String> filieres = etudiantService.getToutesFilieres();  // Récupère toutes les filières (pour le menu déroulant)
        request.setAttribute("etudiant", etudiant);  // Stocke l'étudiant à modifier (pour pré-remplir le formulaire)
        request.setAttribute("filieres", filieres);  // Stocke les filières disponibles
        request.getRequestDispatcher("/jsp/modifierEtudiant.jsp").forward(request, response);  // Forward vers le formulaire de modification
    }

    /**
     * Afficher les détails d'un étudiant avec ses notes et sa moyenne
     * URL: /etudiants?action=details&id=5
     * Charge l'étudiant avec toutes ses notes (JOIN FETCH) et calcule sa moyenne
     */
    private void afficherDetailsEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        Long id = Long.parseLong(request.getParameter("id"));  // Récupère l'ID de l'étudiant (paramètre obligatoire)
        Etudiant etudiant = etudiantService.getEtudiantAvecNotes(id);  // Récupère l'étudiant avec ses notes (JOIN FETCH pour éviter lazy loading)

        if (etudiant == null) {  // Si l'étudiant n'existe pas
            request.setAttribute("erreur", "Étudiant non trouvé");  // Stocke le message d'erreur
            request.getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);  // Redirige vers la page d'erreur
            return;  // Arrête l'exécution
        }

        double moyenne = etudiantService.calculerMoyenne(id);  // Calcule la moyenne pondérée de l'étudiant
        request.setAttribute("etudiant", etudiant);  // Stocke l'étudiant (avec ses notes chargées)
        request.setAttribute("moyenne", moyenne);  // Stocke la moyenne pour affichage
        request.getRequestDispatcher("/jsp/detailsEtudiant.jsp").forward(request, response);  // Forward vers la page de détails
    }

    /**
     * Créer un nouvel étudiant (INSERT en base de données)
     * Méthode appelée par doPost() lorsque action=creer
     * Récupère les données du formulaire, crée l'objet Etudiant, et l'insère en base
     */
    private void creerEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        try {  // Gestion des erreurs
            Etudiant etudiant = new Etudiant();  // Crée un nouvel objet Etudiant vide
            etudiant.setNom(request.getParameter("nom"));  // Récupère le nom du formulaire (champ texte)
            etudiant.setPrenom(request.getParameter("prenom"));  // Récupère le prénom du formulaire
            etudiant.setEmail(request.getParameter("email"));  // Récupère l'email du formulaire
            etudiant.setNumeroEtudiant(request.getParameter("numeroEtudiant"));  // Récupère le numéro étudiant (ex: ETU2024001)
            etudiant.setFiliere(request.getParameter("filiere"));  // Récupère la filière (ex: GI, MIAGE)

            // Conversion de la date de naissance (optionnelle)
            String dateNaissanceStr = request.getParameter("dateNaissance");  // Récupère la date (format yyyy-MM-dd)
            if (dateNaissanceStr != null && !dateNaissanceStr.trim().isEmpty()) {  // Si une date est fournie
                try {
                    Date dateNaissance = dateFormat.parse(dateNaissanceStr);  // Parse la String en Date (ex: "2000-05-15" → Date)
                    etudiant.setDateNaissance(dateNaissance);  // Stocke la date de naissance
                } catch (ParseException e) {  // Si le format de date est invalide
                    throw new IllegalArgumentException("Format de date invalide");  // Lance une exception
                }
            }

            etudiantService.creerEtudiant(etudiant);  // Appelle le service qui valide et insère en base de données (INSERT)

            request.getSession().setAttribute("succes", "Étudiant créé avec succès");  // Stocke un message de succès dans la session
            response.sendRedirect(request.getContextPath() + "/etudiants");  // Redirige vers la liste des étudiants (pattern POST-Redirect-GET)

        } catch (IllegalArgumentException e) {  // Si une erreur de validation survient (ex: email déjà existant)
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur dans la requête
            afficherFormulaireAjout(request, response);  // Ré-affiche le formulaire avec le message d'erreur
        }
    }

    /**
     * Modifier un étudiant existant (UPDATE en base de données)
     * Méthode appelée par doPost() lorsque action=update
     * Récupère l'étudiant existant, met à jour ses propriétés, et sauvegarde en base
     */
    private void modifierEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        try {  // Gestion des erreurs
            Long id = Long.parseLong(request.getParameter("id"));  // Récupère l'ID de l'étudiant à modifier (champ hidden du formulaire)
            Etudiant etudiant = etudiantService.getEtudiant(id);  // Récupère l'étudiant existant en base de données

            if (etudiant == null) {  // Validation: vérifier que l'étudiant existe
                throw new IllegalArgumentException("Étudiant non trouvé");  // Lance une exception si étudiant introuvable
            }

            etudiant.setNom(request.getParameter("nom"));  // Met à jour le nom
            etudiant.setPrenom(request.getParameter("prenom"));  // Met à jour le prénom
            etudiant.setEmail(request.getParameter("email"));  // Met à jour l'email
            etudiant.setNumeroEtudiant(request.getParameter("numeroEtudiant"));  // Met à jour le numéro étudiant
            etudiant.setFiliere(request.getParameter("filiere"));  // Met à jour la filière

            // Conversion de la date de naissance
            String dateNaissanceStr = request.getParameter("dateNaissance");  // Récupère la date du formulaire
            if (dateNaissanceStr != null && !dateNaissanceStr.trim().isEmpty()) {  // Si une date est fournie
                try {
                    Date dateNaissance = dateFormat.parse(dateNaissanceStr);  // Parse la date (String → Date)
                    etudiant.setDateNaissance(dateNaissance);  // Met à jour la date de naissance
                } catch (ParseException e) {  // Si le format est invalide
                    throw new IllegalArgumentException("Format de date invalide");  // Lance une exception
                }
            }

            etudiantService.modifierEtudiant(etudiant);  // Appelle le service qui valide et met à jour en base (UPDATE)

            request.getSession().setAttribute("succes", "Étudiant modifié avec succès");  // Message de succès dans la session
            response.sendRedirect(request.getContextPath() + "/etudiants");  // Redirige vers la liste des étudiants (POST-Redirect-GET)

        } catch (IllegalArgumentException e) {  // Si une erreur de validation survient
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur
            afficherFormulaireModification(request, response);  // Ré-affiche le formulaire avec le message d'erreur
        }
    }

    /**
     * Supprimer un étudiant (DELETE en base de données)
     * Méthode appelée par doGet() lorsque action=supprimer&id=5
     * Supprime l'étudiant et toutes ses notes (CASCADE)
     */
    private void supprimerEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  // Peut lancer ServletException ou IOException

        try {  // Gestion des erreurs
            Long id = Long.parseLong(request.getParameter("id"));  // Récupère l'ID de l'étudiant à supprimer (paramètre URL)
            etudiantService.supprimerEtudiant(id);  // Appelle le service qui supprime l'étudiant (DELETE avec CASCADE sur les notes)

            request.getSession().setAttribute("succes", "Étudiant supprimé avec succès");  // Message de succès dans la session
            response.sendRedirect(request.getContextPath() + "/etudiants");  // Redirige vers la liste des étudiants

        } catch (IllegalArgumentException e) {  // Si une erreur survient (ex: étudiant non trouvé)
            request.setAttribute("erreur", e.getMessage());  // Stocke le message d'erreur
            listerEtudiants(request, response);  // Ré-affiche la liste des étudiants avec le message d'erreur
        }
    }
}
