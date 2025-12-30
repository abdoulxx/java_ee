<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier une Note</title>
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
        <c:if test="${not empty requestScope.erreur}">
            <div class="message error">
                ${requestScope.erreur}
            </div>
        </c:if>

        <div class="card">
            <div class="card-header">
                <h2>Modifier une Note</h2>
            </div>

            <form action="${pageContext.request.contextPath}/notes" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${note.id}">

                <div class="form-group">
                    <label for="etudiantId">Étudiant *</label>
                    <select id="etudiantId"
                            name="etudiantId"
                            class="form-control"
                            required>
                        <option value="">-- Sélectionner un étudiant --</option>
                        <c:forEach items="${etudiants}" var="e">
                            <option value="${e.id}"
                                    ${note.etudiant.id == e.id ? 'selected' : ''}>
                                ${e.nomComplet} (${e.numeroEtudiant})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="matiere">Matière *</label>
                    <input type="text"
                           id="matiere"
                           name="matiere"
                           class="form-control"
                           list="matieres"
                           required
                           value="${note.matiere}"
                           placeholder="Entrez la matière">
                    <datalist id="matieres">
                        <c:forEach items="${matieres}" var="m">
                            <option value="${m}">
                        </c:forEach>
                        <option value="Mathématiques">
                        <option value="Programmation">
                        <option value="Base de données">
                        <option value="Réseaux">
                        <option value="Algorithme">
                        <option value="Systèmes d'exploitation">
                        <option value="Génie logiciel">
                        <option value="Web">
                    </datalist>
                </div>

                <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
                    <div class="form-group">
                        <label for="valeur">Note *</label>
                        <input type="number"
                               id="valeur"
                               name="valeur"
                               class="form-control"
                               min="0"
                               max="20"
                               step="0.25"
                               required
                               value="${note.valeur}"
                               placeholder="0.00">
                        <small style="color: #7f8c8d;">Note sur 20</small>
                    </div>

                    <div class="form-group">
                        <label for="coefficient">Coefficient *</label>
                        <input type="number"
                               id="coefficient"
                               name="coefficient"
                               class="form-control"
                               min="0.5"
                               max="10"
                               step="0.5"
                               value="${note.coefficient}"
                               required
                               placeholder="1.0">
                    </div>

                    <div class="form-group">
                        <label for="semestre">Semestre</label>
                        <select id="semestre" name="semestre" class="form-control">
                            <option value="">-- Optionnel --</option>
                            <option value="1" ${note.semestre == 1 ? 'selected' : ''}>Semestre 1</option>
                            <option value="2" ${note.semestre == 2 ? 'selected' : ''}>Semestre 2</option>
                            <option value="3" ${note.semestre == 3 ? 'selected' : ''}>Semestre 3</option>
                            <option value="4" ${note.semestre == 4 ? 'selected' : ''}>Semestre 4</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="dateEvaluation">Date d'évaluation</label>
                        <fmt:formatDate value="${note.dateEvaluation}" pattern="yyyy-MM-dd" var="formattedDate"/>
                        <input type="date"
                               id="dateEvaluation"
                               name="dateEvaluation"
                               class="form-control"
                               value="${formattedDate}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="commentaire">Commentaire</label>
                    <textarea id="commentaire"
                              name="commentaire"
                              class="form-control"
                              rows="3"
                              placeholder="Commentaire optionnel sur cette note...">${note.commentaire}</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-success">Enregistrer les modifications</button>
                    <a href="${pageContext.request.contextPath}/notes?etudiantId=${note.etudiant.id}"
                       class="btn btn-secondary">
                        Annuler
                    </a>
                </div>
            </form>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 - Application de Gestion des Étudiants</p>
        <p>Master 1 GI/MIAGE - Institut Universitaire d'Abidjan (IUA)</p>
    </footer>
</body>
</html>
