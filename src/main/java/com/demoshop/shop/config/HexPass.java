package com.demoshop.shop.config;
import com.demoshop.shop.dao.SaltDao;
import com.demoshop.shop.entity.Salt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class HexPass {

    private static final Logger logger = LogManager.getLogger(HexPass.class.getName());

    @Autowired
    private  DataSource dataSource;

    @Autowired
    private SaltDao saltDao;

    public  String encryptHex(String data, Integer saltId , int iterationCount, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {

        String saltSql = saltDao.getSaltHex(saltId);

        //String saltSql = selectString(sql);

        logger.info("saltSql:{}  ",saltSql);
        return toHex(encryptByte(data, saltSql, iterationCount, keyLength));
    }

    private  String selectString(String sql, String... para) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return selectStringAct(conn, sql, para);
        }
    }


    private  String toHex(byte[] hash) {
        BigInteger bi = new BigInteger(1, hash);
        String hex = bi.toString(16);
        int paddingLength = (hash.length * 2) - hex.length();
        if(paddingLength > 0){
            return String.format("%0"  + paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }


    private  byte[] encryptByte(String data, String salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), iterationCount, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        return skf.generateSecret(spec).getEncoded();
    }

    private  String selectStringAct(Connection conn, String sql, String... para)
    {
        String result  = "" ;
        PreparedStatement ps = null ;
        ResultSet rs = null ;


        String paraString = "";
        for(String tmp :para)
            paraString+=tmp+",";
            logger.info("[String]["+sql+"]"+paraString);


        try {
            ps = conn.prepareStatement(sql);

            for(int i=0;i<para.length;i++)
                ps.setString(i+1, para[i]);


            rs = ps.executeQuery();
            if(rs.next())
            {
                result = rs.getString(1);
            }
            rs.close();
            ps.close();
            logger.info("[String]["+sql+"][success]");
        } catch (Exception e) {
            logger.info("[String]["+sql+"][error]"+e);
        }

        return result ;
    }
}
