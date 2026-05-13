package com.nnk.springboot.ui.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    @NotBlank(message = "L'identifiant est obligatoire")
    private String username;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    @NotBlank(message = "Le nom complet est obligatoire")
    private String fullname;

    @NotBlank(message = "Le rôle est obligatoire")
    private String role;
}