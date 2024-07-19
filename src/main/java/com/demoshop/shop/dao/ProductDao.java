package com.demoshop.shop.dao;

import com.demoshop.shop.dto.ProductRequest;
import com.demoshop.shop.entity.Product;

import java.util.List;

public interface ProductDao {

    Integer createProduct(ProductRequest productRequest);

    Product getProductById(Integer productId);

    void updateProductById(Integer productId,ProductRequest productRequest);

    List<Product> getProducts(Product product);

    void updateStock(Integer productId,Integer stock);
}
