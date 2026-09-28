package com.nnk.springboot.bidlist.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.bidlist.domain.BidList;
import com.nnk.springboot.bidlist.repositories.BidListRepository;
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
public class BidListControllerTests {

    @Autowired
    private MockMvc mockMvc; // Le simulateur de navigateur

    @MockBean
    private BidListRepository bidListRepository; // Le faux répertoire (Mock)

    @Autowired
    private ObjectMapper objectMapper; // Le traducteur Java <-> JSON

    // --- TEST : VOIR TOUTE LA LISTE ---
    @Test
    public void getAllBidsTest() throws Exception {
        List<BidList> allBids = new ArrayList<>();
        allBids.add(new BidList());

        // On dit au faux répertoire : "Quand on te demande tout, donne cette liste"
        Mockito.when(bidListRepository.findAll()).thenReturn(allBids);

        // On tape l'URL /bidList/test et on vérifie que le serveur répond 200 OK
        mockMvc.perform(get("/bidList/test"))
                .andExpect(status().isOk());
    }

    // --- TEST : AJOUTER UNE LIGNE ---
    @Test
    public void addBidListTest() throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Test Account");

        // On simule l'envoi du formulaire (POST) en format JSON
        mockMvc.perform(post("/bidList/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isOk());

        // On vérifie que le répertoire a bien reçu l'ordre d'enregistrer
        Mockito.verify(bidListRepository, Mockito.times(1)).save(Mockito.any(BidList.class));
    }

    // --- TEST : RÉCUPÉRER UNE LIGNE PRÉCISE ---
    @Test
    public void getBidListByIdTest() throws Exception {
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Test Account");

        // On dit au Mock : "Si on cherche l'ID 1, renvoie cet objet"
        Mockito.when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        mockMvc.perform(get("/bidList/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account").value("Test Account"));
    }

    // --- TEST : METTRE À JOUR ---
    @Test
    public void updateBidListTest() throws Exception {
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Updated Account");

        // On simule l'envoi de la modification
        mockMvc.perform(post("/bidList/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isOk());

        // On vérifie que la sauvegarde a été demandée
        Mockito.verify(bidListRepository, Mockito.times(1)).save(Mockito.any(BidList.class));
    }

    // --- TEST : SUPPRIMER ---
    @Test
    public void deleteBidListTest() throws Exception {
        // On simule le clic sur supprimer pour l'ID 1
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().isOk());

        // On vérifie que l'ordre de suppression a été donné au répertoire
        Mockito.verify(bidListRepository, Mockito.times(1)).deleteById(1);
    }
}