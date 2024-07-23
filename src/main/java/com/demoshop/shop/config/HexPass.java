package com.demoshop.shop.config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
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

@Component
public class HexPass {

    private static final Logger logger = LogManager.getLogger(HexPass.class.getName());

    @Autowired
    private static DataSource dataSource;

    public static String encryptHex(String data, int iterationCount, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {


        String sql ="SELECT salt+c_date FROM salt where salt = ? AND NOT STATUS = '99' ";
        String saltSql = selectString(sql);

        logger.info("saltSql:{}  ",saltSql);
        return toHex(encryptByte(data, saltSql, iterationCount, keyLength));
    }


    private static String selectString(String sql, String... para) throws SQLException {
        Connection conn = dataSource.getConnection();
        return selectStringAct(conn,sql,para);
    }


    private static String toHex(byte[] hash) {
        BigInteger bi = new BigInteger(1, hash);
        String hex = bi.toString(16);
        int paddingLength = (hash.length * 2) - hex.length();
        if(paddingLength > 0){
            return String.format("%0"  + paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }


    private static byte[] encryptByte(String data, String salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), iterationCount, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        return skf.generateSecret(spec).getEncoded();
    }

    private static String selectStringAct(Connection conn, String sql, String... para)
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

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
