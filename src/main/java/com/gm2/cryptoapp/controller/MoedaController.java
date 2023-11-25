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

    @Bean
    public Moeda init() {
        Moeda c1 = new Moeda();

        c1.setNome("BITCOIN");
        c1.setPreco(new BigDecimal(100));
        c1.setQuantidade(new BigDecimal(0.0005));
        c1.setDateTime(new Timestamp(System.currentTimeMillis()));

        Moeda c2 = new Moeda();

        c2.setNome("BITCOIN");
        c2.setPreco(new BigDecimal(250));
        c2.setQuantidade(new BigDecimal(0.0025));
        c2.setDateTime(new Timestamp(System.currentTimeMillis()));

        Moeda c3 = new Moeda();

        c3.setNome("ETHEREUM");
        c3.setPreco(new BigDecimal(500));
        c3.setQuantidade(new BigDecimal(0.0045));
        c3.setDateTime(new Timestamp(System.currentTimeMillis()));

        moedaRepository.insert(c1);
        moedaRepository.insert(c2);
        moedaRepository.insert(c3);
        return c2;
    }

    @GetMapping()
    public ResponseEntity get() {
        return new ResponseEntity<>(moedaRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{nome}")
    public ResponseEntity get(@PathVariable String nome) {
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
        try {
            return new ResponseEntity(moedaRepository.remove(id), HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
}

