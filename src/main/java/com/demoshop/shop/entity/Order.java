package com.demoshop.shop.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Order {

    private  Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private String status;
    private Date createdDate;
    private  Date lastModifiedDate;


}
