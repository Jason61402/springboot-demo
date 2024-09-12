package com.demoshop.shop.rowMapper;

import com.demoshop.shop.entity.Salt;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SaltRowMapper implements RowMapper<Salt> {

    @Override
    public Salt mapRow(ResultSet resultSet, int i) throws SQLException {
        Salt salt = new Salt();
        salt.setSaltId(resultSet.getInt("salt_id"));
        salt.setSalt(resultSet.getString("salt"));
        salt.setCDate(resultSet.getString("c_date"));
        salt.setStatus(resultSet.getString("status"));
        //salt.setSaltHex(resultSet.getString("salt_hex"));

        return salt;
    }
}
