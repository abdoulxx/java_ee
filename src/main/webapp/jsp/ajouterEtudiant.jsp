<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Étudiant</title>
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
        <c:if test="${not empty requestScope.erreur}">
            <div class="message error">
                ${requestScope.erreur}
            </div>
        </c:if>

        <div class="card">
            <div class="card-header">
                <h2>Ajouter un Étudiant</h2>
            </div>

            <form action="${pageContext.request.contextPath}/etudiants" method="post">
                <input type="hidden" name="action" value="creer">

                <div class="form-group">
                    <label for="nom">Nom *</label>
                    <input type="text"
                           id="nom"
                           name="nom"
                           class="form-control"
                           required
                           placeholder="Entrez le nom de famille">
                </div>

                <div class="form-group">
                    <label for="prenom">Prénom *</label>
                    <input type="text"
                           id="prenom"
                           name="prenom"
                           class="form-control"
                           required
                           placeholder="Entrez le prénom">
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email"
                           id="email"
                           name="email"
                           class="form-control"
                           placeholder="exemple@email.com">
                </div>

                <div class="form-group">
                    <label for="numeroEtudiant">Numéro Étudiant</label>
                    <input type="text"
                           id="numeroEtudiant"
                           name="numeroEtudiant"
                           class="form-control"
                           placeholder="Ex: ETU2025001">
                </div>

                <div class="form-group">
                    <label for="filiere">Filière</label>
                    <input type="text"
                           id="filiere"
                           name="filiere"
                           class="form-control"
                           list="filieres"
                           placeholder="Ex: MIAGE, GI, etc.">
                    <datalist id="filieres">
                        <c:forEach items="${filieres}" var="f">
                            <option value="${f}">
                        </c:forEach>
                        <option value="MIAGE">
                        <option value="GI">
                        <option value="Informatique">
                        <option value="Réseaux">
                    </datalist>
                </div>

                <div class="form-group">
                    <label for="dateNaissance">Date de Naissance</label>
                    <input type="date"
                           id="dateNaissance"
                           name="dateNaissance"
                           class="form-control">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-success">Enregistrer</button>
                    <a href="${pageContext.request.contextPath}/etudiants" class="btn btn-secondary">Annuler</a>
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
