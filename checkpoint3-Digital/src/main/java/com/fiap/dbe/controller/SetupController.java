package com.fiap.dbe.controller;

import com.fiap.dbe.model.Setup;
import com.fiap.dbe.repository.SetupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping({"/setups"})
public class SetupController {

    private SetupRepository repository;

    SetupController(SetupRepository setupRepository) {
        this.repository = setupRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Setup create(@RequestBody Setup setup) {
        return repository.save(setup);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Setup setup) {
        return repository.findById(id)
                .map(record -> {
                    record.setName(setup.getName());
                    record.setEmail(setup.getEmail());
                    record.setPhone(setup.getPhone());
                    Setup updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
