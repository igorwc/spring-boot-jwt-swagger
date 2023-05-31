package com.cavalcanti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cavalcanti.domain.Person;
import com.cavalcanti.repository.PersonRepository;

import jakarta.persistence.NoResultException;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
	this.repository = repository;
    }

    @PostMapping
    public Person persist(@RequestParam("name") String name) {
	return repository.save(new Person(name));
    }

    @PutMapping("{id}")
    public Person update(@PathVariable("id") Long id, @RequestParam("name") String name) {
	var person = repository.findById(id)
		.map(it -> it.setName(name))
		.orElseThrow(NoResultException::new);
	return repository.save(person);
    }

    @GetMapping
    public List<Person> get() {
	return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Person> get(@PathVariable("id") Long id) {
	return repository.findById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
	repository.deleteById(id);
    }

}