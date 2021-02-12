/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.app;

import com.accela.jm.contact.db.PersonRepository;
import com.accela.jm.contact.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DefaultPersonService implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public DefaultPersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public Optional<Person> getById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public Optional<Person> edit(Person provided) {
        Optional<Person> existing = getById(provided.getId());
        if (existing.isPresent()) {
            Person person = existing.get();
            person.setAttributes(provided);
            return Optional.of(person);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Person> remove(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            personRepository.delete(person.get());
        }
        return person;
    }

    @Override
    public Long count() {
        return personRepository.count();
    }

    @Override
    public List<Person> findAll() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
