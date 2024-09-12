package com.demoshop.shop.rowMapper;

import com.demoshop.shop.entity.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet resultSet, int i) throws SQLException {
        Member member = new Member();
        member.setMemberId(resultSet.getInt("member_id"));
        member.setUserName(resultSet.getString("user_name"));
        member.setEmail(resultSet.getString("email"));
        member.setPhone(resultSet.getString("phone"));
        member.setStatus(resultSet.getString("status"));
        member.setPassword(resultSet.getString("password"));
        member.setCDate(resultSet.getTimestamp("c_date"));
        member.setUDate(resultSet.getTimestamp("u_date"));

        return member;
    }
}
