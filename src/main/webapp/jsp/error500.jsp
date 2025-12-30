<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur 500 - Erreur serveur</title>
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
                <h1 style="font-size: 6rem; color: #e74c3c; margin-bottom: 1rem;">500</h1>
                <h2 style="color: #e74c3c; margin-bottom: 1.5rem;">Erreur interne du serveur</h2>
                <p style="color: #7f8c8d; margin-bottom: 2rem; font-size: 1.1rem;">
                    Une erreur s'est produite lors du traitement de votre requête.<br>
                    Veuillez réessayer ultérieurement.
                </p>

                <div style="margin-top: 2rem;">
                    <a href="javascript:history.back()" class="btn btn-secondary">Retour</a>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Accueil</a>
                </div>
            </div>
        </div>

        <c:if test="${not empty pageContext.exception}">
            <div class="card">
                <div class="card-header">
                    <h3 style="color: #e74c3c;">Détails techniques (mode développement)</h3>
                </div>
                <div style="background: #f8f9fa; padding: 1rem; border-radius: 5px; font-family: monospace; font-size: 0.9rem;">
                    <strong>Exception :</strong> ${pageContext.exception}<br>
                    <strong>Message :</strong> ${pageContext.exception.message}
                </div>
            </div>
        </c:if>
    </div>

    <footer>
        <p>&copy; 2025 - Application de Gestion des Étudiants</p>
        <p>Master 1 GI/MIAGE - Institut Universitaire d'Abidjan (IUA)</p>
    </footer>
</body>
</html>
