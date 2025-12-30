<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Étudiants</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>Gestion des Étudiants</h1>
            <p>Application Java EE - Master 1 GI/MIAGE - IUA 2025</p>
        </div>
    </header>

    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">Accueil</a></li>
            <li><a href="${pageContext.request.contextPath}/etudiants" class="active">Étudiants</a></li>
            <li><a href="${pageContext.request.contextPath}/notes">Notes</a></li>
        </ul>
    </nav>

    <div class="container">
        <!-- Messages de succès ou d'erreur -->
        <c:if test="${not empty sessionScope.succes}">
            <div class="message success">
                ${sessionScope.succes}
            </div>
            <c:remove var="succes" scope="session"/>
        </c:if>

        <c:if test="${not empty requestScope.erreur}">
            <div class="message error">
                ${requestScope.erreur}
            </div>
        </c:if>

        <div class="card">
            <div class="card-header">
                <h2>Liste des Étudiants</h2>
            </div>

            <!-- Barre de recherche et bouton ajouter -->
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; flex-wrap: wrap; gap: 1rem;">
                <div class="search-bar" style="flex: 1; min-width: 300px;">
                    <form action="${pageContext.request.contextPath}/etudiants" method="get">
                        <input type="text"
                               name="recherche"
                               class="form-control"
                               placeholder="Rechercher un étudiant par nom..."
                               value="${recherche}">
                    </form>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/etudiants?action=ajouter" class="btn btn-success">
                        + Ajouter un étudiant
                    </a>
                </div>
            </div>

            <!-- Tableau des étudiants -->
            <div class="table-responsive">
                <c:choose>
                    <c:when test="${not empty etudiants}">
                        <table>
                            <thead>
                                <tr>
                                    <th>N° Étudiant</th>
                                    <th>Nom Complet</th>
                                    <th>Email</th>
                                    <th>Filière</th>
                                    <th>Date de Naissance</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${etudiants}" var="etudiant">
                                    <tr>
                                        <td>${etudiant.numeroEtudiant}</td>
                                        <td><strong>${etudiant.nomComplet}</strong></td>
                                        <td>${etudiant.email}</td>
                                        <td>
                                            <c:if test="${not empty etudiant.filiere}">
                                                <span class="badge badge-info">${etudiant.filiere}</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${not empty etudiant.dateNaissance}">
                                                <fmt:formatDate value="${etudiant.dateNaissance}" pattern="dd/MM/yyyy"/>
                                            </c:if>
                                        </td>
                                        <td class="actions">
                                            <a href="${pageContext.request.contextPath}/etudiants?action=details&id=${etudiant.id}"
                                               class="btn btn-info btn-sm">
                                                Détails
                                            </a>
                                            <a href="${pageContext.request.contextPath}/notes?etudiantId=${etudiant.id}"
                                               class="btn btn-success btn-sm">
                                                Notes
                                            </a>
                                            <a href="${pageContext.request.contextPath}/etudiants?action=modifier&id=${etudiant.id}"
                                               class="btn btn-warning btn-sm">
                                                Modifier
                                            </a>
                                            <a href="${pageContext.request.contextPath}/etudiants?action=supprimer&id=${etudiant.id}"
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?');">
                                                Supprimer
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="no-data">
                            <p>Aucun étudiant trouvé.</p>
                            <a href="${pageContext.request.contextPath}/etudiants?action=ajouter" class="btn btn-success">
                                Ajouter le premier étudiant
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 - Application de Gestion des Étudiants</p>
        <p>Master 1 GI/MIAGE - Institut Universitaire d'Abidjan (IUA)</p>
    </footer>
</body>
</html>
