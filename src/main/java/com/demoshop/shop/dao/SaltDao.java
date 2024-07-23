package com.demoshop.shop.dao;

import com.demoshop.shop.entity.Salt;

public interface SaltDao {

    Integer createSalt();

    Salt getSalt(String salt);

}
