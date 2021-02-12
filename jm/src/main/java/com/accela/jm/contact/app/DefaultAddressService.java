/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.app;

import com.accela.jm.contact.db.AddressRepository;
import com.accela.jm.contact.domain.Address;
import com.accela.jm.contact.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DefaultAddressService implements AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public DefaultAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Optional<Address> getById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> remove(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            addressRepository.delete(address.get());
        }
        return address;
    }

    @Override
    public Optional<Address> edit(Address address) {
        final Optional<Address> existing = getById(address.getId());
        if (existing.isPresent()) {
            address.setPerson(existing.get().getPerson());
            return Optional.ofNullable(save(address));
        }
        return Optional.empty();
    }
}
