package com.demoshop.shop.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Salt {

    private Integer saltId;

    private String salt;

    private Date cDate;

    private String status;
}
