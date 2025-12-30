<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion Étudiants - Accueil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1>Gestion des Étudiants et des Notes</h1>
            <p>Application Java EE - Master 1 GI/MIAGE - IUA 2025</p>
        </div>
    </header>

    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/" class="active">Accueil</a></li>
            <li><a href="${pageContext.request.contextPath}/etudiants">Étudiants</a></li>
            <li><a href="${pageContext.request.contextPath}/notes">Notes</a></li>
        </ul>
    </nav>

    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>Bienvenue</h2>
            </div>
            <p style="margin-bottom: 2rem;">
                Cette application permet de gérer les étudiants et leurs notes de manière simple et efficace.
            </p>

            <div class="stats">
                <div class="stat-card">
                    <h3>📚</h3>
                    <p style="font-size: 1.1rem; color: #2c3e50; font-weight: 600;">Gestion des Étudiants</p>
                    <p>Ajouter, modifier, supprimer et consulter les étudiants</p>
                    <a href="${pageContext.request.contextPath}/etudiants" class="btn btn-primary" style="margin-top: 1rem;">
                        Voir les étudiants
                    </a>
                </div>

                <div class="stat-card">
                    <h3>📝</h3>
                    <p style="font-size: 1.1rem; color: #2c3e50; font-weight: 600;">Gestion des Notes</p>
                    <p>Ajouter des notes et calculer les moyennes</p>
                    <a href="${pageContext.request.contextPath}/notes" class="btn btn-success" style="margin-top: 1rem;">
                        Voir les notes
                    </a>
                </div>

                <div class="stat-card">
                    <h3>📊</h3>
                    <p style="font-size: 1.1rem; color: #2c3e50; font-weight: 600;">Services REST</p>
                    <p>API REST pour accéder aux données en JSON</p>
                    <a href="${pageContext.request.contextPath}/api/etudiants" class="btn btn-info" style="margin-top: 1rem;">
                        Voir l'API
                    </a>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Fonctionnalités</h2>
            </div>
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 2rem;">
                <div>
                    <h3 style="color: #3498db; margin-bottom: 1rem;">Étudiants</h3>
                    <ul style="list-style: none; padding-left: 0;">
                        <li style="padding: 0.5rem 0;">✓ Ajouter un étudiant</li>
                        <li style="padding: 0.5rem 0;">✓ Modifier un étudiant</li>
                        <li style="padding: 0.5rem 0;">✓ Supprimer un étudiant</li>
                        <li style="padding: 0.5rem 0;">✓ Afficher la liste des étudiants</li>
                        <li style="padding: 0.5rem 0;">✓ Rechercher des étudiants</li>
                    </ul>
                </div>

                <div>
                    <h3 style="color: #27ae60; margin-bottom: 1rem;">Notes</h3>
                    <ul style="list-style: none; padding-left: 0;">
                        <li style="padding: 0.5rem 0;">✓ Ajouter une note à un étudiant</li>
                        <li style="padding: 0.5rem 0;">✓ Modifier une note</li>
                        <li style="padding: 0.5rem 0;">✓ Supprimer une note</li>
                        <li style="padding: 0.5rem 0;">✓ Calculer la moyenne d'un étudiant</li>
                        <li style="padding: 0.5rem 0;">✓ Afficher les statistiques</li>
                    </ul>
                </div>

                <div>
                    <h3 style="color: #e74c3c; margin-bottom: 1rem;">API REST</h3>
                    <ul style="list-style: none; padding-left: 0;">
                        <li style="padding: 0.5rem 0;">✓ Liste des étudiants (JSON)</li>
                        <li style="padding: 0.5rem 0;">✓ Notes d'un étudiant (JSON)</li>
                        <li style="padding: 0.5rem 0;">✓ Calcul de moyenne (JSON)</li>
                        <li style="padding: 0.5rem 0;">✓ CRUD complet via REST</li>
                        <li style="padding: 0.5rem 0;">✓ Statistiques globales</li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Technologies utilisées</h2>
            </div>
            <div style="display: flex; flex-wrap: wrap; gap: 1rem;">
                <span class="badge badge-info">Java EE 8</span>
                <span class="badge badge-success">JPA / Hibernate</span>
                <span class="badge badge-warning">MySQL</span>
                <span class="badge badge-danger">Servlets</span>
                <span class="badge badge-info">JSP / JSTL</span>
                <span class="badge badge-success">JAX-RS / Jersey</span>
                <span class="badge badge-warning">Maven</span>
                <span class="badge badge-danger">HTML / CSS</span>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 - Application de Gestion des Étudiants</p>
        <p>Master 1 GI/MIAGE - Institut Universitaire d'Abidjan (IUA)</p>
    </footer>
</body>
</html>
