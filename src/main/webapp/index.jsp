<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion Étudiants - Accueil</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #0d6efd;
            --secondary-color: #6c757d;
            --success-color: #198754;
            --bg-light: #f8f9fa;
        }
        body {
            background-color: var(--bg-light);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .navbar-custom {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .hero-section {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            color: white;
            padding: 4rem 0;
            margin-bottom: 3rem;
            border-radius: 0 0 50px 50px;
        }
        .feature-card {
            transition: transform 0.3s, box-shadow 0.3s;
            border: none;
            border-radius: 15px;
            height: 100%;
        }
        .feature-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .icon-box {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 1.5rem;
            color: white;
            font-size: 2rem;
        }
        .tech-badge {
            background: white;
            color: var(--primary-color);
            border: 2px solid var(--primary-color);
            padding: 0.5rem 1rem;
            border-radius: 50px;
            font-weight: 600;
            margin: 0.3rem;
            display: inline-block;
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
                        <a class="nav-link active" href="${pageContext.request.contextPath}/">
                            <i class="bi bi-house-fill me-1"></i>Accueil
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/etudiants">
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

    <!-- Hero Section -->
    <div class="hero-section">
        <div class="container text-center">
            <h1 class="display-4 fw-bold mb-3">Gestion des Étudiants et des Notes</h1>
            <p class="lead mb-4">Application Java EE - Master 1 GI/MIAGE - IUA 2025</p>
            <div class="d-flex justify-content-center gap-3">
                <a href="${pageContext.request.contextPath}/etudiants" class="btn btn-light btn-lg px-4">
                    <i class="bi bi-people-fill me-2"></i>Voir les Étudiants
                </a>
                <a href="${pageContext.request.contextPath}/notes" class="btn btn-outline-light btn-lg px-4">
                    <i class="bi bi-journal-text me-2"></i>Gérer les Notes
                </a>
            </div>
        </div>
    </div>

    <div class="container mb-5">
        <!-- Feature Cards -->
        <div class="row g-4 mb-5">
            <div class="col-md-4">
                <div class="card feature-card shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="icon-box">
                            <i class="bi bi-people-fill"></i>
                        </div>
                        <h4 class="fw-bold mb-3">Gestion des Étudiants</h4>
                        <p class="text-muted mb-4">Ajouter, modifier, supprimer et consulter les étudiants avec recherche avancée</p>
                        <a href="${pageContext.request.contextPath}/etudiants" class="btn btn-primary">
                            Accéder <i class="bi bi-arrow-right ms-2"></i>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card feature-card shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="icon-box">
                            <i class="bi bi-journal-text"></i>
                        </div>
                        <h4 class="fw-bold mb-3">Gestion des Notes</h4>
                        <p class="text-muted mb-4">Ajouter des notes avec coefficients et calculer les moyennes pondérées</p>
                        <a href="${pageContext.request.contextPath}/notes" class="btn btn-primary">
                            Accéder <i class="bi bi-arrow-right ms-2"></i>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card feature-card shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="icon-box">
                            <i class="bi bi-cloud-arrow-down-fill"></i>
                        </div>
                        <h4 class="fw-bold mb-3">API REST</h4>
                        <p class="text-muted mb-4">Services REST pour accéder aux données au format JSON (11 endpoints)</p>
                        <a href="${pageContext.request.contextPath}/api/etudiants" class="btn btn-primary" target="_blank">
                            Tester <i class="bi bi-box-arrow-up-right ms-2"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Fonctionnalités -->
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-header bg-primary text-white">
                <h3 class="mb-0"><i class="bi bi-list-check me-2"></i>Fonctionnalités Principales</h3>
            </div>
            <div class="card-body p-4">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <h5 class="text-primary mb-3"><i class="bi bi-person-check-fill me-2"></i>Module Étudiants</h5>
                        <ul class="list-unstyled">
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Créer un étudiant</li>
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Modifier les informations</li>
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Supprimer un étudiant</li>
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Rechercher par nom</li>
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Filtrer par filière (GI/MIAGE)</li>
                        </ul>
                    </div>
                    <div class="col-md-6 mb-3">
                        <h5 class="text-primary mb-3"><i class="bi bi-calculator-fill me-2"></i>Module Notes</h5>
                        <ul class="list-unstyled">
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Ajouter une note avec coefficient</li>
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Modifier une note existante</li>
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Calcul automatique de la moyenne</li>
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Appréciation automatique</li>
                            <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Statistiques par étudiant</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- Technologies -->
        <div class="card shadow-sm border-0">
            <div class="card-header bg-primary text-white">
                <h3 class="mb-0"><i class="bi bi-gear-fill me-2"></i>Technologies Java EE</h3>
            </div>
            <div class="card-body p-4 text-center">
                <span class="tech-badge">Java EE 8</span>
                <span class="tech-badge">JPA / Hibernate</span>
                <span class="tech-badge">MySQL 8</span>
                <span class="tech-badge">Servlets 4.0</span>
                <span class="tech-badge">JSP / JSTL</span>
                <span class="tech-badge">JAX-RS / Jersey</span>
                <span class="tech-badge">Maven 3</span>
                <span class="tech-badge">Bootstrap 5</span>
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
