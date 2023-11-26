package com.gm2.cryptoapp.controller;

import com.gm2.cryptoapp.entity.Moeda;
import com.gm2.cryptoapp.repository.MoedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@RestController
@RequestMapping("/moeda")
public class MoedaController {

    @Autowired
    private MoedaRepository moedaRepository;

    @GetMapping()
    public ResponseEntity get(){
        return new ResponseEntity<>(moedaRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{nome}")
    public ResponseEntity get(@PathVariable String nome){
        try {
            return new ResponseEntity<>(moedaRepository.getByName(nome), HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody Moeda moeda) {
        try {
            moeda.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(moedaRepository.insert(moeda), HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@RequestBody Moeda moeda){
        try{
            moeda.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(moedaRepository.update(moeda), HttpStatus.OK);
        } catch(Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        boolean response = false;
        try {
            response = moedaRepository.remove(id);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}

