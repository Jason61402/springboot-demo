package com.demoshop.shop.dto;

import com.demoshop.shop.constant.ProductCategory;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotNull
    private String productName;

    @NotNull
    private ProductCategory category;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;

    private String description;

    @NotNull
    private Integer status;
}
