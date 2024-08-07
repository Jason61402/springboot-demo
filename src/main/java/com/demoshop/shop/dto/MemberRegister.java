package com.demoshop.shop.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MemberRegister {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
