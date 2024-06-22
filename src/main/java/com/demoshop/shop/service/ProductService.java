package com.demoshop.shop.service;

import com.demoshop.shop.dto.ProductRequest;
import com.demoshop.shop.entity.Product;

public interface ProductService {

    Integer createProduct(ProductRequest productRequest);

    Product getProductById(Integer productId);

    void updateProduct(Integer productId,ProductRequest productRequest);
}
