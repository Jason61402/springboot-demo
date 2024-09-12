package com.demoshop.shop.controller;

import com.demoshop.shop.dto.MemberRegister;
import com.demoshop.shop.entity.Member;
import com.demoshop.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/member/register")
    public ResponseEntity<Member> register(@RequestBody @Valid MemberRegister memberRegister)
            throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Integer memberId = memberService.register(memberRegister);

        Member member = memberService.getMemberById(memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);

    }

}
