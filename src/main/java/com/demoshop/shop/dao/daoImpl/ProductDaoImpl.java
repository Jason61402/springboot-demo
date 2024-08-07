package com.demoshop.shop.dao.daoImpl;

import com.demoshop.shop.dao.ProductDao;
import com.demoshop.shop.dto.ProductRequest;
import com.demoshop.shop.entity.Product;
import com.demoshop.shop.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date, status) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, " +
                ":description, :createdDate, :lastModifiedDate, :status)";

        HashMap<String, Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        map.put("status",productRequest.getStatus());

        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date , status " +
                "FROM product WHERE product_id = :productId";

        HashMap<String, Object> map = new HashMap<>();
        map.put("productId",productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size() > 0){
            return productList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public void updateProductById(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl," +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate," +
                "status = :status WHERE product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());
        map.put("status",productRequest.getStatus());

        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<Product> getProducts(Product product) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                " created_date, last_modified_date , status  FROM product WHERE status != '99' ";
        HashMap<String, Object> map = new HashMap<>();

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate" +
                " WHERE product_id = :productId ";

        HashMap<String, Object> map = new HashMap<>();
        map.put("productId",productId);
        map.put("stock",stock);
        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql,map);
    }


}
