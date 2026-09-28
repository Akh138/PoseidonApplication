package com.nnk.springboot.curvepoint.repositories;

import com.nnk.springboot.curvepoint.domain.CurvePoint;
// Importation du Repository (la passerelle SQL)
import com.nnk.springboot.curvepoint.repositories.CurvePointRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // On allume le microservice en mode "Test"
public class CurvePointRepositoryTests {

    @Autowired // On demande à Spring de nous donner l'accès au répertoire
    private CurvePointRepository curvePointRepository;

    @Test // On définit la méthode de test
    public void curvePointRepositoryTest() {

        // --- 1. PRÉPARATION (On crée un point de test) ---
        // On utilise ton constructeur (CurveId, Term, Value)
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

        // --- 2. TEST DE SAUVEGARDE (CREATE) ---
        curvePoint = curvePointRepository.save(curvePoint); // On l'enregistre en base H2
        assertNotNull(curvePoint.getId()); // On vérifie que la base lui a donné un ID
        assertEquals(10, curvePoint.getCurveId()); // On vérifie que c'est le bon Curve ID

        // --- 3. TEST DE MODIFICATION (UPDATE) ---
        curvePoint.setTerm(20d); // On change le terme (10 -> 20)
        curvePoint = curvePointRepository.save(curvePoint); // On enregistre la modif
        assertEquals(20d, curvePoint.getTerm()); // On vérifie que la modif est prise en compte

        // --- 4. TEST DE LECTURE (READ) ---
        List<CurvePoint> list = curvePointRepository.findAll(); // On demande la liste
        assertTrue(list.size() > 0); // On vérifie qu'il y a au moins notre point dedans

        // --- 5. TEST DE SUPPRESSION (DELETE) ---
        Integer id = curvePoint.getId(); // On garde l'ID pour vérifier après
        curvePointRepository.delete(curvePoint); // On supprime
        Optional<CurvePoint> curvePointDeleted = curvePointRepository.findById(id); // On le recherche
        assertFalse(curvePointDeleted.isPresent()); // On vérifie qu'il n'existe plus (False)
    }
}