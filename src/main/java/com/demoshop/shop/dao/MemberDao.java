package com.demoshop.shop.dao;

import com.demoshop.shop.entity.Member;

public interface MemberDao {

    Member getMemberByEmail(String email);
}
