/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.db;

import com.accela.jm.contact.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
