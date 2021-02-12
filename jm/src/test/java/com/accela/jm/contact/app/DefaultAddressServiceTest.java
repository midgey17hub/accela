package com.accela.jm.contact.app;

import com.accela.jm.contact.db.AddressRepository;
import com.accela.jm.contact.db.PersonRepository;
import com.accela.jm.contact.domain.Address;
import com.accela.jm.contact.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DefaultAddressServiceTest {

    private static final long TEST_ID = 1L;
    private static Address TEST_ADDRESS;

    @Mock
    AddressRepository addressRepository;

    @InjectMocks
    DefaultAddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TEST_ADDRESS = Address.builder()
                .id(TEST_ID).street("St").country("IE")
                .build();
    }

    @Test
    void noData_getById_returnEmpty() {
        Optional<Address> result = addressService.getById(1L);
        assertThat(result).isEmpty();
    }

    @Test
    void personExists_getById_returnPerson() {
        //when
        when(addressRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_ADDRESS));
        Optional<Address> result = addressService.getById(TEST_ID);
        //then
        assertThat(result).isNotEmpty();
        assertThat(TEST_ADDRESS).isEqualTo(result.get());
    }

    @Test
    void save_invokesRepository() {
        //given
        Address address = Address.builder().street("rd").build();
        //when
        addressService.save(address);
        //then
        verify(addressRepository).save(address);
        verify(addressRepository, times(1)).save(any());
    }

    @Test
    void noDataById_remove_doesNothing() {
        //when
        final Optional<Address> result = addressService.remove(TEST_ID);
        //then
        assertThat(result).isEmpty();
        verify(addressRepository, times(0)).delete(any());
    }

    @Test
    void dataExists_remove_invokesRepository() {
        //when
        when(addressRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_ADDRESS));
        final Optional<Address> result = addressService.remove(TEST_ID);
        //then
        assertThat(TEST_ADDRESS).isEqualTo(result.get());
        verify(addressRepository).delete(TEST_ADDRESS);
        verify(addressRepository, times(1)).delete(any());
    }

    @Test
    public void noDataById_edit_returnEmpty() {
        //when
        final Optional<Address> result = addressService.edit(TEST_ADDRESS);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void dataExists_edit_retainPerson() {
        final Person givenPerson = Person.builder()
                .id(TEST_ID)
                .firstName("Joe")
                .lastName("Bloggs")
                .build();
        final Address givenAddress = Address.builder().person(givenPerson).build();
        //when
        when(addressRepository.findById(TEST_ID)).thenReturn(Optional.of(givenAddress));
        addressService.edit(TEST_ADDRESS);
        //then
        Address expected = TEST_ADDRESS;
        expected.setPerson(givenPerson);
        verify(addressRepository).save(expected);
    }
}