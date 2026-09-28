package com.nnk.springboot.rating.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.rating.domain.Rating;
import com.nnk.springboot.rating.repositories.RatingRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingRepository ratingRepository;

    @Autowired
    private ObjectMapper objectMapper;



    // --- TEST : VOIR LA LISTE ---
    @Test
    public void getAllRatingsTest() throws Exception {
        List<Rating> list = new ArrayList<>();
        list.add(new Rating("Aaa", "AAA", "AAA", 10));

        Mockito.when(ratingRepository.findAll()).thenReturn(list);

        mockMvc.perform(get("/rating/test"))
                .andExpect(status().isOk());
    }

    // --- TEST : AJOUTER ---
    @Test
    public void addRatingTest() throws Exception {
        Rating rating = new Rating("Aaa", "AAA", "AAA", 10);

        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rating)))
                .andExpect(status().isOk());

        Mockito.verify(ratingRepository, Mockito.times(1)).save(Mockito.any(Rating.class));
    }


    // --- TEST : RÉCUPÉRER PAR ID ---
    @Test
    public void getRatingByIdTest() throws Exception {
        Rating rating = new Rating("Aaa", "AAA", "AAA", 10);
        rating.setId(1);

        Mockito.when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        mockMvc.perform(get("/rating/get/1"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1));
    }


    // --- TEST : METTRE À JOUR ---
    @Test
    public void updateRatingTest() throws Exception {
        Rating rating = new Rating("Aaa", "AAA", "AAA", 10);
        rating.setId(1);

        mockMvc.perform(post("/rating/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rating)))
                .andExpect(status().isOk());

        Mockito.verify(ratingRepository, Mockito.times(1)).save(Mockito.any(Rating.class));
    }


    // --- TEST : SUPPRIMER ---
    @Test
    public void deleteRatingTest() throws Exception {
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().isOk());

        Mockito.verify(ratingRepository, Mockito.times(1)).deleteById(1);
    }
}