package com.nnk.springboot.rulename.repositories;

import  com.nnk.springboot.rulename.domain.RuleName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class RuleNameRepositoryTests {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    public void ruleNameRepositoryTest(){
        RuleName rule = new RuleName(null, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQL");
        rule.setSqlPart("SQL Part");

        //Create
        rule = ruleNameRepository.save(rule);
        assertNotNull(rule.getId());
        assertEquals("Rule Name", rule.getName());

        //Update
        rule.setDescription("Salut");
        rule = ruleNameRepository.save(rule);
        assertEquals("Salut", rule.getDescription());

        //Read
        List<RuleName> list =ruleNameRepository.findAll();
        assertTrue(list.size() > 0);

        //Delete
        Integer id = rule.getId();
        ruleNameRepository.delete(rule);
        Optional<RuleName> ruleNameDeleted = ruleNameRepository.findById(id);
        assertFalse(ruleNameDeleted.isPresent());

    }


}
