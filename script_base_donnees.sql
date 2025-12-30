-- ============================================
-- Script SQL - Base de données Gestion Étudiants
-- Application Java EE - Master 1 GI/MIAGE - IUA 2025
-- ============================================

-- Suppression de la base si elle existe déjà
DROP DATABASE IF EXISTS gestion_etudiants;

-- Création de la base de données
CREATE DATABASE gestion_etudiants
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Utilisation de la base
USE gestion_etudiants;

-- ============================================
-- Table : etudiants
-- ============================================
CREATE TABLE etudiants (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    date_naissance DATE,
    numero_etudiant VARCHAR(50) UNIQUE,
    filiere VARCHAR(100),
    INDEX idx_nom (nom),
    INDEX idx_prenom (prenom),
    INDEX idx_filiere (filiere)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table : notes
-- ============================================
CREATE TABLE notes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    matiere VARCHAR(100) NOT NULL,
    valeur DOUBLE NOT NULL,
    coefficient DOUBLE NOT NULL DEFAULT 1.0,
    date_evaluation DATE,
    semestre INT,
    commentaire VARCHAR(500),
    etudiant_id BIGINT NOT NULL,
    FOREIGN KEY (etudiant_id) REFERENCES etudiants(id) ON DELETE CASCADE,
    INDEX idx_matiere (matiere),
    INDEX idx_etudiant (etudiant_id),
    INDEX idx_semestre (semestre),
    CONSTRAINT chk_valeur CHECK (valeur >= 0 AND valeur <= 20),
    CONSTRAINT chk_coefficient CHECK (coefficient > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Données de test pour la démonstration
-- ============================================

-- Insertion d'étudiants
INSERT INTO etudiants (nom, prenom, email, date_naissance, numero_etudiant, filiere) VALUES
('KOUASSI', 'Kofi', 'kofi.kouassi@iua.edu.ci', '2000-05-15', 'ETU2025001', 'MIAGE'),
('TRAORE', 'Aminata', 'aminata.traore@iua.edu.ci', '2001-08-22', 'ETU2025002', 'GI'),
('YAO', 'Jean-Pierre', 'jp.yao@iua.edu.ci', '2000-11-10', 'ETU2025003', 'MIAGE'),
('KONE', 'Fatou', 'fatou.kone@iua.edu.ci', '2001-03-17', 'ETU2025004', 'GI'),
('DIALLO', 'Moussa', 'moussa.diallo@iua.edu.ci', '2000-07-28', 'ETU2025005', 'MIAGE'),
('BAMBA', 'Mariam', 'mariam.bamba@iua.edu.ci', '2001-01-05', 'ETU2025006', 'Informatique'),
('OUATTARA', 'Ibrahim', 'ibrahim.ouattara@iua.edu.ci', '2000-09-30', 'ETU2025007', 'GI'),
('SANGARE', 'Aïcha', 'aicha.sangare@iua.edu.ci', '2001-06-12', 'ETU2025008', 'MIAGE'),
('TOURE', 'Sekou', 'sekou.toure@iua.edu.ci', '2000-12-20', 'ETU2025009', 'Réseaux'),
('CISSE', 'Mariama', 'mariama.cisse@iua.edu.ci', '2001-04-08', 'ETU2025010', 'Informatique');

-- Insertion de notes pour KOUASSI Kofi (id=1)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 15.5, 3.0, '2024-11-15', 1, 1, 'Très bon travail sur les concepts orientés objet'),
('Base de données', 14.0, 2.5, '2024-11-20', 1, 1, 'Bonne maîtrise de SQL'),
('Mathématiques', 12.5, 2.0, '2024-11-25', 1, 1, 'Effort à faire sur les probabilités'),
('Réseaux', 16.0, 2.0, '2024-12-01', 1, 1, 'Excellente compréhension des protocoles'),
('Génie Logiciel', 13.5, 2.5, '2024-12-05', 1, 1, NULL);

-- Insertion de notes pour TRAORE Aminata (id=2)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 17.5, 3.0, '2024-11-15', 1, 2, 'Excellente étudiante, très rigoureuse'),
('Base de données', 16.5, 2.5, '2024-11-20', 1, 2, 'Maîtrise parfaite des jointures'),
('Mathématiques', 15.0, 2.0, '2024-11-25', 1, 2, 'Très bon niveau'),
('Algorithme', 18.0, 3.0, '2024-12-02', 1, 2, 'Capacités analytiques remarquables'),
('Web', 16.0, 2.0, '2024-12-06', 1, 2, 'Projet web très bien réalisé');

