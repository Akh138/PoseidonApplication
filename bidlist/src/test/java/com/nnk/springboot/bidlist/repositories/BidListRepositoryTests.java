package com.nnk.springboot.bidlist.repositories;

import com.nnk.springboot.bidlist.domain.BidList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // <--- C'est ça qui transforme le test en "Test d'Intégration".
// Ça allume tout le microservice en mode "Test".
public class BidListRepositoryTests {

    @Autowired // <--- On demande à Spring de nous prêter le Repository pour le test.
    private BidListRepository bidListRepository;

    @Test // <--- Dit à Java : "Exécute ce bloc comme un test indépendant"
    public void bidListRepositoryTest() {
        // --- 1. PRÉPARATION (On crée un objet test) ---
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        // --- 2. TEST DE LA SAUVEGARDE (CREATE) ---
        bid = bidListRepository.save(bid); // On enregistre en base
        assertNotNull(bid.getBidListId()); // "Le juge vérifie" : L'ID ne doit pas être vide !
        assertEquals(10d, bid.getBidQuantity()); // "Le juge vérifie" : La quantité doit être 10.

        // --- 3. TEST DE LA MISE À JOUR (UPDATE) ---
        bid.setBidQuantity(20d); // On change la valeur
        bid = bidListRepository.save(bid); // On ré-enregistre
        assertEquals(20d, bid.getBidQuantity()); // "Le juge vérifie" : Ça a bien été modifié ?

        // --- 4. TEST DE LA LECTURE (READ) ---
        List<BidList> listRetrieved = bidListRepository.findAll(); // On demande la liste complète
        assertTrue(listRetrieved.size() > 0); // "Le juge vérifie" : La liste ne doit pas être vide !

        // --- 5. TEST DE LA SUPPRESSION (DELETE) ---
        Integer id = bid.getBidListId(); // On mémorise l'ID qu'on va effacer
        bidListRepository.delete(bid); // On supprime
        Optional<BidList> bidDeleted = bidListRepository.findById(id); // On essaie de le rechercher
        assertFalse(bidDeleted.isPresent()); // "Le juge vérifie" : Il ne doit plus exister !
    }
}