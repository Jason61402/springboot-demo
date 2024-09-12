package com.demoshop.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Member {

    private Integer memberId;

    private String userName;

    private String phone;

    @JsonIgnore
    private String password;

    private String email;

    private String status;

    private Date cDate;

    private Date uDate;

}
