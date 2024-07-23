package com.demoshop.shop.service;

import com.demoshop.shop.entity.Salt;

public interface SaltService {

    Integer createSalt();

    Salt getSalt(String salt);
}
