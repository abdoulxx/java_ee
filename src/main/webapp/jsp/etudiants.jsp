<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Étudiants</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <!-- DataTables CSS -->
    <link href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/buttons/2.4.2/css/buttons.bootstrap5.min.css" rel="stylesheet">
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
        .table-actions {
            display: flex;
            gap: 0.3rem;
        }
        .btn-sm {
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
        }
        footer {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%);
            color: white;
            padding: 2rem 0;
            margin-top: 4rem;
        }
        /* DataTables Custom Styling */
        div.dataTables_wrapper div.dataTables_length select,
        div.dataTables_wrapper div.dataTables_filter input {
            margin: 0 0.5rem;
        }
        .dt-buttons {
            margin-bottom: 1rem;
        }
        .dt-button {
            background: linear-gradient(135deg, #0d6efd 0%, #0a58ca 100%) !important;
            border: none !important;
            color: white !important;
            padding: 0.5rem 1rem;
            border-radius: 0.375rem;
            margin-right: 0.5rem;
        }
        .dt-button:hover {
            opacity: 0.9;
        }
        table.dataTable thead th {
            border-bottom: 2px solid #0d6efd;
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
                        <a class="nav-link active" href="${pageContext.request.contextPath}/etudiants">
                            <i class="bi bi-people-fill me-1"></i>Étudiants
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/notes">
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
            <h1 class="mb-0"><i class="bi bi-people-fill me-2"></i>Liste des Étudiants</h1>
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

        <!-- Action Buttons -->
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-body">
                <div class="d-flex justify-content-end">
                    <a href="${pageContext.request.contextPath}/etudiants?action=ajouter"
                       class="btn btn-primary btn-lg">
                        <i class="bi bi-plus-circle me-2"></i>Ajouter un étudiant
                    </a>
                </div>
            </div>
        </div>

        <!-- Students Table -->
        <div class="card shadow-sm border-0">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">
                    <i class="bi bi-list-ul me-2"></i>Tous les Étudiants
                    <c:if test="${not empty etudiants}">
                        <span class="badge bg-white text-primary ms-2">${etudiants.size()}</span>
                    </c:if>
                </h5>
            </div>
            <div class="card-body p-0">
                <c:choose>
                    <c:when test="${not empty etudiants}">
                        <div class="table-responsive">
                            <table id="etudiantsTable" class="table table-hover mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th><i class="bi bi-hash me-1"></i>N° Étudiant</th>
                                        <th><i class="bi bi-person me-1"></i>Nom Complet</th>
                                        <th><i class="bi bi-envelope me-1"></i>Email</th>
                                        <th><i class="bi bi-book me-1"></i>Filière</th>
                                        <th><i class="bi bi-calendar me-1"></i>Date de Naissance</th>
                                        <th class="text-center"><i class="bi bi-gear me-1"></i>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${etudiants}" var="etudiant">
                                        <tr>
                                            <td><code>${etudiant.numeroEtudiant}</code></td>
                                            <td><strong>${etudiant.nomComplet}</strong></td>
                                            <td>${etudiant.email}</td>
                                            <td>
                                                <c:if test="${not empty etudiant.filiere}">
                                                    <span class="badge bg-primary">${etudiant.filiere}</span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${not empty etudiant.dateNaissance}">
                                                    <fmt:formatDate value="${etudiant.dateNaissance}" pattern="dd/MM/yyyy"/>
                                                </c:if>
                                            </td>
                                            <td>
                                                <div class="table-actions justify-content-center">
                                                    <a href="${pageContext.request.contextPath}/etudiants?action=details&id=${etudiant.id}"
                                                       class="btn btn-sm btn-info" title="Voir détails">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/notes?etudiantId=${etudiant.id}"
                                                       class="btn btn-sm btn-success" title="Voir notes">
                                                        <i class="bi bi-journal-text"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/etudiants?action=modifier&id=${etudiant.id}"
                                                       class="btn btn-sm btn-warning" title="Modifier">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/etudiants?action=supprimer&id=${etudiant.id}"
                                                       class="btn btn-sm btn-danger" title="Supprimer"
                                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ${etudiant.nomComplet} ?');">
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
                            <h4 class="mt-3 text-muted">Aucun étudiant trouvé</h4>
                            <p class="text-muted mb-4">Commencez par ajouter votre premier étudiant</p>
                            <a href="${pageContext.request.contextPath}/etudiants?action=ajouter"
                               class="btn btn-primary">
                                <i class="bi bi-plus-circle me-2"></i>Ajouter le premier étudiant
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

    <!-- jQuery (required for DataTables) -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>

    <!-- DataTables Buttons -->
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/dataTables.buttons.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.bootstrap5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.html5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.print.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/vfs_fonts.js"></script>

    <script>
        $(document).ready(function() {
            // Check if table exists
            if ($('#etudiantsTable').length) {
                $('#etudiantsTable').DataTable({
                    // Pagination
                    pageLength: 10,
                    lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "Tous"]],

                    // Language - French
                    language: {
                        "sProcessing":     "Traitement en cours...",
                        "sSearch":         "Rechercher&nbsp;:",
                        "sLengthMenu":     "Afficher _MENU_ &eacute;tudiants",
                        "sInfo":           "Affichage de _START_ &agrave; _END_ sur _TOTAL_ &eacute;tudiants",
                        "sInfoEmpty":      "Affichage de 0 &agrave; 0 sur 0 &eacute;tudiant",
                        "sInfoFiltered":   "(filtr&eacute; de _MAX_ &eacute;tudiants au total)",
                        "sInfoPostFix":    "",
                        "sLoadingRecords": "Chargement en cours...",
                        "sZeroRecords":    "Aucun &eacute;tudiant &agrave; afficher",
                        "sEmptyTable":     "Aucune donn&eacute;e disponible",
                        "oPaginate": {
                            "sFirst":      "Premier",
                            "sPrevious":   "Pr&eacute;c&eacute;dent",
                            "sNext":       "Suivant",
                            "sLast":       "Dernier"
                        },
                        "oAria": {
                            "sSortAscending":  ": activer pour trier la colonne par ordre croissant",
                            "sSortDescending": ": activer pour trier la colonne par ordre d&eacute;croissant"
                        },
                        "select": {
                            "rows": {
                                "_": "%d lignes séléctionnées",
                                "0": "Aucune ligne séléctionnée",
                                "1": "1 ligne séléctionnée"
                            }
                        }
                    },

                    // Buttons for export
                    dom: '<"row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>' +
                         '<"row"<"col-sm-12"B>>' +
                         '<"row"<"col-sm-12"tr>>' +
                         '<"row"<"col-sm-12 col-md-5"i><"col-sm-12 col-md-7"p>>',

                    buttons: [
                        {
                            extend: 'copy',
                            text: '<i class="bi bi-clipboard me-1"></i> Copier',
                            className: 'btn-sm'
                        },
                        {
                            extend: 'excel',
                            text: '<i class="bi bi-file-earmark-excel me-1"></i> Excel',
                            className: 'btn-sm',
                            title: 'Liste des Étudiants - IUA',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4]
                            }
                        },
                        {
                            extend: 'pdf',
                            text: '<i class="bi bi-file-earmark-pdf me-1"></i> PDF',
                            className: 'btn-sm',
                            title: 'Liste des Étudiants - IUA',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4]
                            }
                        },
                        {
                            extend: 'print',
                            text: '<i class="bi bi-printer me-1"></i> Imprimer',
                            className: 'btn-sm',
                            title: 'Liste des Étudiants',
                            messageTop: '<h3>Master 1 GI/MIAGE - Institut Universitaire d\'Abidjan (IUA)</h3>',
                            exportOptions: {
                                columns: [0, 1, 2, 3, 4]
                            }
                        }
                    ],

                    // Column definitions
                    columnDefs: [
                        {
                            targets: 5, // Actions column
                            orderable: false,
                            searchable: false
                        }
                    ],

                    // Order by student name by default
                    order: [[1, 'asc']],

                    // Responsive
                    responsive: true
                });
            }
        });
    </script>
</body>
</html>
