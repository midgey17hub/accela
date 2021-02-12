/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.db;

import com.accela.jm.contact.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
