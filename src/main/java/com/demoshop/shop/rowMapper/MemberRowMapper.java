package com.demoshop.shop.rowMapper;

import com.demoshop.shop.entity.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet resultSet, int i) throws SQLException {
        Member member = new Member();
        member.setMemberId(resultSet.getString("member_id"));
        member.setUserName(resultSet.getString("user_name"));
        member.setEmail(resultSet.getString("email"));
        member.setPhone(resultSet.getString("phone"));
        member.setPassWord(resultSet.getString("password"));

        return member;
    }
}
