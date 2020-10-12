package com.assignment1_3.spring_rest.presentationLayer.models;

import com.assignment1_3.spring_rest.dataLayer.models.Address;
import com.assignment1_3.spring_rest.enums.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountHolderRequestModel {

    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    @NotNull
    private Gender gender;
    @NotNull
    private Address address;

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
