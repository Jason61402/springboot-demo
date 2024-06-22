package com.demoshop.shop.dao;

import com.demoshop.shop.dto.ProductRequest;
import com.demoshop.shop.entity.Product;

public interface ProductDao {

    Integer createProduct(ProductRequest productRequest);

    Product getProductById(Integer productId);
}
