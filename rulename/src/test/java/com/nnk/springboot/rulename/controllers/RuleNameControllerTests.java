package com.nnk.springboot.rulename.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.rulename.domain.RuleName;
import com.nnk.springboot.rulename.repositories.RuleNameRepository;
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
public class RuleNameControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameRepository ruleNameRepository; // Notre faux répertoire

    @Autowired
    private ObjectMapper objectMapper;



    // --- TEST : VOIR LA LISTE ---
    @Test
    public void getAllRulesTest() throws Exception {
        List<RuleName> list = new ArrayList<>();
        list.add(new RuleName(1,"Rule Name", "Description", "Json", "Template", "SQL", "SQL Part"));
        Mockito.when(ruleNameRepository.findAll()).thenReturn(list);

        mockMvc.perform(get("/rulename/test"))
                .andExpect(status().isOk());



        // 1. Prépare une liste avec une règle (RuleName)
        // 2. Mockito.when(...) -> simule le retour du repository
        // 3. mockMvc.perform(get("/rulename/test")) -> simule l'appel
    }
    // --- TEST : AJOUTER ---
    @Test
    public void addRuleNameTest() throws Exception {
        RuleName ruleName = new RuleName(1, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");


        // 1. Prépare un objet RuleName
        // 2. mockMvc.perform(post("/rulename/add")...)
    }

    // TODO: Tu peux aussi ajouter getById, update et delete si tu te sens chaud !
}