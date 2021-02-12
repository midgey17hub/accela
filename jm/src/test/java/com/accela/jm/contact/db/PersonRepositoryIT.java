package com.accela.jm.contact.db;

import com.accela.jm.configuration.PersistenceConfiguration;
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
public class PersonRepositoryIT {

    @Autowired
    PersonRepository repository;

    @Test
    public void noData_findById_returnEmpty() {
        final Optional<Person> result = repository.findById(-1L);
        assertThat(result).isEmpty();
    }

    @Test
    @Sql(value = "/db/person/one-person.sql")
    public void oneExisting_findById_returnPerson() {
        //given
        final Person expected = Person.builder()
                .id(-1L)
                .firstName("Joe")
                .lastName("Bloggs")
                .build();
        //when
        final Optional<Person> result = repository.findById(-1L);
        //then
        assertThat(result).isNotEmpty();
        assertThat(expected).isEqualTo(result.get());
    }

    @Test
    @Sql(value = "/db/person/one-person.sql")
    public void oneExisting_findByWrongId_returnEmpty() {
        final Optional<Person> result = repository.findById(-123L);
        assertThat(result).isEmpty();
    }

    @Test
    @Sql(value = "/db/person/four-person.sql")
    public void fourExisting_count_returnFour() {
        final long result = repository.count();
        assertThat(result).isEqualTo(4L);
    }

}