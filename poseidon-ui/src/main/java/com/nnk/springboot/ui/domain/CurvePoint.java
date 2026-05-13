package com.nnk.springboot.ui.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data // Génère Getters, Setters, toString, equals, hashCode
@NoArgsConstructor // Génère le constructeur vide (OBLIGATOIRE pour Jackson/JSON)
@AllArgsConstructor // Génère le constructeur avec tous les champs
public class CurvePoint {

    private Integer id;

    @NotNull(message = "Curve Id est obligatoire")
    private Integer curveId;

    private Timestamp asOfDate;
    @NotNull(message = "Le terme est obligatoire")
    private Double term;

    @NotNull(message = "La valeur est obligatoire")
    private Double value;
    private Timestamp creationDate;

    // On garde ce constructeur manuel, car il est utilisé dans le projet
    // pour créer des objets rapidement avec seulement trois infos.
    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}