-- Insertion de notes pour YAO Jean-Pierre (id=3)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 11.5, 3.0, '2024-11-15', 1, 3, 'Des efforts sont nécessaires'),
('Base de données', 10.0, 2.5, '2024-11-20', 1, 3, 'Juste la moyenne'),
('Mathématiques', 9.5, 2.0, '2024-11-25', 1, 3, 'Besoin de plus de travail'),
('Réseaux', 12.0, 2.0, '2024-12-01', 1, 3, NULL);

-- Insertion de notes pour KONE Fatou (id=4)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 14.0, 3.0, '2024-11-15', 1, 4, 'Bon niveau général'),
('Base de données', 15.5, 2.5, '2024-11-20', 1, 4, 'Très bonne compréhension'),
('Algorithme', 16.0, 3.0, '2024-12-02', 1, 4, 'Excellente logique'),
('Web', 14.5, 2.0, '2024-12-06', 1, 4, NULL);

-- Insertion de notes pour DIALLO Moussa (id=5)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 13.0, 3.0, '2024-11-15', 1, 5, 'Progrès réguliers'),
('Base de données', 12.5, 2.5, '2024-11-20', 1, 5, 'Assez bien'),
('Mathématiques', 14.0, 2.0, '2024-11-25', 1, 5, 'Bon niveau en maths'),
('Génie Logiciel', 15.0, 2.5, '2024-12-05', 1, 5, 'Bon travail en équipe');

-- Insertion de notes pour BAMBA Mariam (id=6)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 16.5, 3.0, '2024-11-15', 1, 6, 'Excellente programmeuse'),
('Systèmes d\'exploitation', 15.0, 2.5, '2024-11-22', 1, 6, 'Bonne maîtrise de Linux'),
('Algorithme', 17.0, 3.0, '2024-12-02', 1, 6, 'Très forte en algo'),
('Web', 15.5, 2.0, '2024-12-06', 1, 6, NULL);

-- Insertion de notes pour OUATTARA Ibrahim (id=7)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 12.0, 3.0, '2024-11-15', 1, 7, 'Peut mieux faire'),
('Base de données', 13.5, 2.5, '2024-11-20', 1, 7, 'Assez bien'),
('Réseaux', 14.5, 2.0, '2024-12-01', 1, 7, 'Bonne maîtrise des réseaux');

-- Insertion de notes pour SANGARE Aïcha (id=8)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 18.0, 3.0, '2024-11-15', 1, 8, 'Excellente étudiante'),
('Base de données', 17.5, 2.5, '2024-11-20', 1, 8, 'Maîtrise exceptionnelle'),
('Mathématiques', 16.5, 2.0, '2024-11-25', 1, 8, 'Très forte en maths'),
('Algorithme', 18.5, 3.0, '2024-12-02', 1, 8, 'Niveau exceptionnel'),
('Génie Logiciel', 17.0, 2.5, '2024-12-05', 1, 8, 'Excellente méthodologie');

-- Insertion de notes pour TOURE Sekou (id=9)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Réseaux', 16.5, 3.0, '2024-12-01', 1, 9, 'Spécialiste des réseaux'),
('Systèmes d\'exploitation', 15.5, 2.5, '2024-11-22', 1, 9, 'Très bon niveau'),
('Sécurité informatique', 17.0, 2.5, '2024-12-10', 1, 9, 'Excellente compréhension');

-- Insertion de notes pour CISSE Mariama (id=10)
INSERT INTO notes (matiere, valeur, coefficient, date_evaluation, semestre, etudiant_id, commentaire) VALUES
('Programmation Java', 14.5, 3.0, '2024-11-15', 1, 10, 'Bon travail'),
('Base de données', 13.0, 2.5, '2024-11-20', 1, 10, 'Assez bien'),
('Web', 15.0, 2.0, '2024-12-06', 1, 10, 'Bonnes compétences web'),
('Algorithme', 14.0, 3.0, '2024-12-02', 1, 10, NULL);

-- ============================================
-- Vues utiles pour les statistiques
-- ============================================

