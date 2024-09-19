package com.example.java_playground.interfaces.rest;

import com.example.java_playground.domain.model.Singer;
import com.example.java_playground.domain.repository.SingerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SingerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SingerRepository singerRepository;

    @BeforeEach
    public void setup() {
        singerRepository.deleteAll();
    }

    @Test
    public void testGetSingerById() throws Exception {
        Singer singer = new Singer(null, "John Doe", "Rock");
        singer = singerRepository.save(singer);

        mockMvc.perform(get("/api/singers/{id}", singer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.genre").value("Rock"));
    }

    @Test
    public void testGetAllSingers() throws Exception {
        Singer singer1 = new Singer(null, "John Doe", "Rock");
        Singer singer2 = new Singer(null, "Jane Doe", "Pop");
        singerRepository.save(singer1);
        singerRepository.save(singer2);

        mockMvc.perform(get("/api/singers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    public void testCreateSinger() throws Exception {
        String singerJson = "{\"name\":\"John Doe\",\"genre\":\"Rock\"}";

        mockMvc.perform(post("/api/singers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(singerJson))
                .andExpect(status().isOk()) // Changed to 201 Created if your endpoint returns this status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.genre").value("Rock"));

        Optional<Singer> singer = singerRepository.findAll().stream().findFirst();
        if (singer.isPresent()) {
            assert "John Doe".equals(singer.get().getName());
            assert "Rock".equals(singer.get().getGenre());
        } else {
            throw new AssertionError("Expected singer not found in repository.");
        }
    }
}