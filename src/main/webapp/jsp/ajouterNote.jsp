<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter une Note</title>
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
            <h1 class="mb-0"><i class="bi bi-plus-circle-fill me-2"></i>Ajouter une Note</h1>
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
                        <h5 class="mb-0"><i class="bi bi-journal-plus me-2"></i>Informations de la Note</h5>
                    </div>
                    <div class="card-body p-4">
                        <form action="${pageContext.request.contextPath}/notes" method="post">
                            <input type="hidden" name="action" value="creer">

                            <div class="mb-4">
                                <label for="etudiantId" class="form-label">
                                    <i class="bi bi-person me-1"></i>Étudiant <span class="text-danger">*</span>
                                </label>
                                <select id="etudiantId"
                                        name="etudiantId"
                                        class="form-select form-select-lg"
                                        required>
                                    <option value="">-- Sélectionner un étudiant --</option>
                                    <c:forEach items="${etudiants}" var="e">
                                        <option value="${e.id}"
                                                ${etudiantSelectionne != null && etudiantSelectionne.id == e.id ? 'selected' : ''}>
                                            ${e.nomComplet} (${e.numeroEtudiant})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="matiere" class="form-label">
                                    <i class="bi bi-book me-1"></i>Matière <span class="text-danger">*</span>
                                </label>
                                <select id="matiere" name="matiere" class="form-select form-select-lg" required>
                                    <option value="">-- Sélectionner une matière --</option>
                                    <c:forEach items="${matieres}" var="m">
                                        <option value="${m}">${m}</option>
                                    </c:forEach>
                                    <option value="Mathématiques">Mathématiques</option>
                                    <option value="Programmation">Programmation</option>
                                    <option value="Base de données">Base de données</option>
                                    <option value="Réseaux">Réseaux</option>
                                    <option value="Algorithme">Algorithme</option>
                                    <option value="Systèmes d'exploitation">Systèmes d'exploitation</option>
                                    <option value="Génie logiciel">Génie logiciel</option>
                                    <option value="Web">Développement Web</option>
                                </select>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="valeur" class="form-label">
                                        <i class="bi bi-award me-1"></i>Note <span class="text-danger">*</span>
                                    </label>
                                    <input type="number"
                                           id="valeur"
                                           name="valeur"
                                           class="form-control form-control-lg"
                                           min="0"
                                           max="20"
                                           step="0.25"
                                           required
                                           placeholder="0.00">
                                    <small class="text-muted">Note sur 20</small>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="coefficient" class="form-label">
                                        <i class="bi bi-bar-chart me-1"></i>Coefficient <span class="text-danger">*</span>
                                    </label>
                                    <input type="number"
                                           id="coefficient"
                                           name="coefficient"
                                           class="form-control form-control-lg"
                                           min="0.5"
                                           max="10"
                                           step="0.5"
                                           value="1"
                                           required
                                           placeholder="1.0">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="semestre" class="form-label">
                                        <i class="bi bi-calendar3 me-1"></i>Semestre
                                    </label>
                                    <select id="semestre" name="semestre" class="form-select form-select-lg">
                                        <option value="">-- Optionnel --</option>
                                        <option value="1">Semestre 1</option>
                                        <option value="2">Semestre 2</option>
                                        <option value="3">Semestre 3</option>
                                        <option value="4">Semestre 4</option>
                                    </select>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="dateEvaluation" class="form-label">
                                        <i class="bi bi-calendar me-1"></i>Date d'évaluation
                                    </label>
                                    <input type="date"
                                           id="dateEvaluation"
                                           name="dateEvaluation"
                                           class="form-control form-control-lg">
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="commentaire" class="form-label">
                                    <i class="bi bi-chat-left-text me-1"></i>Commentaire
                                </label>
                                <textarea id="commentaire"
                                          name="commentaire"
                                          class="form-control"
                                          rows="4"
                                          placeholder="Commentaire optionnel sur cette note..."></textarea>
                            </div>

                            <hr class="my-4">

                            <div class="d-flex gap-2 justify-content-end">
                                <a href="${pageContext.request.contextPath}/notes${not empty etudiantSelectionne ? '?etudiantId='.concat(etudiantSelectionne.id) : ''}"
                                   class="btn btn-secondary btn-lg">
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
