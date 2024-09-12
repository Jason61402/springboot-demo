package com.demoshop.shop.service.serviceImpl;

import com.demoshop.shop.config.HexPass;
import com.demoshop.shop.dao.MemberDao;
import com.demoshop.shop.dao.SaltDao;
import com.demoshop.shop.dto.MemberLogin;
import com.demoshop.shop.dto.MemberRegister;
import com.demoshop.shop.entity.Member;
import com.demoshop.shop.entity.Salt;
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
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger logger = LogManager.getLogger(MemberServiceImpl.class.getName());

    @Autowired
    private HexPass hexPass;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private SaltDao saltDao;

    @Override
    public Member getMemberById(Integer memberId) {
        return memberDao.getMemberById(memberId);
    }

    @Override
    public Integer register(MemberRegister memberRegister)
            throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Member member = memberDao.getMemberByEmail(memberRegister.getEmail());

        if(member != null){
            logger.warn("該email已有註冊：{}", memberRegister.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Integer newSalt = saltDao.createSalt2(); // 產生 salt
        Salt salt = saltDao.getSaltById(newSalt);
        Integer saltId = salt.getSaltId();
        logger.info("saltId:{}", saltId);

        //HexPass hex = new HexPass();
        String hashPass = hexPass.encryptHex(memberRegister.getPassword(), saltId, 1024, 512);
        memberRegister.setPassword(hashPass);

        return memberDao.createMember(memberRegister);
    }

    @Override
    public Member login(MemberLogin memberLogin) {
        return null;
    }

}
