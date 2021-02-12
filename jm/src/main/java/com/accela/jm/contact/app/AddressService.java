/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.app;

import com.accela.jm.contact.domain.Address;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AddressService {
    @Transactional
    Optional<Address> getById(Long id);

    @Transactional
    Address save(Address address);

    Optional<Address> remove(Long id);

    Optional<Address> edit(Address address);
}
