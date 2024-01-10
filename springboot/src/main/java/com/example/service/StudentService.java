package com.example.service;

import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    public Account login(Account admin){

        Account dbStudent = studentMapper.selectByUsername(admin.getUsername());
        if(null == dbStudent){
            throw  new CustomException("賬號或者密碼錯誤");
        }
        if (!admin.getPassword().equals(dbStudent.getPassword())){
            throw new CustomException("密碼錯誤");
        }

        return dbStudent;

    }








}
