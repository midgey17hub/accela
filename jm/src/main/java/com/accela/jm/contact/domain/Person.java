/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity(name = "PERSON")
public final class Person {

    public static final String ALPHABETICAL = "^[A-Za-z]*$";

    @Id
    @SequenceGenerator(name = "PERSON_GENERATOR", sequenceName = "SEQ_PERSON", allocationSize = 1)
    @GeneratedValue(generator = "PERSON_GENERATOR")
    @NumberFormat
    @Column(name = "ID")
    private Long id;


    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = ALPHABETICAL, message = "First Name should be Alphabetical")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = ALPHABETICAL, message = "Last Name should be Alphabetical")
    @Column(name = "LAST_NAME")
    private String lastName;

    @JsonManagedReference
    @OneToMany(mappedBy="person", cascade = CascadeType.ALL)
    private List<Address> addressList;

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public void setAttributes(Person providedPerson) {
        setFirstName(providedPerson.getFirstName());
        setLastName(providedPerson.getLastName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id.equals(person.id) && firstName.equals(person.firstName) && lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private List<Address> addressList;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder addressList(List<Address> addressList) {
            this.addressList = addressList;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.setId(id);
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setAddressList(addressList);
            return person;
        }
    }
}
