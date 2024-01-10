package com.example.service;

import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    public Account login(Account admin){

        Account dbAdmin = adminMapper.selectByUsername(admin.getUsername());
        if(null == dbAdmin){
            throw  new CustomException("賬號或者密碼錯誤");
        }
        if (!admin.getPassword().equals(dbAdmin.getPassword())){
            throw new CustomException("密碼錯誤");
        }

        return dbAdmin;

    }


}
