INSERT INTO filiere (code, libelle) VALUES
('GL', 'Génie Logiciel'),
('G-ECO', 'Génie Economie'),
('RT', 'Réseaux Télécom'),
('COMPT', 'Comptabilité & Gestion');

INSERT INTO classe (code, libelle, frais_inscription, mensualite, autres_frais, filiereId) VALUES
('L1 GL', 'Licence 1 Génie Logiciel', 50000, 25000, 10000, 1),
('L2 GL', 'Licence 2 Génie Logiciel', 60000, 30000, 12000, 1),
('L1 G-ECO', 'Licence 1 Génie Economie', 45000, 20000, 8000, 2),
('L1 RT', 'Licence 1 Réseaux Télécom', 55000, 27000, 9000, 3),
('L1 COMPT', 'Licence 1 Comptabilité & Gestion', 48000, 22000, 8500, 4);