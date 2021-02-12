/*
 **************************************************
 ** Copyright (c) CRIF S.p.A - All Right Reserved *
 **************************************************
 */
package com.accela.jm.contact.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity(name = "ADDRESS")
public class Address {

    @Id
    @SequenceGenerator(name = "ADDRESS_GENERATOR", sequenceName = "SEQ_ADDRESS", allocationSize = 1)
    @GeneratedValue(generator = "ADDRESS_GENERATOR")
    @NumberFormat
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "COUNTRY")
    private String country; //could be an IsoAlpha Enum

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="PERSON_ID", nullable=false)
    private Person person;

    public Person getPerson() {
        return person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return id.equals(address.id) && street.equals(address.street) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(
                postalCode,
                address.postalCode) && country.equals(address.country) && Objects.equals(person, address.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, state, postalCode, country, person);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", person='" + person + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String street;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private Person person;

        private Builder() {
        }


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder person(Person person) {
            this.person = person;
            return this;
        }

        public Address build() {
            Address address = new Address();
            address.setId(id);
            address.setStreet(street);
            address.setCity(city);
            address.setState(state);
            address.setPostalCode(postalCode);
            address.setCountry(country);
            address.setPerson(person);
            return address;
        }
    }
}
