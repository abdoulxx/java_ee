-- ===============================================================================
-- SCRIPT COMPLET - BASE DE DONNÉES GESTION ÉTUDIANTS
-- Application Java EE - Master 1 GI/MIAGE - IUA 2025
-- ===============================================================================
-- Ce script crée la base de données complète avec:
-- - Création de la base de données
-- - Création des tables (etudiants, notes)
-- - Insertion des étudiants Master 1 GI/MIAGE 2024-2025
-- - Insertion des notes pour démonstration
-- - Création des vues et procédures stockées
-- ===============================================================================

-- ===============================================================================
-- 1. SUPPRESSION ET CRÉATION DE LA BASE DE DONNÉES
-- ===============================================================================

DROP DATABASE IF EXISTS gestion_etudiants;
CREATE DATABASE gestion_etudiants CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_etudiants;

-- ===============================================================================
-- 2. CRÉATION DES TABLES
-- ===============================================================================

-- Table des étudiants
CREATE TABLE etudiants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    date_naissance DATE,
    numero_etudiant VARCHAR(50) UNIQUE,
    filiere VARCHAR(100),
    INDEX idx_nom (nom),
    INDEX idx_filiere (filiere)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table des notes
CREATE TABLE notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    etudiant_id BIGINT NOT NULL,
    matiere VARCHAR(200) NOT NULL,
    valeur DOUBLE NOT NULL,
    coefficient DOUBLE NOT NULL DEFAULT 1.0,
    semestre INT,
    date_evaluation DATE,
    commentaire TEXT,
    FOREIGN KEY (etudiant_id) REFERENCES etudiants(id) ON DELETE CASCADE,
    INDEX idx_etudiant (etudiant_id),
    INDEX idx_matiere (matiere),
    INDEX idx_semestre (semestre),
    CONSTRAINT chk_valeur CHECK (valeur >= 0 AND valeur <= 20),
    CONSTRAINT chk_coefficient CHECK (coefficient > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===============================================================================
-- 3. INSERTION DES ÉTUDIANTS MASTER 1 GI/MIAGE 2024-2025
-- ===============================================================================

-- Étudiants GENIE INFORMATIQUE (GI)
INSERT INTO etudiants (nom, prenom, email, numero_etudiant, filiere, date_naissance) VALUES
('ADEGBENLE', 'OYEDIRAN FADIL ADEKUNLE', 'fadil.adegbenle@iua.edu.ci', '21INF013172', 'GI', '2000-01-15'),
('BANCE', 'BOUKARE', 'boukare.bance@iua.edu.ci', '21INF013922', 'GI', '1999-08-22'),
('BAKAYOKO', 'DJIGUIBA HAMED', 'hamed.bakayoko@iua.edu.ci', '24INF07077S', 'GI', '2000-03-10'),
('BOUABRE', 'ANGE CHRISTOPHER DEBE', 'ange.bouabre@iua.edu.ci', '24INF06003S', 'GI', '2001-05-18'),
('CAMARA', 'CHEICK ANLIOU JUNIOR', 'cheick.camara@iua.edu.ci', '21INF013810', 'GI', '2000-11-25'),
('DODO', 'JONATHAN', 'jonathan.dodo@iua.edu.ci', '21INF013632', 'GI', '1999-07-30'),
('N''GUESSAN', 'KRE FREDDY MARINA', 'freddy.nguessan@iua.edu.ci', '24INF07026S', 'GI', '2001-02-14'),
('SANGARE', 'ADAMA', 'adama.sangare@iua.edu.ci', '24INFO7053S', 'GI', '2000-09-05'),
('SAMB', 'ABDOULAYE SIDY', 'sidy.samb@iua.edu.ci', '21INF013398', 'GI', '1999-12-20');

-- Étudiants MIAGE
INSERT INTO etudiants (nom, prenom, email, numero_etudiant, filiere, date_naissance) VALUES
('ALLAH', 'AMON AYA NOEMIE CYRIELLE', 'noemie.allah@iua.edu.ci', 'ETU2024001', 'MIAGE', '2000-06-12'),
('DOUA', 'ZEREKO BORIS', 'boris.doua@iua.edu.ci', '21INF013127', 'MIAGE', '2000-04-08'),
('EBROTTIE', 'JEAN-PHILIPPE', 'jeanphilippe.ebrottie@iua.edu.ci', '24INF06008S', 'MIAGE', '1999-10-16'),
('KARAMOKO', 'MANKANE', 'mankane.karamoko@iua.edu.ci', '24INF05028S', 'MIAGE', '2001-01-22'),
('KONAN', 'LOIS NATHANAEL', 'lois.konan@iua.edu.ci', '22INF02330', 'MIAGE', '2000-08-19'),
('RAJI-AMAO', 'FAWASS ITUNU TUNJI JUNIOR', 'fawass.raji@iua.edu.ci', '22INF02491', 'MIAGE', '2000-03-27'),
('TRAORE', 'MARIAM NADIA', 'mariam.traore@iua.edu.ci', '24INFO7054S', 'MIAGE', '2001-07-14'),
('TRAORE', 'ABDOUL AZIZ', 'aziz.traore@iua.edu.ci', '21INF013535', 'MIAGE', '1999-11-03'),
('LOUKOU', 'SIEGNY MIKE WILSON', 'mike.loukou@iua.edu.ci', '17MI08217', 'MIAGE', '1998-05-29'),
('FOFANA', 'HAWA RACHIDA', 'hawa.fofana@iua.edu.ci', '21INF013972', 'MIAGE', '2000-02-11');

-- ===============================================================================
-- 4. INSERTION DES NOTES - SEMESTRE 7 - GENIE INFORMATIQUE (GI)
-- ===============================================================================

-- ADEGBENLE OYEDIRAN (ID 1)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(1, 'Algorithme avancée et étude de complexité', 14.5, 2, 7, '2024-11-15'),
(1, 'Structure de données avancées (liste, arbre, graphe)', 13.0, 2, 7, '2024-11-18'),
(1, 'Systèmes temps réels embarqués', 15.5, 3, 7, '2024-11-20'),
(1, 'Systèmes d''exploitation temps réels', 12.75, 3, 7, '2024-11-22'),
(1, 'Bases de données transactionnelles reparties', 16.0, 3, 7, '2024-11-25'),
(1, 'Systèmes de gestion de base de données', 14.25, 3, 7, '2024-11-27'),
(1, 'Compilation', 13.5, 2, 7, '2024-11-29'),
(1, 'Programmation côté serveur', 15.0, 3, 7, '2024-12-02'),
(1, 'Programmation avancée en java', 16.5, 3, 7, '2024-12-04'),
(1, 'Merise avancée', 12.0, 3, 7, '2024-12-06'),
(1, 'Langage de modélisation objet UML', 14.75, 3, 7, '2024-12-09');

-- BANCE BOUKARE (ID 2)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(2, 'Algorithme avancée et étude de complexité', 15.0, 2, 7, '2024-11-15'),
(2, 'Structure de données avancées (liste, arbre, graphe)', 16.5, 2, 7, '2024-11-18'),
(2, 'Systèmes temps réels embarqués', 14.0, 3, 7, '2024-11-20'),
(2, 'Systèmes d''exploitation temps réels', 15.5, 3, 7, '2024-11-22'),
(2, 'Bases de données transactionnelles reparties', 13.75, 3, 7, '2024-11-25'),
(2, 'Systèmes de gestion de base de données', 16.0, 3, 7, '2024-11-27'),
(2, 'Compilation', 14.5, 2, 7, '2024-11-29'),
(2, 'Programmation côté serveur', 15.25, 3, 7, '2024-12-02'),
(2, 'Programmation avancée en java', 17.0, 3, 7, '2024-12-04'),
(2, 'Merise avancée', 13.5, 3, 7, '2024-12-06'),
(2, 'Langage de modélisation objet UML', 15.75, 3, 7, '2024-12-09');

-- BAKAYOKO DJIGUIBA (ID 3)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(3, 'Algorithme avancée et étude de complexité', 12.5, 2, 7, '2024-11-15'),
(3, 'Structure de données avancées (liste, arbre, graphe)', 13.75, 2, 7, '2024-11-18'),
(3, 'Systèmes temps réels embarqués', 14.25, 3, 7, '2024-11-20'),
(3, 'Systèmes d''exploitation temps réels', 11.5, 3, 7, '2024-11-22'),
(3, 'Bases de données transactionnelles reparties', 15.0, 3, 7, '2024-11-25'),
(3, 'Systèmes de gestion de base de données', 13.25, 3, 7, '2024-11-27'),
(3, 'Compilation', 12.0, 2, 7, '2024-11-29'),
(3, 'Programmation côté serveur', 16.5, 3, 7, '2024-12-02'),
(3, 'Programmation avancée en java', 15.75, 3, 7, '2024-12-04');

-- BOUABRE ANGE (ID 4)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(4, 'Algorithme avancée et étude de complexité', 16.0, 2, 7, '2024-11-15'),
(4, 'Structure de données avancées (liste, arbre, graphe)', 15.5, 2, 7, '2024-11-18'),
(4, 'Systèmes temps réels embarqués', 17.0, 3, 7, '2024-11-20'),
(4, 'Bases de données transactionnelles reparties', 16.5, 3, 7, '2024-11-25'),
(4, 'Programmation côté serveur', 18.0, 3, 7, '2024-12-02'),
(4, 'Programmation avancée en java', 17.5, 3, 7, '2024-12-04');

-- CAMARA CHEICK (ID 5)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(5, 'Algorithme avancée et étude de complexité', 13.5, 2, 7, '2024-11-15'),
(5, 'Structure de données avancées (liste, arbre, graphe)', 14.0, 2, 7, '2024-11-18'),
(5, 'Systèmes temps réels embarqués', 15.25, 3, 7, '2024-11-20'),
(5, 'Systèmes de gestion de base de données', 14.5, 3, 7, '2024-11-27'),
(5, 'Programmation avancée en java', 16.0, 3, 7, '2024-12-04');

-- ===============================================================================
-- 5. INSERTION DES NOTES - SEMESTRE 8 - GENIE INFORMATIQUE (GI)
-- ===============================================================================

-- DODO JONATHAN (ID 6)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(6, 'Javascript et Framework associés', 14.0, 2, 8, '2024-12-10'),
(6, 'PHP et Framework associée', 15.5, 2, 8, '2024-12-12'),
(6, 'Logique des prédicats et systèmes experts', 13.25, 2, 8, '2024-12-14'),
(6, 'Apprentissage automatique', 16.0, 2, 8, '2024-12-16'),
(6, 'Java EE ou .Net', 17.5, 3, 8, '2024-12-18'),
(6, 'Développement mobiles', 15.0, 3, 8, '2024-12-20'),
(6, 'Industrialisation du développement', 14.5, 2, 8, '2024-12-22'),
(6, 'Tests logiciels', 13.75, 2, 8, '2024-12-24'),
(6, 'Architectures logicielles et web services', 16.25, 2, 8, '2024-12-26');

-- N'GUESSAN FREDDY (ID 7)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(7, 'Javascript et Framework associés', 15.5, 2, 8, '2024-12-10'),
(7, 'PHP et Framework associée', 14.0, 2, 8, '2024-12-12'),
(7, 'Apprentissage automatique', 17.0, 2, 8, '2024-12-16'),
(7, 'Java EE ou .Net', 16.5, 3, 8, '2024-12-18'),
(7, 'Développement mobiles', 17.0, 3, 8, '2024-12-20'),
(7, 'Architectures logicielles et web services', 15.25, 2, 8, '2024-12-26');

-- SANGARE ADAMA (ID 8)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(8, 'Javascript et Framework associés', 13.5, 2, 8, '2024-12-10'),
(8, 'PHP et Framework associée', 14.75, 2, 8, '2024-12-12'),
(8, 'Java EE ou .Net', 15.5, 3, 8, '2024-12-18'),
(8, 'Développement mobiles', 16.0, 3, 8, '2024-12-20');

-- SAMB ABDOULAYE (ID 9)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(9, 'Javascript et Framework associés', 16.5, 2, 8, '2024-12-10'),
(9, 'PHP et Framework associée', 15.0, 2, 8, '2024-12-12'),
(9, 'Logique des prédicats et systèmes experts', 14.5, 2, 8, '2024-12-14'),
(9, 'Java EE ou .Net', 18.0, 3, 8, '2024-12-18'),
(9, 'Développement mobiles', 17.25, 3, 8, '2024-12-20'),
(9, 'Tests logiciels', 15.5, 2, 8, '2024-12-24');

-- ===============================================================================
-- 6. INSERTION DES NOTES - SEMESTRE 7 - MIAGE
-- ===============================================================================

-- ALLAH AMON (ID 10)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(10, 'Algorithme avancée et étude de complexité', 16.0, 2, 7, '2024-11-15'),
(10, 'Structure de données avancées (liste, arbre, graphe)', 15.5, 2, 7, '2024-11-18'),
(10, 'Processus stochastique et chaîne de Markov', 14.0, 2, 7, '2024-11-20'),
(10, 'Recherche opérationnelle et optimisation combinatoire', 15.25, 2, 7, '2024-11-22'),
(10, 'Mathématique financière', 13.75, 2, 7, '2024-11-25'),
(10, 'Finance d''entreprise', 14.5, 2, 7, '2024-11-27'),
(10, 'Réseaux et services télécom', 16.5, 2, 7, '2024-11-29'),
(10, 'Systèmes d''exploitation, script shell', 15.0, 2, 7, '2024-12-02'),
(10, 'Entrepôts et informatique décisionnelle', 17.0, 2, 7, '2024-12-04'),
(10, 'Analyse de données et apprentissage automatique', 16.25, 2, 7, '2024-12-06'),
(10, 'Organisation de l''entreprise', 14.75, 2, 7, '2024-12-09'),
(10, 'Fiscalité des entreprises', 13.5, 2, 7, '2024-12-11'),
(10, 'Merise avancée', 15.5, 2, 7, '2024-12-13'),
(10, 'Langage de modélisation objet UML', 16.0, 2, 7, '2024-12-15');

-- DOUA ZEREKO (ID 11)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(11, 'Algorithme avancée et étude de complexité', 13.5, 2, 7, '2024-11-15'),
(11, 'Structure de données avancées (liste, arbre, graphe)', 14.75, 2, 7, '2024-11-18'),
(11, 'Processus stochastique et chaîne de Markov', 15.0, 2, 7, '2024-11-20'),
(11, 'Mathématique financière', 12.5, 2, 7, '2024-11-25'),
(11, 'Finance d''entreprise', 13.25, 2, 7, '2024-11-27'),
(11, 'Réseaux et services télécom', 16.0, 2, 7, '2024-11-29'),
(11, 'Entrepôts et informatique décisionnelle', 15.5, 2, 7, '2024-12-04'),
(11, 'Analyse de données et apprentissage automatique', 17.5, 2, 7, '2024-12-06');

-- EBROTTIE JEAN-PHILIPPE (ID 12)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(12, 'Algorithme avancée et étude de complexité', 15.0, 2, 7, '2024-11-15'),
(12, 'Structure de données avancées (liste, arbre, graphe)', 16.0, 2, 7, '2024-11-18'),
(12, 'Processus stochastique et chaîne de Markov', 16.5, 2, 7, '2024-11-20'),
(12, 'Recherche opérationnelle et optimisation combinatoire', 14.75, 2, 7, '2024-11-22'),
(12, 'Finance d''entreprise', 15.25, 2, 7, '2024-11-27'),
(12, 'Analyse de données et apprentissage automatique', 18.0, 2, 7, '2024-12-06');

-- KARAMOKO MANKANE (ID 13)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(13, 'Algorithme avancée et étude de complexité', 14.0, 2, 7, '2024-11-15'),
(13, 'Processus stochastique et chaîne de Markov', 13.5, 2, 7, '2024-11-20'),
(13, 'Mathématique financière', 15.0, 2, 7, '2024-11-25'),
(13, 'Réseaux et services télécom', 14.5, 2, 7, '2024-11-29'),
(13, 'Entrepôts et informatique décisionnelle', 16.5, 2, 7, '2024-12-04');

-- KONAN LOIS (ID 14)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(14, 'Algorithme avancée et étude de complexité', 17.0, 2, 7, '2024-11-15'),
(14, 'Structure de données avancées (liste, arbre, graphe)', 16.5, 2, 7, '2024-11-18'),
(14, 'Recherche opérationnelle et optimisation combinatoire', 15.5, 2, 7, '2024-11-22'),
(14, 'Analyse de données et apprentissage automatique', 18.5, 2, 7, '2024-12-06');

-- ===============================================================================
-- 7. INSERTION DES NOTES - SEMESTRE 8 - MIAGE
-- ===============================================================================

-- RAJI-AMAO FAWASS (ID 15)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(15, 'Javascript et Framework associés', 15.5, 2, 8, '2024-12-10'),
(15, 'PHP et Framework associée', 14.0, 2, 8, '2024-12-12'),
(15, 'Base de données avancées', 16.5, 2, 8, '2024-12-14'),
(15, 'Administration d''Oracle', 15.0, 2, 8, '2024-12-16'),
(15, 'Gestion des ressources humaines', 13.75, 2, 8, '2024-12-18'),
(15, 'Java EE ou .Net', 17.0, 3, 8, '2024-12-22'),
(15, 'Développement mobiles', 16.25, 3, 8, '2024-12-24'),
(15, 'Tests logiciels', 14.75, 2, 8, '2024-12-28'),
(15, 'Architectures logicielles et web services', 16.0, 2, 8, '2024-12-30');

-- TRAORE MARIAM (ID 16)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(16, 'Javascript et Framework associés', 14.5, 2, 8, '2024-12-10'),
(16, 'PHP et Framework associée', 15.75, 2, 8, '2024-12-12'),
(16, 'Base de données avancées', 17.0, 2, 8, '2024-12-14'),
(16, 'Java EE ou .Net', 18.5, 3, 8, '2024-12-22'),
(16, 'Développement mobiles', 16.5, 3, 8, '2024-12-24'),
(16, 'Architectures logicielles et web services', 15.25, 2, 8, '2024-12-30');

-- TRAORE ABDOUL (ID 17)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(17, 'Javascript et Framework associés', 13.0, 2, 8, '2024-12-10'),
(17, 'Base de données avancées', 14.5, 2, 8, '2024-12-14'),
(17, 'Java EE ou .Net', 15.5, 3, 8, '2024-12-22'),
(17, 'Développement mobiles', 14.0, 3, 8, '2024-12-24');

-- LOUKOU MIKE (ID 18)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(18, 'Javascript et Framework associés', 16.0, 2, 8, '2024-12-10'),
(18, 'PHP et Framework associée', 15.5, 2, 8, '2024-12-12'),
(18, 'Base de données avancées', 17.5, 2, 8, '2024-12-14'),
(18, 'Administration d''Oracle', 16.0, 2, 8, '2024-12-16'),
(18, 'Java EE ou .Net', 18.0, 3, 8, '2024-12-22'),
(18, 'Développement mobiles', 17.0, 3, 8, '2024-12-24');

-- FOFANA HAWA (ID 19)
INSERT INTO notes (etudiant_id, matiere, valeur, coefficient, semestre, date_evaluation) VALUES
(19, 'Javascript et Framework associés', 15.0, 2, 8, '2024-12-10'),
(19, 'PHP et Framework associée', 14.5, 2, 8, '2024-12-12'),
(19, 'Base de données avancées', 16.0, 2, 8, '2024-12-14'),
(19, 'Java EE ou .Net', 17.5, 3, 8, '2024-12-22'),
(19, 'Développement mobiles', 15.5, 3, 8, '2024-12-24'),
(19, 'Industrialisation du développement logiciel', 14.0, 2, 8, '2024-12-26');

-- ===============================================================================
-- 8. CRÉATION DES VUES
-- ===============================================================================

-- Vue: Moyennes des étudiants
CREATE OR REPLACE VIEW v_moyennes_etudiants AS
SELECT
    e.id,
    e.numero_etudiant,
    e.nom,
    e.prenom,
    e.filiere,
    COUNT(n.id) as nombre_notes,
    ROUND(SUM(n.valeur * n.coefficient) / SUM(n.coefficient), 2) as moyenne_generale,
    CASE
        WHEN SUM(n.valeur * n.coefficient) / SUM(n.coefficient) >= 16 THEN 'Très Bien'
        WHEN SUM(n.valeur * n.coefficient) / SUM(n.coefficient) >= 14 THEN 'Bien'
        WHEN SUM(n.valeur * n.coefficient) / SUM(n.coefficient) >= 12 THEN 'Assez Bien'
        WHEN SUM(n.valeur * n.coefficient) / SUM(n.coefficient) >= 10 THEN 'Passable'
        ELSE 'Insuffisant'
    END as mention
FROM etudiants e
LEFT JOIN notes n ON e.id = n.etudiant_id
GROUP BY e.id, e.numero_etudiant, e.nom, e.prenom, e.filiere;

-- Vue: Statistiques par matière
CREATE OR REPLACE VIEW v_stats_matieres AS
SELECT
    n.matiere,
    n.semestre,
    COUNT(*) as nombre_etudiants,
    ROUND(AVG(n.valeur), 2) as moyenne,
    ROUND(MIN(n.valeur), 2) as note_min,
    ROUND(MAX(n.valeur), 2) as note_max,
    ROUND(AVG(n.coefficient), 2) as coefficient_moyen
FROM notes n
GROUP BY n.matiere, n.semestre
ORDER BY n.semestre, n.matiere;

-- Vue: Étudiants avec mention
CREATE OR REPLACE VIEW v_etudiants_mention AS
SELECT
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
INNER JOIN notes n ON e.id = n.etudiant_id
GROUP BY e.id, e.numero_etudiant, e.nom, e.prenom, e.filiere
HAVING moyenne >= 10
ORDER BY moyenne DESC;

-- ===============================================================================
-- 9. CRÉATION DES PROCÉDURES STOCKÉES
-- ===============================================================================

DELIMITER //

-- Procédure: Calculer la moyenne d'un étudiant
CREATE PROCEDURE sp_calculer_moyenne_etudiant(IN p_etudiant_id BIGINT)
BEGIN
    SELECT
        e.numero_etudiant,
        CONCAT(e.prenom, ' ', e.nom) as nom_complet,
        e.filiere,
        COUNT(n.id) as nombre_notes,
        ROUND(SUM(n.valeur * n.coefficient) / SUM(n.coefficient), 2) as moyenne_generale,
        ROUND(MIN(n.valeur), 2) as note_min,
        ROUND(MAX(n.valeur), 2) as note_max
    FROM etudiants e
    LEFT JOIN notes n ON e.id = n.etudiant_id
    WHERE e.id = p_etudiant_id
    GROUP BY e.id, e.numero_etudiant, e.nom, e.prenom, e.filiere;
END //

-- Procédure: Classement des étudiants
CREATE PROCEDURE sp_classement_etudiants()
BEGIN
    SELECT
        ROW_NUMBER() OVER (ORDER BY SUM(n.valeur * n.coefficient) / SUM(n.coefficient) DESC) as rang,
        e.numero_etudiant,
        CONCAT(e.prenom, ' ', e.nom) as nom_complet,
        e.filiere,
        ROUND(SUM(n.valeur * n.coefficient) / SUM(n.coefficient), 2) as moyenne_generale
    FROM etudiants e
    INNER JOIN notes n ON e.id = n.etudiant_id
    GROUP BY e.id, e.numero_etudiant, e.nom, e.prenom, e.filiere
    ORDER BY moyenne_generale DESC;
END //

DELIMITER ;

-- ===============================================================================
-- 10. REQUÊTES DE VÉRIFICATION
-- ===============================================================================

-- Nombre d'étudiants par filière
SELECT
    filiere,
    COUNT(*) as nombre_etudiants
FROM etudiants
GROUP BY filiere
ORDER BY filiere;

-- Nombre de notes par semestre
SELECT
    semestre,
    COUNT(*) as nombre_notes,
    ROUND(AVG(valeur), 2) as moyenne_semestre
FROM notes
GROUP BY semestre
ORDER BY semestre;

-- Top 10 étudiants avec les meilleures moyennes
SELECT
    e.numero_etudiant,
    CONCAT(e.prenom, ' ', e.nom) as nom_complet,
    e.filiere,
    COUNT(n.id) as nb_notes,
    ROUND(SUM(n.valeur * n.coefficient) / SUM(n.coefficient), 2) as moyenne
FROM etudiants e
LEFT JOIN notes n ON e.id = n.etudiant_id
GROUP BY e.id, e.numero_etudiant, e.nom, e.prenom, e.filiere
HAVING COUNT(n.id) > 0
ORDER BY moyenne DESC
LIMIT 10;

-- Liste complète avec moyennes
SELECT * FROM v_moyennes_etudiants ORDER BY filiere, moyenne_generale DESC;

-- ===============================================================================
-- 11. RÉSUMÉ FINAL
-- ===============================================================================

SELECT '========================================' as '';
SELECT '     BASE DE DONNÉES CRÉÉE AVEC SUCCÈS!' as STATUT;
SELECT '========================================' as '';
SELECT CONCAT('✓ Total étudiants: ', COUNT(*)) as RESULTAT FROM etudiants;
SELECT CONCAT('✓ Total notes: ', COUNT(*)) as RESULTAT FROM notes;
SELECT CONCAT('✓ Étudiants GI: ', COUNT(*)) as RESULTAT FROM etudiants WHERE filiere = 'GI';
SELECT CONCAT('✓ Étudiants MIAGE: ', COUNT(*)) as RESULTAT FROM etudiants WHERE filiere = 'MIAGE';
SELECT '========================================' as '';
SELECT 'Application prête pour utilisation!' as MESSAGE;
SELECT '========================================' as '';

-- ===============================================================================
-- FIN DU SCRIPT
-- ===============================================================================
