/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.app;

import com.accela.jm.contact.domain.Person;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> getById(Long id);

    Person save(Person person);

    @Transactional
    Optional<Person> edit(Person provided);

    Optional<Person> remove(Long id);

    Long count();

    List<Person> findAll();
}
