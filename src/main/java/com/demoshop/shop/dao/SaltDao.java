package com.demoshop.shop.dao;

import com.demoshop.shop.entity.Salt;

public interface SaltDao {

    Integer createSalt(Salt salt);

    Integer createSalt2();

    Salt getSaltById(Integer saltId);

    String getSaltHex(Integer saltId);

}
