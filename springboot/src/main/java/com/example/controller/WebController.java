package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.enumEntity.AccountEnum;
import com.example.service.AdminService;
import com.example.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    private StudentService studentService;

    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }


    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account resultAccount;
        if(AccountEnum.ADMIN.name().equals(account.getRole())){
            resultAccount = adminService.login(account);
        }else if(AccountEnum.STUDENT.name().equals(account.getRole())){
            resultAccount = studentService.login(account);
        }else{
            return Result.error("非法登錄！");
        }
        return Result.success(resultAccount);

    }

}