-- Vue : Moyenne par étudiant
CREATE VIEW v_moyennes_etudiants AS
SELECT
    e.id,
    e.numero_etudiant,
    e.nom,
    e.prenom,
    e.filiere,
    COUNT(n.id) as nombre_notes,
    ROUND(SUM(n.valeur * n.coefficient) / SUM(n.coefficient), 2) as moyenne_generale,
    MIN(n.valeur) as note_min,
    MAX(n.valeur) as note_max
FROM etudiants e
LEFT JOIN notes n ON e.id = n.etudiant_id
GROUP BY e.id, e.numero_etudiant, e.nom, e.prenom, e.filiere;

-- Vue : Statistiques par matière
CREATE VIEW v_stats_matieres AS
SELECT
    n.matiere,
    COUNT(DISTINCT n.etudiant_id) as nombre_etudiants,
    COUNT(n.id) as nombre_notes,
    ROUND(AVG(n.valeur), 2) as moyenne_matiere,
    MIN(n.valeur) as note_min,
    MAX(n.valeur) as note_max
FROM notes n
GROUP BY n.matiere
ORDER BY n.matiere;

-- Vue : Étudiants avec mention
CREATE VIEW v_etudiants_mention AS
SELECT
    e.id,
    e.numero_etudiant,
    CONCAT(e.prenom, ' ', e.nom) as nom_complet,
    e.filiere,
    ROUND(SUM(n.valeur * n.coefficient) / SUM(n.coefficient), 2) as moyenne,
    CASE
        WHEN SUM(n.valeur * n.coefficient) / SUM(n.coefficient) >= 16 THEN 'Très Bien'
        WHEN SUM(n.valeur * n.coefficient) / SUM(n.coefficient) >= 14 THEN 'Bien'
        WHEN SUM(n.valeur * n.coefficient) / SUM(n.coefficient) >= 12 THEN 'Assez Bien'
        WHEN SUM(n.valeur * n.coefficient) / SUM(n.coefficient) >= 10 THEN 'Passable'
        ELSE 'Insuffisant'
    END as mention
FROM etudiants e
LEFT JOIN notes n ON e.id = n.etudiant_id
GROUP BY e.id, e.numero_etudiant, e.nom, e.prenom, e.filiere
HAVING COUNT(n.id) > 0
ORDER BY moyenne DESC;

-- ============================================
-- Procédures stockées utiles
-- ============================================

DELIMITER //

-- Procédure : Calculer la moyenne d'un étudiant
CREATE PROCEDURE sp_calculer_moyenne_etudiant(IN p_etudiant_id BIGINT)
BEGIN
    SELECT
        e.id,
        CONCAT(e.prenom, ' ', e.nom) as nom_complet,
        e.filiere,
        COUNT(n.id) as nombre_notes,
        ROUND(SUM(n.valeur * n.coefficient) / SUM(n.coefficient), 2) as moyenne_generale
    FROM etudiants e
    LEFT JOIN notes n ON e.id = n.etudiant_id
    WHERE e.id = p_etudiant_id
    GROUP BY e.id, e.nom, e.prenom, e.filiere;
END //

-- Procédure : Obtenir le classement des étudiants
CREATE PROCEDURE sp_classement_etudiants()
BEGIN
    SELECT
        ROW_NUMBER() OVER (ORDER BY SUM(n.valeur * n.coefficient) / SUM(n.coefficient) DESC) as rang,
        e.numero_etudiant,
        CONCAT(e.prenom, ' ', e.nom) as nom_complet,
        e.filiere,
        COUNT(n.id) as nombre_notes,
        ROUND(SUM(n.valeur * n.coefficient) / SUM(n.coefficient), 2) as moyenne
    FROM etudiants e
    LEFT JOIN notes n ON e.id = n.etudiant_id
    GROUP BY e.id, e.numero_etudiant, e.nom, e.prenom, e.filiere
    HAVING COUNT(n.id) > 0
    ORDER BY moyenne DESC;
END //

DELIMITER ;

-- ============================================
-- Requêtes de vérification
-- ============================================

-- Vérifier les données insérées
SELECT 'Nombre d\'étudiants :' as Info, COUNT(*) as Total FROM etudiants
UNION ALL
SELECT 'Nombre de notes :', COUNT(*) FROM notes;

-- Afficher les moyennes
SELECT * FROM v_moyennes_etudiants ORDER BY moyenne_generale DESC;

-- Afficher le classement
CALL sp_classement_etudiants();

-- ============================================
-- Fin du script
-- ============================================
