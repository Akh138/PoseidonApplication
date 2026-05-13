package com.nnk.springboot.ui.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data // Génère Getters, Setters, toString, equals, hashCode
@NoArgsConstructor // Génère le constructeur vide (OBLIGATOIRE pour Jackson/JSON)
@AllArgsConstructor // Génère le constructeur avec tous les champs
public class Trade {
    private Integer tradeId;

    @NotBlank(message = "Le compte (Account) est obligatoire")
    private String account;

    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @NotNull(message = "La quantité d'achat est obligatoire")
    @Positive(message = "La quantité doit être supérieure à zéro")
    private Double buyQuantity;


    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
