<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails Étudiant - ${etudiant.nomComplet}</title>
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
        <!-- Informations de l'étudiant -->
        <div class="card">
            <div class="card-header">
                <h2>Détails de l'étudiant</h2>
            </div>

            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 2rem;">
                <div>
                    <p><strong>Nom complet :</strong></p>
                    <p style="font-size: 1.3rem; color: #2c3e50;">${etudiant.nomComplet}</p>
                </div>

                <div>
                    <p><strong>Numéro étudiant :</strong></p>
                    <p style="font-size: 1.1rem;">${etudiant.numeroEtudiant}</p>
                </div>

                <div>
                    <p><strong>Email :</strong></p>
                    <p style="font-size: 1.1rem;">${etudiant.email}</p>
                </div>

                <div>
                    <p><strong>Filière :</strong></p>
                    <p style="font-size: 1.1rem;">
                        <c:if test="${not empty etudiant.filiere}">
                            <span class="badge badge-info">${etudiant.filiere}</span>
                        </c:if>
                    </p>
                </div>

                <div>
                    <p><strong>Date de naissance :</strong></p>
                    <p style="font-size: 1.1rem;">
                        <c:if test="${not empty etudiant.dateNaissance}">
                            <fmt:formatDate value="${etudiant.dateNaissance}" pattern="dd/MM/yyyy"/>
                        </c:if>
                    </p>
                </div>

                <div>
                    <p><strong>Moyenne générale :</strong></p>
                    <p style="font-size: 1.5rem; font-weight: bold; color: ${moyenne >= 10 ? '#27ae60' : '#e74c3c'};">
                        <fmt:formatNumber value="${moyenne}" minFractionDigits="2" maxFractionDigits="2"/> / 20
                    </p>
                </div>
            </div>

            <div style="margin-top: 2rem; padding-top: 1.5rem; border-top: 1px solid #e0e0e0;">
                <a href="${pageContext.request.contextPath}/etudiants?action=modifier&id=${etudiant.id}" class="btn btn-warning">
                    Modifier
                </a>
                <a href="${pageContext.request.contextPath}/notes?etudiantId=${etudiant.id}" class="btn btn-success">
                    Voir les notes
                </a>
                <a href="${pageContext.request.contextPath}/notes?action=ajouter&etudiantId=${etudiant.id}" class="btn btn-primary">
                    Ajouter une note
                </a>
                <a href="${pageContext.request.contextPath}/etudiants" class="btn btn-secondary">
                    Retour à la liste
                </a>
            </div>
        </div>

        <!-- Liste des notes -->
        <div class="card">
            <div class="card-header">
                <h2>Notes de l'étudiant</h2>
            </div>

            <c:choose>
                <c:when test="${not empty etudiant.notes}">
                    <div class="table-responsive">
                        <table>
                            <thead>
                                <tr>
                                    <th>Matière</th>
                                    <th>Note</th>
                                    <th>Coefficient</th>
                                    <th>Note pondérée</th>
                                    <th>Semestre</th>
                                    <th>Date</th>
                                    <th>Appréciation</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${etudiant.notes}" var="note">
                                    <tr>
                                        <td><strong>${note.matiere}</strong></td>
                                        <td style="font-size: 1.1rem; font-weight: bold;">
                                            <fmt:formatNumber value="${note.valeur}" minFractionDigits="2" maxFractionDigits="2"/> / 20
                                        </td>
                                        <td>${note.coefficient}</td>
                                        <td>
                                            <fmt:formatNumber value="${note.notePonderee}" minFractionDigits="2" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <c:if test="${not empty note.semestre}">
                                                S${note.semestre}
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
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="no-data">
                        <p>Aucune note enregistrée pour cet étudiant.</p>
                        <a href="${pageContext.request.contextPath}/notes?action=ajouter&etudiantId=${etudiant.id}" class="btn btn-primary">
                            Ajouter une note
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 - Application de Gestion des Étudiants</p>
        <p>Master 1 GI/MIAGE - Institut Universitaire d'Abidjan (IUA)</p>
    </footer>
</body>
</html>
