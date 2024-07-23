package com.demoshop.shop.dao.daoImpl;

import com.demoshop.shop.dao.SaltDao;
import com.demoshop.shop.entity.Salt;
import com.demoshop.shop.rowMapper.SaltRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class SaltDaoImpl implements SaltDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createSalt() {

        String sql = "INSERT INTO salt(salt, c_date, status) " +
                "VALUES (:salt, :cDate, :status)";

        UUID uuid = UUID.randomUUID();
        Date now = new Date();
        String status = "0";

        HashMap<String, Object> map = new HashMap<>();
        map.put("salt",uuid.toString());
        map.put("cDate",now);
        map.put("status",status);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int saltId = keyHolder.getKey().intValue();

        return saltId;
    }

    @Override
    public Salt getSalt(String salt) {
        String sql = "SELECT salt_id, salt, c_date, status " +
                "FROM salt WHERE salt = :salt AND status != '99' ";

        HashMap<String, Object> map = new HashMap<>();
        map.put("salt",salt);

        List<Salt> saltList = namedParameterJdbcTemplate.query(sql, map, new SaltRowMapper());

        if(saltList.size() > 0){
            return saltList.get(0);
        }else {
            return null;
        }
    }
}
