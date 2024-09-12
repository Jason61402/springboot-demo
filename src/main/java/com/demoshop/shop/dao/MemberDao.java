package com.demoshop.shop.dao;

import com.demoshop.shop.dto.MemberRegister;
import com.demoshop.shop.entity.Member;

public interface MemberDao {

    Member getMemberByEmail(String email);

    Member getMemberById(Integer memberId);

    Integer createMember(MemberRegister member);
}
