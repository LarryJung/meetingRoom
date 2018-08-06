package com.larry.meetingroomreservation.security.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FormLoginDto {

    @JsonProperty(value = "userId")
    private String userId;

    @JsonProperty(value = "password")
    private String password;

}
