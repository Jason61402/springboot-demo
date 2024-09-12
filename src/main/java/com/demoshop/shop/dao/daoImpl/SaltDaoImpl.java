package com.demoshop.shop.dao.daoImpl;

import com.demoshop.shop.dao.SaltDao;
import com.demoshop.shop.entity.Salt;
import com.demoshop.shop.rowMapper.SaltRowMapper;
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
import java.util.UUID;

@Component
public class SaltDaoImpl implements SaltDao {

    private static final Logger logger = LogManager.getLogger(SaltDaoImpl.class.getName());

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createSalt(Salt salt) {

        String sql = "INSERT INTO salt(salt, c_date, status) " +
                "VALUES (:salt, :cDate, :status)";

        //UUID uuid = UUID.randomUUID();
        Date now = new Date();
        String status = "0";

        HashMap<String, Object> map = new HashMap<>();
        map.put("salt",salt.getSalt());
        map.put("cDate",now);
        map.put("status",status);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int saltId = keyHolder.getKey().intValue();

        logger.info("產生的saltId:{} ", saltId);
        return saltId;
    }

    @Override
    public Integer createSalt2() {

        String sql = "INSERT INTO salt(salt, c_date, status) " +
                "VALUES (:salt, :cDate, :status)";

        String uuid = UUID.randomUUID().toString();
        Date now = new Date();
        String status = "0";

        HashMap<String, Object> map = new HashMap<>();
        map.put("salt",uuid);
        map.put("cDate",now);
        map.put("status",status);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int saltId = keyHolder.getKey().intValue();

        return saltId;

    }

    @Override
    public Salt getSaltById(Integer saltId) {
        String sql = "SELECT salt_id, salt, c_date, status " +
                " FROM salt WHERE salt_id = :saltId AND status != '99' ";

        HashMap<String, Object> map = new HashMap<>();
        map.put("saltId",saltId);

        List<Salt> saltList = namedParameterJdbcTemplate.query(sql, map, new SaltRowMapper());

        if(saltList.size() > 0){
            return saltList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public String getSaltHex(Integer saltId) {
        String sql = "SELECT salt+c_date FROM salt " +
                " where salt_id = :saltId AND NOT status = '99' ";

        HashMap<String, Object> map = new HashMap<>();
        map.put("saltId",saltId);

        try {
            String sqlSalt = namedParameterJdbcTemplate.queryForObject(sql, map, String.class);
            return sqlSalt;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
