package com.nnk.springboot.curvepoint.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.curvepoint.domain.CurvePoint;
import com.nnk.springboot.curvepoint.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// On importe les outils pour simuler les appels web
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTests {

    @Autowired
    private MockMvc mockMvc; // Notre navigateur virtuel

    @MockBean
    private CurvePointRepository curvePointRepository; // Notre faux répertoire

    @Autowired
    private ObjectMapper objectMapper; // Pour transformer Java en JSON

    // --- TEST : VOIR LA LISTE ---
    @Test
    public void getAllPointsTest() throws Exception {
        List<CurvePoint> list = new ArrayList<>();
        list.add(new CurvePoint(10, 10d, 30d));

        Mockito.when(curvePointRepository.findAll()).thenReturn(list);

        mockMvc.perform(get("/curvePoint/test"))
                .andExpect(status().isOk());
    }

    // --- TEST : AJOUTER (Celui que tu as fait, en plus court) ---
    @Test
    public void addCurvePointTest() throws Exception {
        // On crée l'objet directement avec le constructeur
        CurvePoint cp = new CurvePoint(10, 10d, 30d);

        mockMvc.perform(post("/curvePoint/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cp)))
                .andExpect(status().isOk());

        // On vérifie que le repository a bien été sollicité
        Mockito.verify(curvePointRepository, Mockito.times(1)).save(Mockito.any(CurvePoint.class));
    }

    // --- TEST : RÉCUPÉRER PAR ID ---
    @Test
    public void getCurvePointByIdTest() throws Exception {
        CurvePoint cp = new CurvePoint(10, 10d, 30d);
        cp.setId(1);

        Mockito.when(curvePointRepository.findById(1)).thenReturn(Optional.of(cp));

        mockMvc.perform(get("/curvePoint/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.curveId").value(10)); // On vérifie que le CurveId est bien 10.
    }

    // --- TEST : METTRE À JOUR ---
    @Test
    public void updateCurvePointTest() throws Exception {
        CurvePoint cp = new CurvePoint(10, 20d, 40d);
        cp.setId(1);

        mockMvc.perform(post("/curvePoint/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cp)))
                .andExpect(status().isOk());

        Mockito.verify(curvePointRepository, Mockito.times(1)).save(Mockito.any(CurvePoint.class));
    }

    // --- TEST : SUPPRIMER ---
    @Test
    public void deleteCurvePointTest() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().isOk());

        Mockito.verify(curvePointRepository, Mockito.times(1)).deleteById(1);
    }
}