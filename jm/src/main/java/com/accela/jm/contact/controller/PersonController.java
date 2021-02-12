/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.controller;

import com.accela.jm.contact.app.AddressService;
import com.accela.jm.contact.app.PersonService;
import com.accela.jm.contact.domain.Address;
import com.accela.jm.contact.domain.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
public class PersonController {

    private static final String PERSON_PATH = "/person";
    private static final String ADDRESS_PATH = PERSON_PATH + "/address";

    private final PersonService personService;
    private final AddressService addressService;

    @Autowired
    public PersonController(PersonService personService, AddressService addressService) {
        this.personService = personService;
        this.addressService = addressService;
    }

    @GetMapping(PERSON_PATH + "/{id}")
    @ResponseBody
    public Person findPerson(@PathVariable Long id) {
        log.info("Fetching person {}", id);
        Optional<Person> person = personService.getById(id);
        return extractOrThrow(person, "Person not found " + id);
    }

    @PutMapping(PERSON_PATH + "/add")
    @ResponseBody
    public Person addPerson(@Valid @RequestBody Person person) {
        log.info("Adding new person {}", person);
        return personService.save(person);
    }

    @PostMapping(PERSON_PATH + "/edit")
    @ResponseBody
    public Person editPerson(@Valid @RequestBody Person provided) {
        log.info("Edit person {}", provided);
        final Optional<Person> updatedPerson = personService.edit(provided);
        final Person person = extractOrThrow(updatedPerson, "Person not found " + provided.getId());
        log.info("Person {} name changed from [{} {}] to: [{} {}]",
                provided.getId(), person.getFirstName(), person.getLastName(), provided.getFirstName(), provided.getLastName());
        return person;
    }

    @DeleteMapping(PERSON_PATH + "/remove/{id}")
    @ResponseBody
    public Person removePerson(@PathVariable Long id) {
        log.info("Removing person {}", id);
        final Optional<Person> person = personService.remove(id);
        return extractOrThrow(person, "Person not found " + id);
    }

    @GetMapping(PERSON_PATH + "/count")
    @ResponseBody
    public Long countPersons() {
        final Long count = personService.count();
        log.info("Counted person records {}", count);
        return count;
    }

    @GetMapping(PERSON_PATH + "/list")
    @ResponseBody
    public List<Person> listPersons() {
        log.info("Listing person records");
        return personService.findAll();
    }

    @GetMapping(ADDRESS_PATH + "/{id}")
    @ResponseBody
    public Address findAddress(@PathVariable Long id) {
        log.info("Fetching address {}", id);
        Optional<Address> address = addressService.getById(id);
        return extractOrThrow(address, "Address not found " + id);
    }

    @PutMapping(ADDRESS_PATH + "/add/{id}")
    public ResponseEntity<Person> addAddress(@PathVariable Long id, @Valid @RequestBody Address address) {
        log.info("Person id {}, Adding new {}", address);
        final Optional<Person> person = personService.getById(id);
        final Person existingPerson = extractOrThrow(person, "Person not found " + id);
        address.setPerson(existingPerson);
        addressService.save(address);
        return ResponseEntity.ok(existingPerson);
    }

    @PostMapping(ADDRESS_PATH + "/edit")
    @ResponseBody
    public Address editAddress(@Valid @RequestBody Address providedAddress) {
        log.info("Edit {}", providedAddress);
        Optional<Address> address = addressService.edit(providedAddress);
        return extractOrThrow(address, "Address not found " + providedAddress.getId());
    }

    @DeleteMapping(ADDRESS_PATH + "/remove/{id}")
    @ResponseBody
    public Address removeAddress(@PathVariable Long id) {
        log.info("Removing address {}", id);
        final Optional<Address> address = addressService.remove(id);
        return extractOrThrow(address, "Address not found " + id);
    }

    private <T> T extractOrThrow(Optional<T> optionalT, String errorMessage) {
        return optionalT.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage)
        );
    }
}
