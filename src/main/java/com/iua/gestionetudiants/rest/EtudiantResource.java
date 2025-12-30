package com.iua.gestionetudiants.rest;

import com.iua.gestionetudiants.model.Etudiant;
import com.iua.gestionetudiants.model.Note;
import com.iua.gestionetudiants.service.EtudiantService;
import com.iua.gestionetudiants.service.NoteService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service REST pour la gestion des étudiants
 * Expose les données au format JSON
 * URL de base : /api/etudiants
 */
@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EtudiantResource {

    private EtudiantService etudiantService;
    private NoteService noteService;

    public EtudiantResource() {
        this.etudiantService = new EtudiantService();
        this.noteService = new NoteService();
    }

    /**
     * GET /api/etudiants
     * Récupérer la liste de tous les étudiants au format JSON
     * @return liste des étudiants
     */
    @GET
    public Response getTousLesEtudiants() {
        try {
            List<Etudiant> etudiants = etudiantService.getTousLesEtudiants();
            return Response.ok(etudiants).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /api/etudiants/{id}
     * Récupérer un étudiant par son ID
     * @param id l'identifiant de l'étudiant
     * @return l'étudiant
     */
    @GET
    @Path("/{id}")
    public Response getEtudiant(@PathParam("id") Long id) {
        try {
            Etudiant etudiant = etudiantService.getEtudiant(id);
            if (etudiant == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(creerMessageErreur("Étudiant non trouvé avec l'ID: " + id))
                        .build();
            }
            return Response.ok(etudiant).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /api/etudiants/{id}/notes
     * Récupérer les notes d'un étudiant
     * @param id l'identifiant de l'étudiant
     * @return liste des notes de l'étudiant
     */
    @GET
    @Path("/{id}/notes")
    public Response getNotesEtudiant(@PathParam("id") Long id) {
        try {
            Etudiant etudiant = etudiantService.getEtudiant(id);
            if (etudiant == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(creerMessageErreur("Étudiant non trouvé avec l'ID: " + id))
                        .build();
            }

            List<Note> notes = noteService.getNotesParEtudiant(id);
            return Response.ok(notes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /api/etudiants/{id}/moyenne
     * Calculer et afficher la moyenne d'un étudiant
     * @param id l'identifiant de l'étudiant
     * @return la moyenne avec les statistiques
     */
    @GET
    @Path("/{id}/moyenne")
    public Response getMoyenneEtudiant(@PathParam("id") Long id) {
        try {
            Etudiant etudiant = etudiantService.getEtudiant(id);
            if (etudiant == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(creerMessageErreur("Étudiant non trouvé avec l'ID: " + id))
                        .build();
            }

            double moyenne = noteService.calculerMoyenne(id);
            double[] stats = noteService.getStatistiquesEtudiant(id);
            String appreciation = noteService.getAppreciation(moyenne);

            Map<String, Object> resultat = new HashMap<>();
            resultat.put("etudiantId", id);
            resultat.put("nomComplet", etudiant.getNomComplet());
            resultat.put("moyenne", moyenne);
            resultat.put("noteMin", stats[1]);
            resultat.put("noteMax", stats[2]);
            resultat.put("nombreNotes", (int) stats[3]);
            resultat.put("appreciation", appreciation);
            resultat.put("reussi", noteService.etudiantAReussi(id));

            return Response.ok(resultat).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /api/etudiants/{id}/details
     * Récupérer les détails complets d'un étudiant avec ses notes
     * @param id l'identifiant de l'étudiant
     * @return détails complets
     */
    @GET
    @Path("/{id}/details")
    public Response getDetailsComplets(@PathParam("id") Long id) {
        try {
            Etudiant etudiant = etudiantService.getEtudiantAvecNotes(id);
            if (etudiant == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(creerMessageErreur("Étudiant non trouvé avec l'ID: " + id))
                        .build();
            }

            double moyenne = etudiant.calculerMoyenne();
            String appreciation = noteService.getAppreciation(moyenne);

            Map<String, Object> resultat = new HashMap<>();
            resultat.put("etudiant", etudiant);
            resultat.put("notes", etudiant.getNotes());
            resultat.put("moyenne", moyenne);
            resultat.put("appreciation", appreciation);
            resultat.put("nombreNotes", etudiant.getNotes().size());

            return Response.ok(resultat).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /api/etudiants/recherche?nom={nom}
     * Rechercher des étudiants par nom
     * @param nom le nom à rechercher
     * @return liste des étudiants correspondants
     */
    @GET
    @Path("/recherche")
    public Response rechercherEtudiants(@QueryParam("nom") String nom) {
        try {
            List<Etudiant> etudiants = etudiantService.rechercherParNom(nom);
            return Response.ok(etudiants).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /api/etudiants/filiere/{filiere}
     * Récupérer les étudiants d'une filière
     * @param filiere la filière
     * @return liste des étudiants de cette filière
     */
    @GET
    @Path("/filiere/{filiere}")
    public Response getEtudiantsParFiliere(@PathParam("filiere") String filiere) {
        try {
            List<Etudiant> etudiants = etudiantService.rechercherParFiliere(filiere);
            return Response.ok(etudiants).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * POST /api/etudiants
     * Créer un nouvel étudiant
     * @param etudiant l'étudiant à créer
     * @return réponse de création
     */
    @POST
    public Response creerEtudiant(Etudiant etudiant) {
        try {
            etudiantService.creerEtudiant(etudiant);
            return Response.status(Response.Status.CREATED)
                    .entity(creerMessageSucces("Étudiant créé avec succès"))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * PUT /api/etudiants/{id}
     * Mettre à jour un étudiant
     * @param id l'identifiant de l'étudiant
     * @param etudiant les nouvelles données
     * @return réponse de mise à jour
     */
    @PUT
    @Path("/{id}")
    public Response modifierEtudiant(@PathParam("id") Long id, Etudiant etudiant) {
        try {
            etudiant.setId(id);
            Etudiant updated = etudiantService.modifierEtudiant(etudiant);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * DELETE /api/etudiants/{id}
     * Supprimer un étudiant
     * @param id l'identifiant de l'étudiant
     * @return réponse de suppression
     */
    @DELETE
    @Path("/{id}")
    public Response supprimerEtudiant(@PathParam("id") Long id) {
        try {
            etudiantService.supprimerEtudiant(id);
            return Response.ok(creerMessageSucces("Étudiant supprimé avec succès")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /api/etudiants/statistiques
     * Obtenir des statistiques globales
     * @return statistiques
     */
    @GET
    @Path("/statistiques")
    public Response getStatistiques() {
        try {
            long nombreEtudiants = etudiantService.compterEtudiants();
            List<String> filieres = etudiantService.getToutesFilieres();

            Map<String, Object> stats = new HashMap<>();
            stats.put("nombreTotalEtudiants", nombreEtudiants);
            stats.put("nombreFilieres", filieres.size());
            stats.put("filieres", filieres);

            return Response.ok(stats).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(creerMessageErreur(e.getMessage()))
                    .build();
        }
    }

    /**
     * Créer un message d'erreur formaté
     */
    private Map<String, String> creerMessageErreur(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("erreur", message);
        error.put("statut", "error");
        return error;
    }

    /**
     * Créer un message de succès formaté
     */
    private Map<String, String> creerMessageSucces(String message) {
        Map<String, String> success = new HashMap<>();
        success.put("message", message);
        success.put("statut", "success");
        return success;
    }
}
