<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Notes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>Gestion des Notes</h1>
            <p>Application Java EE - Master 1 GI/MIAGE - IUA 2025</p>
        </div>
    </header>

    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">Accueil</a></li>
            <li><a href="${pageContext.request.contextPath}/etudiants">Étudiants</a></li>
            <li><a href="${pageContext.request.contextPath}/notes" class="active">Notes</a></li>
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

        <!-- Filtre par étudiant -->
        <div class="card">
            <div class="card-header">
                <h2>Filtrer par Étudiant</h2>
            </div>

            <form action="${pageContext.request.contextPath}/notes" method="get" style="display: flex; gap: 1rem; align-items: end;">
                <div class="form-group" style="flex: 1; margin-bottom: 0;">
                    <label for="etudiantId">Sélectionner un étudiant</label>
                    <select id="etudiantId" name="etudiantId" class="form-control">
                        <option value="">-- Tous les étudiants --</option>
                        <c:forEach items="${etudiants}" var="e">
                            <option value="${e.id}" ${etudiantSelectionne != null && etudiantSelectionne.id == e.id ? 'selected' : ''}>
                                ${e.nomComplet} (${e.numeroEtudiant})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Filtrer</button>
            </form>
        </div>

        <!-- Informations de l'étudiant sélectionné -->
        <c:if test="${not empty etudiantSelectionne}">
            <div class="card" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white;">
                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 2rem;">
                    <div>
                        <p style="opacity: 0.9; margin-bottom: 0.5rem;">Étudiant</p>
                        <p style="font-size: 1.5rem; font-weight: bold;">${etudiantSelectionne.nomComplet}</p>
                    </div>
                    <div>
                        <p style="opacity: 0.9; margin-bottom: 0.5rem;">Filière</p>
                        <p style="font-size: 1.2rem;">${etudiantSelectionne.filiere}</p>
                    </div>
                    <div>
                        <p style="opacity: 0.9; margin-bottom: 0.5rem;">Moyenne Générale</p>
                        <p style="font-size: 2rem; font-weight: bold;">
                            <fmt:formatNumber value="${moyenne}" minFractionDigits="2" maxFractionDigits="2"/> / 20
                        </p>
                    </div>
                    <div>
                        <p style="opacity: 0.9; margin-bottom: 0.5rem;">Nombre de notes</p>
                        <p style="font-size: 1.5rem; font-weight: bold;">${notes.size()}</p>
                    </div>
                </div>
            </div>
        </c:if>

        <!-- Liste des notes -->
        <div class="card">
            <div class="card-header" style="display: flex; justify-content: space-between; align-items: center;">
                <h2>
                    <c:choose>
                        <c:when test="${not empty etudiantSelectionne}">
                            Notes de ${etudiantSelectionne.nomComplet}
                        </c:when>
                        <c:otherwise>
                            Toutes les Notes
                        </c:otherwise>
                    </c:choose>
                </h2>
                <a href="${pageContext.request.contextPath}/notes?action=ajouter${not empty etudiantSelectionne ? '&etudiantId='.concat(etudiantSelectionne.id) : ''}"
                   class="btn btn-success">
                    + Ajouter une note
                </a>
            </div>

            <div class="table-responsive">
                <c:choose>
                    <c:when test="${not empty notes}">
                        <table>
                            <thead>
                                <tr>
                                    <c:if test="${empty etudiantSelectionne}">
                                        <th>Étudiant</th>
                                    </c:if>
                                    <th>Matière</th>
                                    <th>Note</th>
                                    <th>Coefficient</th>
                                    <th>Note pondérée</th>
                                    <th>Semestre</th>
                                    <th>Date</th>
                                    <th>Appréciation</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${notes}" var="note">
                                    <tr>
                                        <c:if test="${empty etudiantSelectionne}">
                                            <td>
                                                <a href="${pageContext.request.contextPath}/notes?etudiantId=${note.etudiant.id}">
                                                    ${note.etudiant.nomComplet}
                                                </a>
                                            </td>
                                        </c:if>
                                        <td><strong>${note.matiere}</strong></td>
                                        <td style="font-size: 1.1rem; font-weight: bold; color: ${note.valeur >= 10 ? '#27ae60' : '#e74c3c'};">
                                            <fmt:formatNumber value="${note.valeur}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </td>
                                        <td>${note.coefficient}</td>
                                        <td>
                                            <fmt:formatNumber value="${note.notePonderee}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <c:if test="${not empty note.semestre}">
                                                <span class="badge badge-info">S${note.semestre}</span>
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
                                                    <span class="badge badge-success">Très Bien</span>
                                                </c:when>
                                                <c:when test="${note.valeur >= 14}">
                                                    <span class="badge badge-info">Bien</span>
                                                </c:when>
                                                <c:when test="${note.valeur >= 12}">
                                                    <span class="badge badge-info">Assez Bien</span>
                                                </c:when>
                                                <c:when test="${note.valeur >= 10}">
                                                    <span class="badge badge-warning">Passable</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-danger">Insuffisant</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="actions">
                                            <a href="${pageContext.request.contextPath}/notes?action=modifier&id=${note.id}"
                                               class="btn btn-warning btn-sm">
                                                Modifier
                                            </a>
                                            <a href="${pageContext.request.contextPath}/notes?action=supprimer&id=${note.id}"
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette note ?');">
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
                            <p>Aucune note trouvée.</p>
                            <a href="${pageContext.request.contextPath}/notes?action=ajouter" class="btn btn-success">
                                Ajouter une note
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
