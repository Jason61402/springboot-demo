package com.demoshop.shop.service;

import com.demoshop.shop.dto.ProductRequest;
import com.demoshop.shop.entity.Product;

import java.util.List;

public interface ProductService {

    Integer createProduct(ProductRequest productRequest);

    Product getProductById(Integer productId);

    List<Product> getProducts(Product product);

    void updateProduct(Integer productId,ProductRequest productRequest);
}
