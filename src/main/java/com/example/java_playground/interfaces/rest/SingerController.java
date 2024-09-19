package com.example.java_playground.interfaces.rest;

import com.example.java_playground.domain.model.Singer;
import com.example.java_playground.domain.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/singers")
public class SingerController {

    private final SingerService singerService;

    @Autowired
    public SingerController(SingerService singerService) {
        this.singerService = singerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Singer> getSingerById(@PathVariable Long id) {
        Singer singer = singerService.getSingerById(id);
        if (singer != null) {
            return ResponseEntity.ok(singer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Singer> getAllSingers() {
        return singerService.getAllSingers();
    }

    @PostMapping
    public ResponseEntity<Singer> createSinger(@RequestBody Singer singer) {
        singerService.saveSinger(singer);
        return ResponseEntity.ok(singer);
    }
}
