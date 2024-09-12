package com.demoshop.shop.dao.daoImpl;

import com.demoshop.shop.dao.MemberDao;
import com.demoshop.shop.dto.MemberRegister;
import com.demoshop.shop.entity.Member;
import com.demoshop.shop.rowMapper.MemberRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class MemberDaoImpl implements MemberDao {

    private static final Logger logger = LogManager.getLogger(MemberDaoImpl.class.getName());

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createMember(MemberRegister member) {
        String sql = "INSERT INTO member (email, password, user_name, phone, status, c_date, u_date)" +
                " VALUES (:email, :password, :userName, :phone, :status, :cDate, :uDate) ";

        Date now = new Date();
        String status = "00";

        HashMap<String, Object> map = new HashMap<>();
        map.put("email",member.getEmail());
        map.put("password",member.getPassword());
        map.put("userName",member.getUserName());
        map.put("phone",member.getPhone());
        map.put("status",status);
        map.put("cDate",now);
        map.put("uDate",now);

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), holder);
        int memberId = holder.getKey().intValue();

        return memberId;
    }

    @Override
    public Member getMemberByEmail(String email) {
        String sql = "SELECT member_id, email, user_name, phone, status, password, c_date, u_date " +
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

    @Override
    public Member getMemberById(Integer memberId) {
        String sql = "SELECT member_id, email, user_name, phone, status, password, user_name, c_date, u_date " +
                "AND status != 99 " +
                "FROM member WHERE member_id = :memberId" ;
        HashMap<String, Object> map = new HashMap<>();
        map.put("memberId",memberId);

        List<Member> member = namedParameterJdbcTemplate.query(sql, map, new MemberRowMapper());

        if(member.size() > 0){
            return member.get(0);
        }else {
            return null;
        }
    }
}
