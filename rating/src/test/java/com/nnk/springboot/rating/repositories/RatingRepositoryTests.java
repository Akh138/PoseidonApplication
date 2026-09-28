package com.nnk.springboot.rating.repositories;

import com.nnk.springboot.rating.domain.Rating;
import com.nnk.springboot.rating.repositories.RatingRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class RatingRepositoryTests {

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void ratingRepositoryTest(){

        Rating rating = new Rating("Aaa", "AAA", "AAA", 10); // Moody's, S&P, Fitch, Order

        // Create
        rating = ratingRepository.save(rating);
        assertNotNull(rating.getMoodysRating());
        assertEquals("Aaa", rating.getMoodysRating());


        //update
        rating.setSandPRating("BBB");
        rating = ratingRepository.save(rating);
        assertEquals("BBB", rating.getSandPRating());

        //Read
        List<Rating> list = ratingRepository.findAll();
        assertTrue(list.size() > 0);

        // Delete
        Integer id = rating.getId();
        ratingRepository.delete(rating);
        Optional<Rating> ratingDeleted = ratingRepository.findById(id);
        assertFalse(ratingDeleted.isPresent());
    }
}
