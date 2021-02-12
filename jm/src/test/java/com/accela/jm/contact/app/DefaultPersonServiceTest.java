package com.accela.jm.contact.app;

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

class DefaultPersonServiceTest {

    private static final long TEST_ID = 1L;
    private static Person TEST_PERSON;

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    DefaultPersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TEST_PERSON = Person.builder()
                .id(TEST_ID).firstName("fn").lastName("ln")
                .build();
    }

    @Test
    void noData_getById_returnEmpty() {
        Optional<Person> result = personService.getById(1L);
        assertThat(result).isEmpty();
    }

    @Test
    void personExists_getById_returnPerson() {
        //when
        when(personRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_PERSON));
        Optional<Person> result = personService.getById(TEST_ID);
        //then
        assertThat(result).isNotEmpty();
        assertThat(TEST_PERSON).isEqualTo(result.get());
    }

    @Test
    void save_invokesRepository() {
        //given
        Person person = Person.builder().firstName("joe").build();
        //when
        personService.save(person);
        //then
        verify(personRepository).save(person);
        verify(personRepository, times(1)).save(any());
    }

    @Test
    void noDataById_remove_doesNothing() {
        //when
        final Optional<Person> result = personService.remove(TEST_ID);
        //then
        assertThat(result).isEmpty();
        verify(personRepository, times(0)).delete(any());
    }

    @Test
    void dataExists_remove_invokesRepository() {
        //when
        when(personRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_PERSON));
        final Optional<Person> result = personService.remove(TEST_ID);
        //then
        assertThat(TEST_PERSON).isEqualTo(result.get());
        verify(personRepository).delete(TEST_PERSON);
        verify(personRepository, times(1)).delete(any());
    }

    @Test
    public void noDataById_edit_returnEmpty() {
        //when
        final Optional<Person> result = personService.edit(TEST_PERSON);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void dataExists_edit_retainAddress() {
        final List<Address> givenAddresses = Collections.singletonList(
                Address.builder().id(TEST_ID).street("St").country("IE").build());
        TEST_PERSON.setAddressList(givenAddresses);
        final Person givenPerson = Person.builder()
                .id(TEST_ID)
                .firstName("Sir")
                .lastName("Newgent")
                .build();
        //when
        when(personRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_PERSON));
        final Optional<Person> result = personService.edit(givenPerson);
        //then
        assertThat(givenPerson).isEqualTo(result.get());
        assertThat(givenAddresses).isEqualTo(result.get().getAddressList());
    }

    @Test
    public void noData_count_returnZero() {
        //when
        final Long result = personService.count();
        //then
        assertThat(result).isZero();
    }

    @Test
    public void exists_count_returnResult() {
        //given
        final long givenCount = 5L;
        //when
        when(personRepository.count()).thenReturn(givenCount);
        final Long result = personService.count();
        //then
        assertThat(result).isEqualTo(givenCount);
    }
}