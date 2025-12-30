<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Étudiants</title>
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
        .table-actions {
            display: flex;
            gap: 0.3rem;
        }
        .btn-sm {
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
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
            <h1 class="mb-0"><i class="bi bi-people-fill me-2"></i>Liste des Étudiants</h1>
        </div>
    </div>

    <div class="container mb-5">
        <!-- Messages -->
        <c:if test="${not empty sessionScope.succes}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i>${sessionScope.succes}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="succes" scope="session"/>
        </c:if>

        <c:if test="${not empty requestScope.erreur}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>${requestScope.erreur}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Search and Add -->
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-body">
                <div class="row align-items-center">
                    <div class="col-md-8 mb-3 mb-md-0">
                        <form action="${pageContext.request.contextPath}/etudiants" method="get">
                            <div class="input-group">
                                <span class="input-group-text bg-white">
                                    <i class="bi bi-search"></i>
                                </span>
                                <input type="text"
                                       name="recherche"
                                       class="form-control"
                                       placeholder="Rechercher un étudiant par nom..."
                                       value="${recherche}">
                                <button class="btn btn-primary" type="submit">
                                    Rechercher
                                </button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-4 text-md-end">
                        <a href="${pageContext.request.contextPath}/etudiants?action=ajouter"
                           class="btn btn-primary btn-lg w-100 w-md-auto">
                            <i class="bi bi-plus-circle me-2"></i>Ajouter un étudiant
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Students Table -->
        <div class="card shadow-sm border-0">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">
                    <i class="bi bi-list-ul me-2"></i>Tous les Étudiants
                    <c:if test="${not empty etudiants}">
                        <span class="badge bg-white text-primary ms-2">${etudiants.size()}</span>
                    </c:if>
                </h5>
            </div>
            <div class="card-body p-0">
                <c:choose>
                    <c:when test="${not empty etudiants}">
                        <div class="table-responsive">
                            <table class="table table-hover mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th><i class="bi bi-hash me-1"></i>N° Étudiant</th>
                                        <th><i class="bi bi-person me-1"></i>Nom Complet</th>
                                        <th><i class="bi bi-envelope me-1"></i>Email</th>
                                        <th><i class="bi bi-book me-1"></i>Filière</th>
                                        <th><i class="bi bi-calendar me-1"></i>Date de Naissance</th>
                                        <th class="text-center"><i class="bi bi-gear me-1"></i>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${etudiants}" var="etudiant">
                                        <tr>
                                            <td><code>${etudiant.numeroEtudiant}</code></td>
                                            <td><strong>${etudiant.nomComplet}</strong></td>
                                            <td>${etudiant.email}</td>
                                            <td>
                                                <c:if test="${not empty etudiant.filiere}">
                                                    <span class="badge bg-primary">${etudiant.filiere}</span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${not empty etudiant.dateNaissance}">
                                                    <fmt:formatDate value="${etudiant.dateNaissance}" pattern="dd/MM/yyyy"/>
                                                </c:if>
                                            </td>
                                            <td>
                                                <div class="table-actions justify-content-center">
                                                    <a href="${pageContext.request.contextPath}/etudiants?action=details&id=${etudiant.id}"
                                                       class="btn btn-sm btn-info" title="Voir détails">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/notes?etudiantId=${etudiant.id}"
                                                       class="btn btn-sm btn-success" title="Voir notes">
                                                        <i class="bi bi-journal-text"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/etudiants?action=modifier&id=${etudiant.id}"
                                                       class="btn btn-sm btn-warning" title="Modifier">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/etudiants?action=supprimer&id=${etudiant.id}"
                                                       class="btn btn-sm btn-danger" title="Supprimer"
                                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ${etudiant.nomComplet} ?');">
                                                        <i class="bi bi-trash"></i>
                                                    </a>
                                                </div>
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
                            <h4 class="mt-3 text-muted">Aucun étudiant trouvé</h4>
                            <p class="text-muted mb-4">Commencez par ajouter votre premier étudiant</p>
                            <a href="${pageContext.request.contextPath}/etudiants?action=ajouter"
                               class="btn btn-primary">
                                <i class="bi bi-plus-circle me-2"></i>Ajouter le premier étudiant
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
