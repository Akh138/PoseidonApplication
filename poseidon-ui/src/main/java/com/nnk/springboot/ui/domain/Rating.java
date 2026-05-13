package com.nnk.springboot.ui.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data // Génère Getters, Setters, toString, equals, hashCode
@NoArgsConstructor // Génère le constructeur vide (OBLIGATOIRE pour Jackson/JSON)
@AllArgsConstructor // Génère le constructeur avec tous les champs

public class Rating {

    private Integer id;

    @NotBlank(message = "Moody's Rating est obligatoire")
    private String moodysRating;

    @NotBlank(message = "S&P Rating est obligatoire")
    private String sandPRating;

    @NotBlank(message = "Fitch Rating est obligatoire")
    private String fitchRating;

    @NotNull(message = "Le numéro d'ordre est obligatoire")
    private Integer orderNumber;



    // Constructeur manuel pour tes tests
    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
