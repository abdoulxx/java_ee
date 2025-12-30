<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur</title>
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
            <li><a href="${pageContext.request.contextPath}/etudiants">Étudiants</a></li>
            <li><a href="${pageContext.request.contextPath}/notes">Notes</a></li>
        </ul>
    </nav>

    <div class="container">
        <div class="card">
            <div style="text-align: center; padding: 3rem 0;">
                <h1 style="font-size: 4rem; color: #e74c3c; margin-bottom: 1rem;">⚠️</h1>
                <h2 style="color: #e74c3c; margin-bottom: 1.5rem;">Une erreur s'est produite</h2>

                <c:if test="${not empty requestScope.erreur}">
                    <div class="message error" style="text-align: left; margin: 2rem auto; max-width: 600px;">
                        <strong>Détail de l'erreur :</strong><br>
                        ${requestScope.erreur}
                    </div>
                </c:if>

                <c:if test="${empty requestScope.erreur}">
                    <p style="color: #7f8c8d; margin-bottom: 2rem;">
                        Une erreur inattendue s'est produite lors du traitement de votre requête.
                    </p>
                </c:if>

                <div style="margin-top: 2rem;">
                    <a href="javascript:history.back()" class="btn btn-secondary">Retour</a>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Accueil</a>
                </div>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 - Application de Gestion des Étudiants</p>
        <p>Master 1 GI/MIAGE - Institut Universitaire d'Abidjan (IUA)</p>
    </footer>
</body>
</html>
