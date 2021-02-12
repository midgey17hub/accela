package com.accela.jm.contact.controller;

import com.accela.jm.contact.app.AddressService;
import com.accela.jm.contact.app.PersonService;
import com.accela.jm.contact.domain.Address;
import com.accela.jm.contact.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PersonControllerTest {

    @Mock
    PersonService personService;

    @Mock
    AddressService addressService;

    @InjectMocks
    PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static final Long TEST_ID = -1L;

    private static final Person TEST_PERSON = Person.builder()
            .id(TEST_ID)
            .firstName("Joe")
            .lastName("Bloggs")
            .addressList(Collections.emptyList())
            .build();

    private static final Address TEST_ADDRESS = Address.builder()
            .id(TEST_ID)
            .street("St")
            .country("Ireland")
            .person(TEST_PERSON)
            .build();

    @Test
    void noData_findPerson_throwResponseStatusEx() {
        assertThrows(ResponseStatusException.class, () -> personController.findPerson(TEST_ID));
    }

    @Test
    void exists_findPerson_returnPerson() {
        //when
        when(personService.getById(TEST_ID)).thenReturn(Optional.ofNullable(TEST_PERSON));
        final Person result = personController.findPerson(TEST_ID);
        //then
        assertThat(TEST_PERSON).isEqualTo(result);
    }

    @Test
    void noData_editPerson_throwResponseStatusEx() {
        assertThrows(ResponseStatusException.class, () -> personController.editPerson(TEST_PERSON));
    }

    @Test
    void exists_editPerson_returnUpdatedPerson() {
        //when
        when(personService.edit(any())).thenReturn(Optional.of(TEST_PERSON));
        final Person result = personController.editPerson(TEST_PERSON);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    void noData_removePerson_throwResponseStatusEx() {
        assertThrows(ResponseStatusException.class, () -> personController.removePerson(TEST_ID));
    }

    @Test
    void exists_removePerson_returnPerson() {
        //when
        when(personService.remove(TEST_ID)).thenReturn(Optional.of(TEST_PERSON));
        final Person result = personController.removePerson(TEST_ID);
        //then
        assertThat(TEST_PERSON).isEqualTo(result);
    }

    @Test
    void noData_findAddress_throwResponseStatusEx() {
        assertThrows(ResponseStatusException.class, () -> personController.findAddress(TEST_ID));
    }

    @Test
    void exists_findAddress_returnAddress() {
        //when
        when(addressService.getById(TEST_ID)).thenReturn(Optional.of(TEST_ADDRESS));
        final Address result = personController.findAddress(TEST_ID);
        //then
        assertThat(TEST_ADDRESS).isEqualTo(result);
    }

    @Test
    void noData_addAddress_throwResponseStatusEx() {
        assertThrows(ResponseStatusException.class, () -> personController.addAddress(TEST_ID, TEST_ADDRESS));
    }

    @Test
    void personExists_addAddress_returnResponse() {
        //when
        when(personService.getById(TEST_ID)).thenReturn(Optional.of(TEST_PERSON));
        final ResponseEntity<Person> result = personController.addAddress(TEST_ID, TEST_ADDRESS);
        //then
        assertThat(TEST_PERSON).isEqualTo(result.getBody());
    }

    @Test
    void noData_editAddress_throwResponseStatusEx() {
        assertThrows(ResponseStatusException.class, () -> personController.editAddress(TEST_ADDRESS));
    }

    @Test
    void exists_editAddress_returnAddress() {
        //when
        when(addressService.edit(any())).thenReturn(Optional.of(TEST_ADDRESS));
        final Address result = personController.editAddress(TEST_ADDRESS);
        //then
        assertThat(TEST_ADDRESS).isEqualTo(result);
    }

    @Test
    void noData_removeAddress_throwResponseStatusEx() {
        assertThrows(ResponseStatusException.class, () -> personController.removeAddress(TEST_ID));
    }

    @Test
    void exists_removeAddress_returnAddress() {
        //when
        when(addressService.remove(TEST_ID)).thenReturn(Optional.of(TEST_ADDRESS));
        final Address result = personController.removeAddress(TEST_ID);
        //then
        assertThat(TEST_ADDRESS).isEqualTo(result);
    }
}