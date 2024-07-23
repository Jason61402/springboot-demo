package com.demoshop.shop.entity;

import lombok.Data;

@Data
public class Member {

    private String memberId;

    private String userName;

    private String phone;

    private String passWord;

    private String email;
}
