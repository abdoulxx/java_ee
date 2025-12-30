<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Étudiant</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .navbar-custom {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .page-header {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
        }
        footer {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            color: white;
            padding: 2rem 0;
            margin-top: 4rem;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark navbar-custom">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
                <i class="bi bi-mortarboard-fill me-2"></i>Gestion Étudiants
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">
                            <i class="bi bi-house-fill me-1"></i>Accueil
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/etudiants">
                            <i class="bi bi-people-fill me-1"></i>Étudiants
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/notes">
                            <i class="bi bi-journal-text me-1"></i>Notes
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Page Header -->
    <div class="page-header">
        <div class="container">
            <h1 class="mb-0"><i class="bi bi-person-plus-fill me-2"></i>Ajouter un Étudiant</h1>
        </div>
    </div>

    <div class="container mb-5">
        <!-- Error Message -->
        <c:if test="${not empty requestScope.erreur}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>${requestScope.erreur}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Form Card -->
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow-sm border-0">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="bi bi-file-earmark-person me-2"></i>Informations de l'Étudiant</h5>
                    </div>
                    <div class="card-body p-4">
                        <form action="${pageContext.request.contextPath}/etudiants" method="post">
                            <input type="hidden" name="action" value="creer">

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="nom" class="form-label">
                                        <i class="bi bi-person me-1"></i>Nom <span class="text-danger">*</span>
                                    </label>
                                    <input type="text"
                                           id="nom"
                                           name="nom"
                                           class="form-control form-control-lg"
                                           required
                                           placeholder="Entrez le nom de famille">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="prenom" class="form-label">
                                        <i class="bi bi-person me-1"></i>Prénom <span class="text-danger">*</span>
                                    </label>
                                    <input type="text"
                                           id="prenom"
                                           name="prenom"
                                           class="form-control form-control-lg"
                                           required
                                           placeholder="Entrez le prénom">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">
                                    <i class="bi bi-envelope me-1"></i>Email
                                </label>
                                <input type="email"
                                       id="email"
                                       name="email"
                                       class="form-control form-control-lg"
                                       placeholder="exemple@iua.edu.ci">
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="numeroEtudiant" class="form-label">
                                        <i class="bi bi-hash me-1"></i>Numéro Étudiant
                                    </label>
                                    <input type="text"
                                           id="numeroEtudiant"
                                           name="numeroEtudiant"
                                           class="form-control form-control-lg"
                                           placeholder="Ex: ETU2025001">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="filiere" class="form-label">
                                        <i class="bi bi-book me-1"></i>Filière
                                    </label>
                                    <select id="filiere" name="filiere" class="form-select form-select-lg">
                                        <option value="">-- Sélectionnez une filière --</option>
                                        <option value="MIAGE">MIAGE</option>
                                        <option value="GI">GI (Génie Informatique)</option>
                                        <option value="Informatique">Informatique</option>
                                        <option value="Réseaux">Réseaux et Télécommunications</option>
                                    </select>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="dateNaissance" class="form-label">
                                    <i class="bi bi-calendar me-1"></i>Date de Naissance
                                </label>
                                <input type="date"
                                       id="dateNaissance"
                                       name="dateNaissance"
                                       class="form-control form-control-lg">
                            </div>

                            <hr class="my-4">

                            <div class="d-flex gap-2 justify-content-end">
                                <a href="${pageContext.request.contextPath}/etudiants" class="btn btn-secondary btn-lg">
                                    <i class="bi bi-x-circle me-2"></i>Annuler
                                </a>
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="bi bi-check-circle me-2"></i>Enregistrer
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Info Card -->
                <div class="card border-primary mt-3">
                    <div class="card-body">
                        <small class="text-muted">
                            <i class="bi bi-info-circle me-1"></i>
                            Les champs marqués d'un astérisque (<span class="text-danger">*</span>) sont obligatoires.
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="text-center">
        <div class="container">
            <p class="mb-1">&copy; 2025 - Application de Gestion des Étudiants</p>
            <p class="mb-0 opacity-75">Master 1 GI/MIAGE - Institut Universitaire d'Abidjan (IUA)</p>
        </div>
    </footer>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
