package com.carrental.carrental.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode //to compare easily
@ToString
public class LoginRequest {
    private final String email;
    private final String password;

}
