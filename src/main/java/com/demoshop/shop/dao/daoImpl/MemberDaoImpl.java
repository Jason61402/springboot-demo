package com.demoshop.shop.dao.daoImpl;

import com.demoshop.shop.dao.MemberDao;
import com.demoshop.shop.entity.Member;
import com.demoshop.shop.rowMapper.MemberRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class MemberDaoImpl implements MemberDao {

    private static final Logger logger = LogManager.getLogger(MemberDaoImpl.class.getName());

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Member getMemberByEmail(String email) {
        String sql = "SELECT member_id, email, password, user_name, " +
                "AND status != 99 " +
                "FROM member WHERE email = :email";

        HashMap<String, Object> map = new HashMap<>();
        map.put("email",email);

        List<Member> member = namedParameterJdbcTemplate.query(sql, map, new MemberRowMapper());

        logger.info("\n\t[SQL]:{}",sql);

        if(member.size() > 0){
            return member.get(0);
        }else {
            return null;
        }
    }
}
