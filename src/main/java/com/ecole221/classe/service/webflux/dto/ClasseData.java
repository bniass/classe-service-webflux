package com.ecole221.classe.service.webflux.dto;

public record ClasseData(String code, String libelleclasse, int fraisInscription,
                         int autresFrais, int mensualite, String libellefiliere) {
}
