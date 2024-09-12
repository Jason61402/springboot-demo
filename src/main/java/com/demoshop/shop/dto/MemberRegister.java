package com.demoshop.shop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class MemberRegister {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String userName;

    private String phone;

}
