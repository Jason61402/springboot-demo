package com.demoshop.shop.controller;

import com.demoshop.shop.config.ApiResponse;
import com.demoshop.shop.config.LineNotifyService;
import com.demoshop.shop.dto.ProductRequest;
import com.demoshop.shop.entity.Product;
import com.demoshop.shop.service.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private  LineNotifyService lineNotifyService;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/product")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody @Validated ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);
        if(product != null){
            ApiResponse<Product> response = new ApiResponse<>("成功",product);
            log.info("創建成功，商品ID:{}",productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        ApiResponse<Product> response = new ApiResponse<>("失敗",null);
        log.warn("創建失敗");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @GetMapping("/product/{productId}")
    public  ResponseEntity<ApiResponse<Product>> getProduct(@PathVariable Integer productId) throws Exception {
        Product product = productService.getProductById(productId);

        if(product != null){
            ApiResponse<Product> response = new ApiResponse<>("成功",product);
            log.info("查詢到商品：{}",product);
            lineNotifyService.sendNotification("查詢到商品： " + product.getProductName());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            ApiResponse<Product> response = new ApiResponse<>("失敗",null);
            log.info("未查詢到對應商品");
            lineNotifyService.sendNotification("未查詢到對應商品");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/product/products")
    public ResponseEntity<ApiResponse<List<Product>>>getProducts(Product product){
        List<Product> productList = productService.getProducts(product);
        ApiResponse<List<Product>> response;

        if(productList != null){
            response = new ApiResponse<>("成功", productList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            response = new ApiResponse<>("未找到产品", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/product/objectProducts")
    public ResponseEntity<String> getProductsObject(Product product){
        List<Product> productList = productService.getProducts(product);

        JSONObject apiResponse = new JSONObject(); // 外層object
        if(productList != null && !productList.isEmpty()){
            JSONArray jsonArray = new JSONArray();
            for (Product res : productList){
                JSONObject result = new JSONObject();
                result.put("productId",res.getProductId());
                result.put("productName",res.getProductName());
                result.put("category",res.getCategory());
                result.put("imageUrl",res.getImageUrl());
                result.put("price",res.getPrice());
                result.put("stock",res.getStock());
                result.put("description",res.getDescription());
                result.put("createdDate",res.getCreatedDate());
                result.put("lastModifiedDate",res.getLastModifiedDate());
                jsonArray.put(result);

            }
            apiResponse.put("msg","M000");
            apiResponse.put("data",jsonArray);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse.toString());
        }
        apiResponse.put("msg","M999");
        apiResponse.put("data",new JSONArray());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse.toString());

    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Validated ProductRequest productRequest){
        Product product = productService.getProductById(productId);

        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId, productRequest);
        Product updateProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

}
