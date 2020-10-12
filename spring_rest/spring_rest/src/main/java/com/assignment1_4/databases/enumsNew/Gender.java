package com.assignment1_4.databases.enumsNew;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {
    @JsonProperty("Man")
    MAN,
    @JsonProperty("Woman")
    WOMAN,
    @JsonProperty("Gender queer")
    GENDER_QUEER,
    @JsonProperty("Non binary")
    NON_BINARY,
    @JsonProperty("Prefer not to disclose")
    PREFER_NOT_TO_DISCLOSE;
}