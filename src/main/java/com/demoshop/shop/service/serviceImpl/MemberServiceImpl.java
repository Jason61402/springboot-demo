package com.demoshop.shop.service.serviceImpl;

import com.demoshop.shop.config.HexPass;
import com.demoshop.shop.dao.MemberDao;
import com.demoshop.shop.dao.SaltDao;
import com.demoshop.shop.dto.MemberLogin;
import com.demoshop.shop.dto.MemberRegister;
import com.demoshop.shop.entity.Member;
import com.demoshop.shop.service.SaltService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demoshop.shop.service.MemberService;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger logger = LogManager.getLogger(MemberServiceImpl.class.getName());

    @Autowired
    private MemberDao memberDao;
    
    @Autowired
    private SaltService saltService;

    @Override
    public Integer register(MemberRegister memberRegister)
            throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Member member = memberDao.getMemberByEmail(memberRegister.getEmail());

        if(member != null){
            logger.warn("該email已有註冊：{}", memberRegister.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        saltService.createSalt();
        String hashPass = HexPass.encryptHex(memberRegister.getPassword(), 1024, 512);
        memberRegister.setPassword(hashPass);

        return null;
    }

    @Override
    public Member login(MemberLogin memberLogin) {
        return null;
    }
}
