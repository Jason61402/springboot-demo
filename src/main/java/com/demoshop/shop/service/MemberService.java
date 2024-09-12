package com.demoshop.shop.service;

import com.demoshop.shop.dto.MemberLogin;
import com.demoshop.shop.dto.MemberRegister;
import com.demoshop.shop.entity.Member;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public interface MemberService {

    Member getMemberById(Integer memberId);

    Integer register(MemberRegister memberRegister) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException;

    Member login(MemberLogin memberLogin);
}
