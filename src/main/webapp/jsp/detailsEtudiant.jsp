<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails - ${etudiant.nomComplet}</title>
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
        .info-card {
            border-left: 4px solid #0d6efd;
        }
        .stat-box {
            text-align: center;
            padding: 1.5rem;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
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
            <nav aria-label="breadcrumb" class="mb-3">
                <ol class="breadcrumb mb-0">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/etudiants" class="text-white">Étudiants</a></li>
                    <li class="breadcrumb-item active text-white" aria-current="page">${etudiant.nomComplet}</li>
                </ol>
            </nav>
            <h1 class="mb-0">
                <i class="bi bi-person-circle me-2"></i>${etudiant.nomComplet}
            </h1>
        </div>
    </div>

    <div class="container mb-5">
        <!-- Student Info Card -->
        <div class="card shadow-sm border-0 mb-4 info-card">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0"><i class="bi bi-person-lines-fill me-2"></i>Informations Personnelles</h5>
            </div>
            <div class="card-body p-4">
                <div class="row g-4">
                    <div class="col-md-4">
                        <div class="stat-box">
                            <i class="bi bi-hash text-primary display-6 mb-2"></i>
                            <p class="text-muted mb-1">Numéro Étudiant</p>
                            <h5 class="fw-bold">${etudiant.numeroEtudiant}</h5>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stat-box">
                            <i class="bi bi-envelope text-primary display-6 mb-2"></i>
                            <p class="text-muted mb-1">Email</p>
                            <h6 class="fw-bold">${etudiant.email}</h6>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stat-box">
                            <i class="bi bi-book text-primary display-6 mb-2"></i>
                            <p class="text-muted mb-1">Filière</p>
                            <h5>
                                <c:if test="${not empty etudiant.filiere}">
                                    <span class="badge bg-primary">${etudiant.filiere}</span>
                                </c:if>
                            </h5>
                        </div>
                    </div>
                </div>

                <div class="row g-4 mt-2">
                    <div class="col-md-4">
                        <div class="stat-box">
                            <i class="bi bi-calendar text-primary display-6 mb-2"></i>
                            <p class="text-muted mb-1">Date de Naissance</p>
                            <h6 class="fw-bold">
                                <c:if test="${not empty etudiant.dateNaissance}">
                                    <fmt:formatDate value="${etudiant.dateNaissance}" pattern="dd/MM/yyyy"/>
                                </c:if>
                            </h6>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stat-box">
                            <i class="bi bi-calculator text-primary display-6 mb-2"></i>
                            <p class="text-muted mb-1">Moyenne Générale</p>
                            <h2 class="mb-0 ${moyenne >= 10 ? 'text-success' : 'text-danger'}">
                                <fmt:formatNumber value="${moyenne}" minFractionDigits="2" maxFractionDigits="2"/>
                            </h2>
                            <small class="text-muted">/ 20</small>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stat-box">
                            <i class="bi bi-journal-check text-primary display-6 mb-2"></i>
                            <p class="text-muted mb-1">Nombre de Notes</p>
                            <h2 class="mb-0">${etudiant.notes.size()}</h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-footer bg-white border-top p-4">
                <div class="d-flex gap-2 flex-wrap">
                    <a href="${pageContext.request.contextPath}/etudiants?action=modifier&id=${etudiant.id}"
                       class="btn btn-warning">
                        <i class="bi bi-pencil me-2"></i>Modifier
                    </a>
                    <a href="${pageContext.request.contextPath}/notes?etudiantId=${etudiant.id}"
                       class="btn btn-success">
                        <i class="bi bi-journal-text me-2"></i>Voir les notes
                    </a>
                    <a href="${pageContext.request.contextPath}/notes?action=ajouter&etudiantId=${etudiant.id}"
                       class="btn btn-primary">
                        <i class="bi bi-plus-circle me-2"></i>Ajouter une note
                    </a>
                    <a href="${pageContext.request.contextPath}/etudiants"
                       class="btn btn-secondary ms-auto">
                        <i class="bi bi-arrow-left me-2"></i>Retour à la liste
                    </a>
                </div>
            </div>
        </div>

        <!-- Notes Table -->
        <div class="card shadow-sm border-0">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">
                    <i class="bi bi-list-ul me-2"></i>Notes de l'Étudiant
                    <c:if test="${not empty etudiant.notes}">
                        <span class="badge bg-white text-primary ms-2">${etudiant.notes.size()}</span>
                    </c:if>
                </h5>
            </div>
            <div class="card-body p-0">
                <c:choose>
                    <c:when test="${not empty etudiant.notes}">
                        <div class="table-responsive">
                            <table class="table table-hover mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th><i class="bi bi-book me-1"></i>Matière</th>
                                        <th><i class="bi bi-award me-1"></i>Note</th>
                                        <th><i class="bi bi-bar-chart me-1"></i>Coefficient</th>
                                        <th><i class="bi bi-calculator me-1"></i>Note pondérée</th>
                                        <th><i class="bi bi-calendar3 me-1"></i>Semestre</th>
                                        <th><i class="bi bi-calendar me-1"></i>Date</th>
                                        <th><i class="bi bi-star me-1"></i>Appréciation</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${etudiant.notes}" var="note">
                                        <tr>
                                            <td><strong>${note.matiere}</strong></td>
                                            <td>
                                                <span class="fs-5 fw-bold ${note.valeur >= 10 ? 'text-success' : 'text-danger'}">
                                                    <fmt:formatNumber value="${note.valeur}" minFractionDigits="2" maxFractionDigits="2"/>
                                                </span>
                                                <small class="text-muted">/ 20</small>
                                            </td>
                                            <td><span class="badge bg-secondary">${note.coefficient}</span></td>
                                            <td>
                                                <fmt:formatNumber value="${note.notePonderee}" minFractionDigits="2" maxFractionDigits="2"/>
                                            </td>
                                            <td>
                                                <c:if test="${not empty note.semestre}">
                                                    <span class="badge bg-primary">S${note.semestre}</span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${not empty note.dateEvaluation}">
                                                    <fmt:formatDate value="${note.dateEvaluation}" pattern="dd/MM/yyyy"/>
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${note.valeur >= 16}">
                                                        <span class="badge bg-success">Très Bien</span>
                                                    </c:when>
                                                    <c:when test="${note.valeur >= 14}">
                                                        <span class="badge bg-info">Bien</span>
                                                    </c:when>
                                                    <c:when test="${note.valeur >= 12}">
                                                        <span class="badge bg-primary">Assez Bien</span>
                                                    </c:when>
                                                    <c:when test="${note.valeur >= 10}">
                                                        <span class="badge bg-warning text-dark">Passable</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-danger">Insuffisant</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="text-center py-5">
                            <i class="bi bi-inbox display-1 text-muted"></i>
                            <h4 class="mt-3 text-muted">Aucune note enregistrée</h4>
                            <p class="text-muted mb-4">Commencez par ajouter une note pour cet étudiant</p>
                            <a href="${pageContext.request.contextPath}/notes?action=ajouter&etudiantId=${etudiant.id}"
                               class="btn btn-primary">
                                <i class="bi bi-plus-circle me-2"></i>Ajouter une note
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
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
