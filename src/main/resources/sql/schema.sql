DROP SCHEMA IF EXISTS classe_service_r2dbc CASCADE;

DROP TABLE IF EXISTS filiere CASCADE;

create table filiere(
    id serial primary key,
    code varchar(10),
    libelle varchar(100)
);

DROP TABLE IF EXISTS classe CASCADE;

create table classe(
    id serial primary key,
    code varchar(10),
    libelle varchar(100),
    frais_inscription integer,
    mensualite integer,
    autres_frais integer,
    filiereId integer,
    CONSTRAINT fk_filiere FOREIGN KEY (filiereId) REFERENCES filiere (id) ON DELETE CASCADE
);
