package com.ecole221.classe.service.webflux.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classe {
    @Id
    private long id;
    private String code;
    private String libelle;
    private int fraisInscription;
    private int mensualite;
    private int autresFrais;
    private long filiereid;
}
