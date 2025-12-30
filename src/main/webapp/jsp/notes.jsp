<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Notes</title>
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
        .stats-card {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            color: white;
            border-radius: 15px;
            padding: 2rem;
        }
        .table-actions {
            display: flex;
            gap: 0.3rem;
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/etudiants">
                            <i class="bi bi-people-fill me-1"></i>Étudiants
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/notes">
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
            <h1 class="mb-0"><i class="bi bi-journal-text me-2"></i>Gestion des Notes</h1>
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

        <!-- Student Filter -->
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-body">
                <h5 class="card-title mb-3">
                    <i class="bi bi-funnel-fill me-2"></i>Filtrer par Étudiant
                </h5>
                <form action="${pageContext.request.contextPath}/notes" method="get">
                    <div class="row align-items-end">
                        <div class="col-md-10 mb-3 mb-md-0">
                            <select name="etudiantId" class="form-select form-select-lg">
                                <option value="">-- Tous les étudiants --</option>
                                <c:forEach items="${etudiants}" var="e">
                                    <option value="${e.id}" ${etudiantSelectionne != null && etudiantSelectionne.id == e.id ? 'selected' : ''}>
                                        ${e.nomComplet} (${e.numeroEtudiant})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary btn-lg w-100">
                                <i class="bi bi-search me-2"></i>Filtrer
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Student Stats (if selected) -->
        <c:if test="${not empty etudiantSelectionne}">
            <div class="stats-card shadow-sm mb-4">
                <div class="row g-4 text-center">
                    <div class="col-md-3">
                        <div>
                            <i class="bi bi-person-circle display-5 mb-2"></i>
                            <p class="opacity-75 mb-1">Étudiant</p>
                            <h4 class="fw-bold">${etudiantSelectionne.nomComplet}</h4>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div>
                            <i class="bi bi-book display-5 mb-2"></i>
                            <p class="opacity-75 mb-1">Filière</p>
                            <h4 class="fw-bold">${etudiantSelectionne.filiere}</h4>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div>
                            <i class="bi bi-calculator display-5 mb-2"></i>
                            <p class="opacity-75 mb-1">Moyenne Générale</p>
                            <h2 class="fw-bold mb-0">
                                <fmt:formatNumber value="${moyenne}" minFractionDigits="2" maxFractionDigits="2"/> / 20
                            </h2>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div>
                            <i class="bi bi-list-check display-5 mb-2"></i>
                            <p class="opacity-75 mb-1">Nombre de notes</p>
                            <h4 class="fw-bold">${notes.size()}</h4>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <!-- Notes Table -->
        <div class="card shadow-sm border-0">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <h5 class="mb-0">
                    <i class="bi bi-list-ul me-2"></i>
                    <c:choose>
                        <c:when test="${not empty etudiantSelectionne}">
                            Notes de ${etudiantSelectionne.nomComplet}
                        </c:when>
                        <c:otherwise>
                            Toutes les Notes
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${not empty notes}">
                        <span class="badge bg-white text-primary ms-2">${notes.size()}</span>
                    </c:if>
                </h5>
                <a href="${pageContext.request.contextPath}/notes?action=ajouter${not empty etudiantSelectionne ? '&etudiantId='.concat(etudiantSelectionne.id) : ''}"
                   class="btn btn-light">
                    <i class="bi bi-plus-circle me-2"></i>Ajouter une note
                </a>
            </div>
            <div class="card-body p-0">
                <c:choose>
                    <c:when test="${not empty notes}">
                        <div class="table-responsive">
                            <table class="table table-hover mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <c:if test="${empty etudiantSelectionne}">
                                            <th><i class="bi bi-person me-1"></i>Étudiant</th>
                                        </c:if>
                                        <th><i class="bi bi-book me-1"></i>Matière</th>
                                        <th><i class="bi bi-award me-1"></i>Note</th>
                                        <th><i class="bi bi-bar-chart me-1"></i>Coefficient</th>
                                        <th><i class="bi bi-calculator me-1"></i>Note pondérée</th>
                                        <th><i class="bi bi-calendar3 me-1"></i>Semestre</th>
                                        <th><i class="bi bi-calendar me-1"></i>Date</th>
                                        <th><i class="bi bi-star me-1"></i>Appréciation</th>
                                        <th class="text-center"><i class="bi bi-gear me-1"></i>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${notes}" var="note">
                                        <tr>
                                            <c:if test="${empty etudiantSelectionne}">
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/notes?etudiantId=${note.etudiant.id}" class="text-decoration-none">
                                                        <i class="bi bi-link-45deg me-1"></i>${note.etudiant.nomComplet}
                                                    </a>
                                                </td>
                                            </c:if>
                                            <td><strong>${note.matiere}</strong></td>
                                            <td>
                                                <span class="fs-5 fw-bold ${note.valeur >= 10 ? 'text-success' : 'text-danger'}">
                                                    <fmt:formatNumber value="${note.valeur}" minFractionDigits="2" maxFractionDigits="2"/>
                                                </span>
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
                                            <td>
                                                <div class="table-actions justify-content-center">
                                                    <a href="${pageContext.request.contextPath}/notes?action=modifier&id=${note.id}"
                                                       class="btn btn-sm btn-warning" title="Modifier">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/notes?action=supprimer&id=${note.id}"
                                                       class="btn btn-sm btn-danger" title="Supprimer"
                                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette note ?');">
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
                            <h4 class="mt-3 text-muted">Aucune note trouvée</h4>
                            <p class="text-muted mb-4">Commencez par ajouter une note</p>
                            <a href="${pageContext.request.contextPath}/notes?action=ajouter"
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
