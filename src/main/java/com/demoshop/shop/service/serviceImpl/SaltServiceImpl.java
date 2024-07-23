package com.demoshop.shop.service.serviceImpl;

import com.demoshop.shop.dao.SaltDao;
import com.demoshop.shop.entity.Salt;
import com.demoshop.shop.service.SaltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaltServiceImpl implements SaltService {

    @Autowired
    private SaltDao saltDao;

    @Override
    public Integer createSalt() {
        return saltDao.createSalt();
    }

    @Override
    public Salt getSalt(String salt) {
        return saltDao.getSalt(salt);
    }
}
