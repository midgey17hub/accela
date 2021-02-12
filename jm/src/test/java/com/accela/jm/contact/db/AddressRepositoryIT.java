package com.accela.jm.contact.db;

import com.accela.jm.configuration.PersistenceConfiguration;
import com.accela.jm.contact.domain.Address;
import com.accela.jm.contact.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PersistenceConfiguration.class
})
@Transactional
@TestPropertySource(locations = { "classpath:connection.properties" })
public class AddressRepositoryIT {

    @Autowired
    AddressRepository repository;

    @Test
    public void noData_findById_returnEmpty() {
        final Optional<Address> result = repository.findById(-1L);
        assertThat(result).isEmpty();
    }

    @Test
    @Sql(value = {"/db/person/one-person.sql", "/db/address/one-address.sql"})
    public void oneExisting_findById_returnAddress() {
        //given
        final Person expectedPerson = Person.builder()
                .id(-1L)
                .firstName("Joe")
                .lastName("Bloggs")
                .build();
        final Address expected = Address.builder()
                .id(-1L)
                .street("123 Main St")
                .country("Ireland")
                .person(expectedPerson)
                .build();
        //when
        final Optional<Address> result = repository.findById(-1L);
        //then
        assertThat(result).isNotEmpty();
        assertThat(expected).isEqualTo(result.get());
    }

    @Test
    @Sql(value = {"/db/person/one-person.sql", "/db/address/one-address.sql"})
    public void oneExisting_findByWrongId_returnEmpty() {
        //when
        final Optional<Address> result = repository.findById(-123L);
        //then
        assertThat(result).isEmpty();
    }

}