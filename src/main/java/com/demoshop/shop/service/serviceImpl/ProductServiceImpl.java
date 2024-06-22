package com.demoshop.shop.service.serviceImpl;

import com.demoshop.shop.dao.ProductDao;
import com.demoshop.shop.dto.ProductRequest;
import com.demoshop.shop.entity.Product;
import com.demoshop.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
