package com.example.java_playground.domain.service;

import com.example.java_playground.domain.model.Singer;
import com.example.java_playground.domain.repository.SingerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerService {
    @Autowired
    private SingerRepository singerRepository;

    public Singer getSingerById(Long id) {
        return singerRepository.findById(id).orElse(null);
    }

    public List<Singer> getAllSingers() {
        return singerRepository.findAll();
    }

    public void saveSinger(Singer singer) {
        singerRepository.save(singer);
    }

    public void deleteSinger(Long id) {
        singerRepository.deleteById(id);
    }
}
